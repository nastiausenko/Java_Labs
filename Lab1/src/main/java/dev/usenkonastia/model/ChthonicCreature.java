package dev.usenkonastia.model;

import java.time.LocalDate;

/**
 * Represents a chthonic creature with specific attributes such as name, type, date of first mention, and attack power.
 *
 * @author                  Anastasiia Usenko
 * @param name              the name of the creature
 * @param type              the type or classification of the creature
 * @param firstMentionDate  the date when the creature was first mentioned
 * @param attackPower       the attack power of the creature
 */
public record ChthonicCreature(
        String name,
        String type,
        LocalDate firstMentionDate,
        int attackPower
) {}
