package com.careermind.careermind.controller;

import com.careermind.careermind.model.JobDescription;
import com.careermind.careermind.service.JobDescriptionService;
import com.careermind.careermind.service.SkillExtractionService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/jobs")
public class JobDescriptionController {

    private final JobDescriptionService jobDescriptionService;
    private final SkillExtractionService skillExtractionService;

    public JobDescriptionController(
            JobDescriptionService jobDescriptionService,
            SkillExtractionService skillExtractionService) {

        this.jobDescriptionService = jobDescriptionService;
        this.skillExtractionService = skillExtractionService;
    }

    @PostMapping
    public JobDescription createJobDescription(
            @RequestBody JobDescription jobDescription) {

        // Automatically extract skills from the job description
        List<String> detectedSkills =
                skillExtractionService.extractSkills(
                        jobDescription.getDescription()
                );

        // Store the detected skills
        jobDescription.setRequiredSkills(
                String.join(", ", detectedSkills)
        );

        return jobDescriptionService.saveJobDescription(jobDescription);
    }

    @GetMapping
    public List<JobDescription> getAllJobDescriptions() {

        return jobDescriptionService.getAllJobDescriptions();
    }
}