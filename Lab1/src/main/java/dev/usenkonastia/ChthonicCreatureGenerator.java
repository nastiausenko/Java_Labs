package dev.usenkonastia;

import java.time.LocalDate;
import java.util.List;
import java.util.Random;
import java.util.stream.Stream;


public class ChthonicCreatureGenerator {
    private static final List<String> NAMES = List.of("Мара", "Лілея", "Чорт", "Відьма", "Русалка");
    private static final List<String> TYPES = List.of("Демон", "Дух", "Вампір", "Перевертень", "Горгулья");
    private static final Random RANDOM = new Random();

    public static Stream<ChthonicCreature> generate() {
        return Stream.generate(() -> new ChthonicCreature(
                getRandomElement(NAMES),
                getRandomElement(TYPES),
                getRandomDate(),
                getRandomAttackPower()
        ));
    }

    private static String getRandomElement(List<String> list) {
        return list.get(RANDOM.nextInt(list.size()));
    }

    private static LocalDate getRandomDate() {
        int year = 1000 + RANDOM.nextInt(1024);
        int dayOfYear = 1 + RANDOM.nextInt(LocalDate.of(year, 12, 31).getDayOfYear() - 1);
        return LocalDate.ofYearDay(year, dayOfYear);
    }

    private static int getRandomAttackPower() {
        return 10 + RANDOM.nextInt(91);
    }
}
