package com.careermind.careermind.service;

import com.careermind.careermind.model.JobDescription;
import com.careermind.careermind.repository.JobDescriptionRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JobDescriptionService {

    private final JobDescriptionRepository jobDescriptionRepository;

    public JobDescriptionService(JobDescriptionRepository jobDescriptionRepository) {
        this.jobDescriptionRepository = jobDescriptionRepository;
    }

    public JobDescription saveJobDescription(JobDescription jobDescription) {
        return jobDescriptionRepository.save(jobDescription);
    }

    public List<JobDescription> getAllJobDescriptions() {
        return jobDescriptionRepository.findAll();
    }
}