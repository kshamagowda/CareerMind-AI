package com.careermind.careermind.controller;

import com.careermind.careermind.model.Resume;
import com.careermind.careermind.service.PdfService;
import com.careermind.careermind.service.ResumeService;
import com.careermind.careermind.service.SkillExtractionService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController
@RequestMapping("/api/resumes")
public class ResumeController {

    private final ResumeService resumeService;
    private final PdfService pdfService;
    private final SkillExtractionService skillExtractionService;

    public ResumeController(
            ResumeService resumeService,
            PdfService pdfService,
            SkillExtractionService skillExtractionService) {

        this.resumeService = resumeService;
        this.pdfService = pdfService;
        this.skillExtractionService = skillExtractionService;
    }

    @PostMapping
    public Resume createResume(@RequestBody Resume resume) {
        return resumeService.saveResume(resume);
    }

    @GetMapping
    public List<Resume> getAllResumes() {
        return resumeService.getAllResumes();
    }

    @PostMapping("/upload")
    public Resume uploadResume(@RequestParam("file") MultipartFile file) throws IOException {

        // Step 1: Extract text from PDF
        String resumeText = pdfService.extractText(file);

        // Step 2: Extract skills
        List<String> skills = skillExtractionService.extractSkills(resumeText);

        // Step 3: Extract email
        String email = extractEmail(resumeText);

        // Step 4: Extract phone number
        String phone = extractPhone(resumeText);

        // Step 5: Extract name
        String name = extractName(resumeText);

        // Step 6: Create Resume object
        Resume resume = new Resume();

        resume.setName(name);
        resume.setEmail(email);
        resume.setPhone(phone);
        resume.setSkills(String.join(", ", skills));

        // Step 7: Save everything to MySQL
        return resumeService.saveResume(resume);
    }

    private String extractEmail(String text) {

        Pattern pattern = Pattern.compile(
                "[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}"
        );

        Matcher matcher = pattern.matcher(text);

        if (matcher.find()) {
            return matcher.group();
        }

        return null;
    }

    private String extractPhone(String text) {

        Pattern pattern = Pattern.compile(
                "(?:\\+91[-\\s]?)?[6-9]\\d{9}"
        );

        Matcher matcher = pattern.matcher(text);

        if (matcher.find()) {
            return matcher.group();
        }

        return null;
    }

    private String extractName(String text) {

        String[] lines = text.split("\\r?\\n");

        for (String line : lines) {

            line = line.trim();

            if (line.isEmpty()) {
                continue;
            }

            // Ignore lines that look like contact information
            if (line.contains("@")) {
                continue;
            }

            if (line.matches(".*\\d.*")) {
                continue;
            }

            // Ignore common resume headings
            if (line.equalsIgnoreCase("resume")
                    || line.equalsIgnoreCase("curriculum vitae")
                    || line.equalsIgnoreCase("cv")) {
                continue;
            }

            // Name is usually near the beginning of the resume
            if (line.length() >= 3 && line.length() <= 60) {
                return line;
            }
        }

        return null;
    }
}