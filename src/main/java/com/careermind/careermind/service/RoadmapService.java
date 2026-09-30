package com.careermind.careermind.service;

import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class RoadmapService {

    public List<Map<String, Object>> generateRoadmap(
            Set<String> missingSkills) {

        List<Map<String, Object>> roadmap =
                new ArrayList<>();

        int stepNumber = 1;

        for (String skill : missingSkills) {

            Map<String, Object> step =
                    new LinkedHashMap<>();

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

        Map<String, String> durations =
                new HashMap<>();

        durations.put("java", "2 weeks");
        durations.put("python", "2 weeks");
        durations.put("spring boot", "2 weeks");
        durations.put("mysql", "1 week");
        durations.put("sql", "1 week");
        durations.put("rest apis", "1 week");
        durations.put("git", "3 days");
        durations.put("jpa", "1 week");
        durations.put("hibernate", "1 week");
        durations.put("dsa", "2 weeks");
        durations.put("oop", "1 week");

        return durations.getOrDefault(
                skill.toLowerCase(),
                "1 week"
        );
    }

    private List<String> getTopics(String skill) {

        Map<String, List<String>> topics =
                new HashMap<>();

        topics.put("spring boot", Arrays.asList(
                "Spring Boot fundamentals",
                "Dependency Injection",
                "REST Controllers",
                "Spring Data JPA",
                "Database integration",
                "Exception handling"
        ));

        topics.put("mysql", Arrays.asList(
                "Database fundamentals",
                "SELECT and WHERE",
                "JOINs",
                "GROUP BY and HAVING",
                "Subqueries",
                "Indexes and constraints"
        ));

        topics.put("rest apis", Arrays.asList(
                "HTTP fundamentals",
                "GET and POST requests",
                "PUT and DELETE requests",
                "JSON and request/response bodies",
                "Status codes",
                "API testing with Postman"
        ));

        topics.put("java", Arrays.asList(
                "Java fundamentals",
                "Object-Oriented Programming",
                "Collections Framework",
                "Exception handling",
                "Streams and Lambda expressions",
                "Generics"
        ));

        topics.put("jpa", Arrays.asList(
                "JPA fundamentals",
                "Entities and primary keys",
                "Relationships and mappings",
                "Repositories",
                "JPQL queries",
                "CRUD operations"
        ));

        topics.put("hibernate", Arrays.asList(
                "Hibernate fundamentals",
                "ORM concepts",
                "Entity mapping",
                "One-to-One and One-to-Many relationships",
                "Lazy and eager loading",
                "Transactions"
        ));

        topics.put("dsa", Arrays.asList(
                "Arrays and Strings",
                "Linked Lists",
                "Stacks and Queues",
                "Hashing",
                "Trees and Graphs",
                "Sorting and Searching",
                "Time and Space Complexity"
        ));

        topics.put("oop", Arrays.asList(
                "Classes and Objects",
                "Encapsulation",
                "Inheritance",
                "Polymorphism",
                "Abstraction",
                "Interfaces",
                "SOLID principles"
        ));

        topics.put("sql", Arrays.asList(
                "SQL fundamentals",
                "SELECT and filtering",
                "JOINs",
                "GROUP BY and aggregate functions",
                "Subqueries",
                "Database design"
        ));

        topics.put("git", Arrays.asList(
                "Git fundamentals",
                "Repositories and commits",
                "Branches",
                "Merge and rebase",
                "Pull requests",
                "GitHub collaboration"
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

        Map<String, String> projects =
                new HashMap<>();

        projects.put(
                "spring boot",
                "Build a Job Application REST API using Spring Boot"
        );

        projects.put(
                "mysql",
                "Build a Student Management Database with MySQL"
        );

        projects.put(
                "rest apis",
                "Build a Job Search REST API"
        );

        projects.put(
                "java",
                "Build a Java Employee Management System"
        );

        projects.put(
                "jpa",
                "Build a CRUD application using Spring Data JPA"
        );

        projects.put(
                "hibernate",
                "Build a database application using Hibernate ORM"
        );

        projects.put(
                "dsa",
                "Build a Java-based Algorithm Practice Toolkit"
        );

        projects.put(
                "oop",
                "Build an Object-Oriented Library Management System"
        );

        projects.put(
                "sql",
                "Build an Employee Database with SQL queries"
        );

        projects.put(
                "git",
                "Create and manage a collaborative GitHub project"
        );

        return projects.getOrDefault(
                skill.toLowerCase(),
                "Build a small project using " + skill
        );
    }
}