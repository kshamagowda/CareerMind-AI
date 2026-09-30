package com.careermind.careermind.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Service
public class OpenAIService {

    @Value("${OPENAI_API_KEY:}")
    private String apiKey;

    private final HttpClient httpClient = HttpClient.newHttpClient();

    public String evaluateInterviewAnswer(
            String question,
            String answer) {

        String prompt = """
                Evaluate this interview answer.

                Interview Question:
                %s

                Candidate Answer:
                %s

                Give:
                1. Score out of 100
                2. What was done well
                3. What could be improved
                4. A better example answer

                Keep the feedback concise and useful for a student.
                """.formatted(question, answer);

        String jsonBody = """
                {
                  "model": "gpt-5-mini",
                  "input": %s
                }
                """.formatted(toJsonString(prompt));

        try {

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(
                            "https://api.openai.com/v1/responses"
                    ))
                    .header(
                            "Authorization",
                            "Bearer " + apiKey
                    )
                    .header(
                            "Content-Type",
                            "application/json"
                    )
                    .POST(
                            HttpRequest.BodyPublishers.ofString(jsonBody)
                    )
                    .build();

            HttpResponse<String> response =
                    httpClient.send(
                            request,
                            HttpResponse.BodyHandlers.ofString()
                    );

            if (response.statusCode() >= 200
                    && response.statusCode() < 300) {

                return response.body();

            } else {

                return "OpenAI API error: "
                        + response.statusCode()
                        + " - "
                        + response.body();
            }

        } catch (Exception e) {

            return "Error connecting to OpenAI: "
                    + e.getMessage();
        }
    }

    private String toJsonString(String text) {

        return "\"" + text
                .replace("\\", "\\\\")
                .replace("\"", "\\\"")
                .replace("\n", "\\n")
                .replace("\r", "\\r")
                + "\"";
    }
}