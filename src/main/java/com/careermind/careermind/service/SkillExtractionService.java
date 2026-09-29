package com.careermind.careermind.service;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

@Service
public class SkillExtractionService {

    private final List<String> knownSkills = Arrays.asList(
            "Java",
            "Python",
            "JavaScript",
            "C",
            "C++",
            "SQL",
            "HTML",
            "CSS",
            "Spring Boot",
            "Spring",
            "Node.js",
            "Express.js",
            "REST APIs",
            "REST",
            "MySQL",
            "SQLite",
            "MongoDB",
            "Git",
            "GitHub",
            "Docker",
            "AWS",
            "Azure",
            "React",
            "Angular",
            "NLP",
            "Machine Learning",
            "Artificial Intelligence",
            "AI",
            "Pandas",
            "NumPy",
            "Matplotlib",
            "TensorFlow",
            "PyTorch",
            "Jest",
            "JWT",
            "JPA",
            "Hibernate",
            "DSA",
            "Data Structures",
            "Algorithms",
            "DBMS",
            "OOP",
            "TF-IDF",
            "Cosine Similarity"
    );

    public List<String> extractSkills(String text) {

        List<String> extractedSkills = new ArrayList<>();

        if (text == null || text.isBlank()) {
            return extractedSkills;
        }

        String lowerText = text.toLowerCase();

        for (String skill : knownSkills) {

            String lowerSkill = skill.toLowerCase();

            String regex = "(?<![a-z0-9+#.-])"
                    + Pattern.quote(lowerSkill)
                    + "(?![a-z0-9+#.-])";

            if (Pattern.compile(regex).matcher(lowerText).find()) {
                extractedSkills.add(skill);
            }
        }

        // Remove less-specific skills when a more-specific skill is present
        if (extractedSkills.contains("Spring Boot")) {
            extractedSkills.remove("Spring");
        }

        if (extractedSkills.contains("REST APIs")) {
            extractedSkills.remove("REST");
        }

        return extractedSkills;
    }
}