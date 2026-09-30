package com.careermind.careermind.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Service
public class OllamaService {

    private final HttpClient httpClient = HttpClient.newHttpClient();
    private final ObjectMapper objectMapper = new ObjectMapper();

    public String evaluateInterviewAnswer(
            String question,
            String answer) {

        String prompt = """
                You are an interview evaluator for CareerMind AI.

                Evaluate the candidate's answer to the interview question below.

                Interview Question:
                %s

                Candidate Answer:
                %s

                First, identify the type of interview question.
                
                Possible question types:
                1. INTRODUCTION
                2. TECHNICAL_SKILLS
                3. PROJECT
                4. TECHNICAL_PROBLEM
                5. BEHAVIORAL
                6. LEARNING_AND_ADAPTABILITY
                7. OTHER
                
                Then evaluate the answer using five categories.
                The importance of each category should depend on the question type.
                
                Base categories:
                
                1. Relevance: 0-25
                2. Technical Accuracy: 0-25
                3. Clarity and Structure: 0-20
                4. Specific Examples and Personal Contribution: 0-15
                5. Communication and Professionalism: 0-15
                
                For INTRODUCTION questions:
                - Do NOT heavily penalize the candidate for not giving detailed technical problems.
                - Focus on whether the candidate clearly introduces themselves.
                - Evaluate motivation for the role.
                - Evaluate whether relevant skills and projects are mentioned naturally.
                - Communication and structure are especially important.
                
                For TECHNICAL_SKILLS questions:
                - Focus strongly on whether the candidate accurately describes their technical skills.
                - Give credit for technologies supported by concrete project experience.
                - Do not require performance metrics.
                
                For PROJECT questions:
                - Focus strongly on the candidate's personal contribution.
                - Evaluate technologies used, responsibilities, challenges, and what the candidate learned.
                - Do not require user numbers or performance metrics unless the candidate actually provides them.
                
                For TECHNICAL_PROBLEM questions:
                - Focus strongly on the problem, investigation, solution, testing, and result.
                - Evaluate the candidate's technical reasoning and debugging approach.
                
                For BEHAVIORAL questions:
                - Focus on the situation, actions taken, communication, decision-making, and result.
                - Do not require technical details unless the question is technical.
                
                For LEARNING_AND_ADAPTABILITY questions:
                - Focus on the candidate's learning process.
                - Look for a practical example of learning and applying a new technology.
                - Evaluate adaptability and problem-solving.
                
                IMPORTANT:
                - Do not invent achievements, metrics, users, technologies, or project results.
                - Do not penalize the candidate for information that the question does not reasonably require.
                - Do not require every answer to contain a technical challenge.
                - Do not automatically give a score around 80.
                - Do not automatically give 82.
                - Score the actual answer.
                - Calculate the final score by adding the five category scores.
                
                Return the evaluation in EXACTLY this format:
                
                Question Type: [type]
                
                Relevance: X/25
                Technical Accuracy: X/25
                Clarity and Structure: X/20
                Specific Examples: X/15
                Communication and Professionalism: X/15
                Score: X/100
                
                Strengths:
                - Give 2 or 3 specific strengths.
                
                Improvements:
                - Give 2 or 3 specific improvements.
                
                Better Answer:
                - Give a concise improved version of the candidate's answer.
                """.formatted(question, answer);

        String jsonBody = """
                {
                  "model": "llama3.2:3b",
                  "prompt": %s,
                  "stream": false
                }
                """.formatted(toJsonString(prompt));

        try {

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(
                            "http://localhost:11434/api/generate"
                    ))
                    .header(
                            "Content-Type",
                            "application/json"
                    )
                    .POST(
                            HttpRequest.BodyPublishers.ofString(
                                    jsonBody
                            )
                    )
                    .build();

            HttpResponse<String> response =
                    httpClient.send(
                            request,
                            HttpResponse.BodyHandlers.ofString()
                    );

            if (response.statusCode() >= 200
                    && response.statusCode() < 300) {

                JsonNode jsonResponse =
                        objectMapper.readTree(
                                response.body()
                        );

                JsonNode aiResponse =
                        jsonResponse.get("response");

                if (aiResponse != null) {
                    return aiResponse.asText();
                }

                return "AI response was empty.";
            }

            return "Ollama error: "
                    + response.statusCode()
                    + " - "
                    + response.body();

        } catch (Exception e) {

            return "Error connecting to Ollama: "
                    + e.getMessage();
        }
    }

    private String toJsonString(String text) {

        return "\""
                + text
                .replace("\\", "\\\\")
                .replace("\"", "\\\"")
                .replace("\n", "\\n")
                .replace("\r", "\\r")
                + "\"";
    }
}