package com.careermind.careermind.service;

import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class SkillGapService {

    public Map<String, Object> analyzeSkillGap(
            String resumeSkills,
            String requiredSkills) {

        // Convert resume skills into a set
        Set<String> resumeSkillSet = convertToSet(resumeSkills);

        // Convert required job skills into a set
        Set<String> requiredSkillSet = convertToSet(requiredSkills);

        // Find matched skills
        Set<String> matchedSkills = new LinkedHashSet<>(resumeSkillSet);
        matchedSkills.retainAll(requiredSkillSet);

        // Find missing skills
        Set<String> missingSkills = new LinkedHashSet<>(requiredSkillSet);
        missingSkills.removeAll(resumeSkillSet);

        // Calculate match percentage
        double matchPercentage = 0;

        if (!requiredSkillSet.isEmpty()) {
            matchPercentage =
                    ((double) matchedSkills.size()
                            / requiredSkillSet.size()) * 100;
        }

        // Round to 2 decimal places
        matchPercentage =
                Math.round(matchPercentage * 100.0) / 100.0;

        // Prepare result
        Map<String, Object> result = new LinkedHashMap<>();

        result.put("matchedSkills", matchedSkills);
        result.put("missingSkills", missingSkills);
        result.put("matchPercentage", matchPercentage);

        return result;
    }

    private Set<String> convertToSet(String skills) {

        Set<String> skillSet = new LinkedHashSet<>();

        if (skills == null || skills.isBlank()) {
            return skillSet;
        }

        String[] skillArray = skills.split(",");

        for (String skill : skillArray) {

            String cleanedSkill = skill.trim().toLowerCase();

            if (!cleanedSkill.isEmpty()) {
                skillSet.add(cleanedSkill);
            }
        }

        return skillSet;
    }
}