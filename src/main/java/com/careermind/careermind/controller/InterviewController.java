package com.careermind.careermind.controller;

import com.careermind.careermind.model.Interview;
import com.careermind.careermind.service.InterviewQuestionService;
import com.careermind.careermind.service.InterviewService;
import com.careermind.careermind.service.OllamaService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/interviews")
public class InterviewController {

    private final InterviewService interviewService;
    private final InterviewQuestionService interviewQuestionService;
    private final OllamaService ollamaService;

    public InterviewController(
            InterviewService interviewService,
            InterviewQuestionService interviewQuestionService,
            OllamaService ollamaService) {

        this.interviewService = interviewService;
        this.interviewQuestionService = interviewQuestionService;
        this.ollamaService = ollamaService;
    }

    @PostMapping
    public Interview createInterview(@RequestBody Interview interview) {
        return interviewService.saveInterview(interview);
    }

    @GetMapping
    public List<Interview> getAllInterviews() {
        return interviewService.getAllInterviews();
    }

    @GetMapping("/questions")
    public List<String> generateQuestions(
            @RequestParam String jobTitle) {

        return interviewQuestionService.generateQuestions(jobTitle);
    }

    @PostMapping("/ai-evaluate")
    public String evaluateAnswerWithAI(
            @RequestParam String question,
            @RequestParam String answer,
            @RequestParam String jobTitle) {

        String aiResult =
                ollamaService.evaluateInterviewAnswer(
                        question,
                        answer
                );

        Interview interview = new Interview();

        interview.setJobTitle(jobTitle);
        interview.setQuestion(question);
        interview.setAnswer(answer);
        interview.setFeedback(aiResult);

        java.util.regex.Matcher scoreMatcher =
                java.util.regex.Pattern
                        .compile(
                                "Score:\\s*(\\d+)",
                                java.util.regex.Pattern.CASE_INSENSITIVE
                        )
                        .matcher(aiResult);

        if (scoreMatcher.find()) {
            interview.setScore(
                    Integer.parseInt(scoreMatcher.group(1))
            );
        }

        interviewService.saveInterview(interview);

        return aiResult;
    }
}