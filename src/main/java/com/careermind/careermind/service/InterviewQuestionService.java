package com.careermind.careermind.service;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class InterviewQuestionService {

    public List<String> generateQuestions(String jobTitle) {

        List<String> questions = new ArrayList<>();

        if (jobTitle == null || jobTitle.isBlank()) {
            jobTitle = "Software Engineer";
        }

        questions.add(
                "Tell me about yourself and why you are interested in the "
                        + jobTitle + " role."
        );

        questions.add(
                "What technical skills do you have that are relevant to this "
                        + jobTitle + " role?"
        );

        questions.add(
                "Describe a project you have worked on and explain your contribution."
        );

        questions.add(
                "Tell me about a technical problem you faced and how you solved it."
        );

        questions.add(
                "How do you approach learning a new technology or framework?"
        );

        return questions;
    }
}