package com.wordy.server.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Generates random letter sets for the Wordy game.
 * Each set contains 20 letters with 5-7 vowels (letters can repeat).
 */
public class LetterGenerator {
    private static final Random random = new Random();
    private static final char[] VOWELS = {'A', 'E', 'I', 'O', 'U'};
    private static final char[] CONSONANTS = {
            'B', 'C', 'D', 'F', 'G', 'H', 'J', 'K', 'L', 'M',
            'N', 'P', 'Q', 'R', 'S', 'T', 'V', 'W', 'X', 'Y', 'Z'
    };
    private static final int TOTAL_LETTERS = 20;
    private static final int MIN_VOWELS = 5;
    private static final int MAX_VOWELS = 7;

    /**
     * Generates a random set of 20 letters with 5-7 vowels.
     *
     * @return a string of 20 random letters
     */
    public static String generateLetters() {
        List<Character> letters = new ArrayList<>();

        // Generate random number of vowels (5-7)
        int vowelCount = MIN_VOWELS + random.nextInt(MAX_VOWELS - MIN_VOWELS + 1);

        // Add vowels
        for (int i = 0; i < vowelCount; i++) {
            letters.add(VOWELS[random.nextInt(VOWELS.length)]);
        }

        // Add consonants to reach 20 total
        int consonantCount = TOTAL_LETTERS - vowelCount;
        for (int i = 0; i < consonantCount; i++) {
            letters.add(CONSONANTS[random.nextInt(CONSONANTS.length)]);
        }

        // Shuffle the letters
        Collections.shuffle(letters);

        // Convert to string
        StringBuilder result = new StringBuilder();
        for (char c : letters) {
            result.append(c);
        }

        return result.toString();
    }

    /**
     * Validates the generated letters (for testing purposes).
     *
     * @param letters the letter string to validate
     * @return true if valid (20 letters, 5-7 vowels)
     */
    public static boolean isValid(String letters) {
        if (letters == null || letters.length() != TOTAL_LETTERS) {
            return false;
        }

        int vowelCount = 0;
        for (char c : letters.toCharArray()) {
            for (char vowel : VOWELS) {
                if (c == vowel) {
                    vowelCount++;
                    break;
                }
            }
        }

        return vowelCount >= MIN_VOWELS && vowelCount <= MAX_VOWELS;
    }

    /**
     * Gets the vowel count in a letter set.
     *
     * @param letters the letter string
     * @return number of vowels
     */
    public static int getVowelCount(String letters) {
        if (letters == null) return 0;

        int count = 0;
        for (char c : letters.toCharArray()) {
            for (char vowel : VOWELS) {
                if (c == vowel) {
                    count++;
                    break;
                }
            }
        }
        return count;
    }
}
