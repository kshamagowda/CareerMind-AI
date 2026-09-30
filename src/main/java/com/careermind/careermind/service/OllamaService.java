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

    private String classifyQuestion(String question) {

        String q = question.toLowerCase().trim();

        // INTRODUCTION
        if (q.contains("tell me about yourself")
                || q.contains("introduce yourself")
                || q.contains("your background")
                || q.contains("why are you interested")
                || q.contains("why do you want")
                || q.contains("why are you applying")) {

            return "INTRODUCTION";
        }

        // TECHNICAL SKILLS
        if (q.contains("technical skills")
                || q.contains("programming languages")
                || q.contains("technical knowledge")
                || q.contains("what technologies")
                || q.contains("what frameworks")
                || q.contains("what databases")
                || q.contains("what tools")
                || q.contains("what skills")
                || q.contains("which skills")
                || q.contains("technologies are you")
                || q.contains("frameworks are you")
                || q.contains("languages are you")) {

            return "TECHNICAL_SKILLS";
        }

        // PROJECT
        if (q.contains("project")
                || q.contains("application you built")
                || q.contains("system you developed")
                || q.contains("what did you build")
                || q.contains("your contribution")) {

            return "PROJECT";
        }

        // TECHNICAL PROBLEM
        if (q.contains("technical problem")
                || q.contains("technical challenge")
                || q.contains("debug")
                || q.contains("debugging")
                || q.contains("coding problem")
                || q.contains("performance issue")
                || q.contains("technical issue")
                || q.contains("how did you solve")) {

            return "TECHNICAL_PROBLEM";
        }

        // BEHAVIORAL
        if (q.contains("teamwork")
                || q.contains("conflict")
                || q.contains("leadership")
                || q.contains("communication")
                || q.contains("failure")
                || q.contains("pressure")
                || q.contains("decision")
                || q.contains("workplace situation")) {

            return "BEHAVIORAL";
        }

        // LEARNING AND ADAPTABILITY
        if (q.contains("learn")
                || q.contains("new technology")
                || q.contains("new technologies")
                || q.contains("adapt")
                || q.contains("unfamiliar")
                || q.contains("new framework")
                || q.contains("learn something new")) {

            return "LEARNING_AND_ADAPTABILITY";
        }

        return "OTHER";
    }

    public String evaluateInterviewAnswer(
            String question,
            String answer) {

        String questionType = classifyQuestion(question);

        String prompt = """
                You are an interview evaluator for CareerMind AI.

                Evaluate the candidate's answer to the interview question below.

                Interview Question:
                %s

                Candidate Answer:
                %s

                Question Type:
                %s

                The question type above was determined by the CareerMind AI backend.

                IMPORTANT:
                - Use exactly the question type provided above.
                - Do NOT change, reclassify, or override the question type.
                - Evaluate the candidate's answer according to this question type.
                - Classify based on the actual question, not the candidate's answer.
                
                Evaluate the answer using these five scoring categories:

                1. Relevance: 0-25
                2. Technical Accuracy: 0-25
                3. Clarity and Structure: 0-20
                4. Specific Examples and Personal Contribution: 0-15
                5. Communication and Professionalism: 0-15

                SCORING RULES:

                - Relevance must be between 0 and 25.
                - Technical Accuracy must be between 0 and 25.
                - Clarity and Structure must be between 0 and 20.
                - Specific Examples must be between 0 and 15.
                - Communication and Professionalism must be between 0 and 15.
                - Never give a category score above its maximum.
                - Never give a negative score.
                - The final Score must equal the exact sum of the five category scores.
                - The final Score must therefore be between 0 and 100.
                - Do not automatically give a score around 80.
                - Do not automatically give 82.
                - Score the actual quality of the candidate's answer.

                QUESTION TYPE GUIDANCE:

                INTRODUCTION:
                - Evaluate whether the candidate clearly introduces themselves.
                - Evaluate their background, relevant skills, motivation, and career interest.
                - Communication and structure are important.
                - Do not heavily penalize the candidate for not giving detailed technical explanations.

                TECHNICAL_SKILLS:
                - Focus strongly on whether the candidate accurately describes their technical skills.
                - Check whether the technologies mentioned are relevant to the question.
                - Give additional credit when the candidate connects skills to genuine project experience.
                - Do not require performance metrics.
                - Do not require a technical challenge unless the question asks for one.

                PROJECT:
                - Focus on the candidate's actual project experience.
                - Evaluate their personal contribution.
                - Evaluate technologies used, responsibilities, challenges, and learning.
                - Do not require user numbers or performance metrics unless the candidate actually provides them.

                TECHNICAL_PROBLEM:
                - Focus on the actual technical problem.
                - Evaluate investigation, debugging, reasoning, solution, testing, and result.
                - Do not invent a technical problem if the candidate did not describe one.

                BEHAVIORAL:
                - Focus on the situation, actions taken, communication, decision-making, and result.
                - Do not require technical details unless the question specifically asks for them.

                LEARNING_AND_ADAPTABILITY:
                - Focus on the candidate's learning process.
                - Evaluate how they learned or adapted.
                - Look for practical evidence when it is actually provided.
                - Do not invent learning experiences.

                OTHER:
                - Evaluate the answer according to the actual question.
                - Do not force the answer into another category.

                STRICT FACTUAL ACCURACY RULES:

                - NEVER invent achievements.
                - NEVER invent performance metrics.
                - NEVER invent percentages.
                - NEVER invent user numbers.
                - NEVER invent application traffic.
                - NEVER invent performance improvements.
                - NEVER invent technologies.
                - NEVER invent tools.
                - NEVER invent responsibilities.
                - NEVER invent project results.
                - NEVER invent company names.
                - NEVER invent internships or work experience.
                - NEVER invent certifications.
                - NEVER invent awards.
                - NEVER invent facts about the candidate.
                - Only use information explicitly provided in the candidate's answer or question.
                - If the candidate did not provide a metric, do not create one.
                - If the candidate did not provide a result, do not create one.
                - If information is missing, say that it was not provided.
                - Do not assume that a technology was used just because it is mentioned in the job description.

                BETTER ANSWER RULES:

                - Improve the candidate's answer while preserving the candidate's actual facts.
                - You may improve grammar, clarity, organization, and professional wording.
                - You may reorganize information.
                - You may make the answer more concise.
                - You may NOT add fake achievements, metrics, technologies, users, results, responsibilities, or experiences.
                - You may NOT claim the candidate did something that was not stated in the original answer.
                - If the original answer lacks an important detail, improve the structure without inventing the missing detail.

                Return the evaluation in EXACTLY this format:

                Question Type: %s

                Relevance: X/25
                Technical Accuracy: X/25
                Clarity and Structure: X/20
                Specific Examples: X/15
                Communication and Professionalism: X/15
                Score: X/100

                Strengths:
                - Give 2 or 3 specific strengths based only on the candidate's actual answer.

                Improvements:
                - Give 2 or 3 specific improvements based only on the candidate's actual answer.

                Better Answer:
                - Give a concise improved version of the candidate's answer.
                - Do not add facts that were not provided by the candidate.
                """.formatted(
                question,
                answer,
                questionType,
                questionType
        );

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