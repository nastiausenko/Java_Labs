package dev.usenkonastia.model;

import java.time.LocalDate;

public record ChthonicCreature(
        String name,
        String type,
        LocalDate firstMentionDate,
        int attackPower
) {}
