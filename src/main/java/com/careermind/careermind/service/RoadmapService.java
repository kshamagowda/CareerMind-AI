package com.careermind.careermind.service;

import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class RoadmapService {

    public List<Map<String, Object>> generateRoadmap(
            Set<String> missingSkills) {

        List<Map<String, Object>> roadmap = new ArrayList<>();

        int stepNumber = 1;

        for (String skill : missingSkills) {

            Map<String, Object> step = new LinkedHashMap<>();

            step.put("step", stepNumber);
            step.put("skill", skill);
            step.put("duration", getDuration(skill));
            step.put("topics", getTopics(skill));
            step.put("project", getProject(skill));

            roadmap.add(step);

            stepNumber++;
        }

        return roadmap;
    }

    private String getDuration(String skill) {

        Map<String, String> durations = new HashMap<>();

        durations.put("java", "2 weeks");
        durations.put("python", "2 weeks");
        durations.put("spring boot", "2 weeks");
        durations.put("mysql", "1 week");
        durations.put("sql", "1 week");
        durations.put("rest apis", "1 week");
        durations.put("git", "3 days");

        return durations.getOrDefault(
                skill.toLowerCase(),
                "1 week"
        );
    }

    private List<String> getTopics(String skill) {

        Map<String, List<String>> topics = new HashMap<>();

        topics.put("spring boot", Arrays.asList(
                "Spring Boot basics",
                "REST Controllers",
                "Dependency Injection",
                "Spring Data JPA",
                "Database integration"
        ));

        topics.put("mysql", Arrays.asList(
                "SQL basics",
                "SELECT and WHERE",
                "JOINs",
                "GROUP BY",
                "Subqueries"
        ));

        topics.put("rest apis", Arrays.asList(
                "HTTP methods",
                "GET and POST",
                "PUT and DELETE",
                "JSON",
                "API testing"
        ));

        topics.put("java", Arrays.asList(
                "OOP",
                "Collections",
                "Exception handling",
                "Streams",
                "Generics"
        ));

        return topics.getOrDefault(
                skill.toLowerCase(),
                Arrays.asList(
                        "Fundamentals",
                        "Core concepts",
                        "Practice problems",
                        "Mini project"
                )
        );
    }

    private String getProject(String skill) {

        Map<String, String> projects = new HashMap<>();

        projects.put(
                "spring boot",
                "Build a REST API using Spring Boot"
        );

        projects.put(
                "mysql",
                "Build a Student Management Database"
        );

        projects.put(
                "rest apis",
                "Build a Job Search REST API"
        );

        projects.put(
                "java",
                "Build a Java-based Employee Management System"
        );

        return projects.getOrDefault(
                skill.toLowerCase(),
                "Build a small project using " + skill
        );
    }
}