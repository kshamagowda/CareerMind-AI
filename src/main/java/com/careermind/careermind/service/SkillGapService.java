package com.careermind.careermind.service;

import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class SkillGapService {

    public Map<String, Object> analyzeSkillGap(
            String resumeSkills,
            String requiredSkills) {

        Set<String> resumeSkillSet =
                normalizeSkills(convertToSet(resumeSkills));

        Set<String> requiredSkillSet =
                normalizeSkills(convertToSet(requiredSkills));

        Set<String> matchedSkills =
                new LinkedHashSet<>(resumeSkillSet);

        matchedSkills.retainAll(requiredSkillSet);

        Set<String> missingSkills =
                new LinkedHashSet<>(requiredSkillSet);

        missingSkills.removeAll(resumeSkillSet);

        double matchPercentage = 0;

        if (!requiredSkillSet.isEmpty()) {
            matchPercentage =
                    ((double) matchedSkills.size()
                            / requiredSkillSet.size()) * 100;
        }

        matchPercentage =
                Math.round(matchPercentage * 100.0) / 100.0;

        Map<String, Object> result =
                new LinkedHashMap<>();

        result.put("matchedSkills", matchedSkills);
        result.put("missingSkills", missingSkills);
        result.put("matchPercentage", matchPercentage);

        return result;
    }

    private Set<String> convertToSet(String skills) {

        Set<String> skillSet =
                new LinkedHashSet<>();

        if (skills == null || skills.isBlank()) {
            return skillSet;
        }

        String[] skillArray =
                skills.split(",");

        for (String skill : skillArray) {

            String cleanedSkill =
                    skill.trim().toLowerCase();

            if (!cleanedSkill.isEmpty()) {
                skillSet.add(cleanedSkill);
            }
        }

        return skillSet;
    }

    private Set<String> normalizeSkills(Set<String> skills) {

        Set<String> normalizedSkills =
                new LinkedHashSet<>();

        for (String skill : skills) {

            if (skill.equalsIgnoreCase("rest")
                    || skill.equalsIgnoreCase("REST APIs")) {

                normalizedSkills.add("REST APIs");

            } else {
                normalizedSkills.add(skill);
            }
        }

        return normalizedSkills;
    }
}