package com.careermind.careermind.controller;

import com.careermind.careermind.model.JobDescription;
import com.careermind.careermind.model.Resume;
import com.careermind.careermind.repository.JobDescriptionRepository;
import com.careermind.careermind.repository.ResumeRepository;
import com.careermind.careermind.service.SkillGapService;
import com.careermind.careermind.service.RoadmapService;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/roadmap")
public class RoadmapController {

    private final ResumeRepository resumeRepository;
    private final JobDescriptionRepository jobDescriptionRepository;
    private final SkillGapService skillGapService;
    private final RoadmapService roadmapService;

    public RoadmapController(
            ResumeRepository resumeRepository,
            JobDescriptionRepository jobDescriptionRepository,
            SkillGapService skillGapService,
            RoadmapService roadmapService) {

        this.resumeRepository = resumeRepository;
        this.jobDescriptionRepository = jobDescriptionRepository;
        this.skillGapService = skillGapService;
        this.roadmapService = roadmapService;
    }

    @GetMapping("/generate")
    public List<Map<String, Object>> generateRoadmap(
            @RequestParam Long resumeId,
            @RequestParam Long jobId) {

        Resume resume = resumeRepository.findById(resumeId)
                .orElseThrow(() ->
                        new RuntimeException("Resume not found"));

        JobDescription jobDescription =
                jobDescriptionRepository.findById(jobId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Job description not found"));

        Map<String, Object> skillGap =
                skillGapService.analyzeSkillGap(
                        resume.getSkills(),
                        jobDescription.getRequiredSkills()
                );

        @SuppressWarnings("unchecked")
        Set<String> missingSkills =
                (Set<String>) skillGap.get("missingSkills");

        return roadmapService.generateRoadmap(missingSkills);
    }
}