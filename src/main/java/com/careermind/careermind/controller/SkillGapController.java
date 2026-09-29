package com.careermind.careermind.controller;

import com.careermind.careermind.model.JobDescription;
import com.careermind.careermind.model.Resume;
import com.careermind.careermind.repository.JobDescriptionRepository;
import com.careermind.careermind.repository.ResumeRepository;
import com.careermind.careermind.service.SkillGapService;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/skill-gap")
public class SkillGapController {

    private final ResumeRepository resumeRepository;
    private final JobDescriptionRepository jobDescriptionRepository;
    private final SkillGapService skillGapService;

    public SkillGapController(
            ResumeRepository resumeRepository,
            JobDescriptionRepository jobDescriptionRepository,
            SkillGapService skillGapService) {

        this.resumeRepository = resumeRepository;
        this.jobDescriptionRepository = jobDescriptionRepository;
        this.skillGapService = skillGapService;
    }

    @GetMapping("/analyze")
    public Map<String, Object> analyzeSkillGap(
            @RequestParam Long resumeId,
            @RequestParam Long jobId) {

        Resume resume = resumeRepository.findById(resumeId)
                .orElseThrow(() -> new RuntimeException("Resume not found"));

        JobDescription jobDescription = jobDescriptionRepository.findById(jobId)
                .orElseThrow(() -> new RuntimeException("Job description not found"));

        return skillGapService.analyzeSkillGap(
                resume.getSkills(),
                jobDescription.getRequiredSkills()
        );
    }
}