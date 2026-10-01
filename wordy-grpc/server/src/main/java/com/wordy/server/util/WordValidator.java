package com.wordy.server.util;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Validates words against the dictionary (words.txt) and checks if they can be
 * formed from available letters.
 */
public class WordValidator {
    private static WordValidator instance;
    private Set<String> dictionary;

    private WordValidator() {
        loadDictionary();
    }

    /**
     * Gets the singleton instance of WordValidator.
     */
    public static synchronized WordValidator getInstance() {
        if (instance == null) {
            instance = new WordValidator();
        }
        return instance;
    }

    /**
     * Loads the dictionary from words.txt into memory.
     */
    private void loadDictionary() {
        dictionary = new HashSet<>();
        try {
            // Try multiple possible locations for words.txt
            String[] possiblePaths = {
                    "wordy-grpc/server/src/main/res/words.txt",
                    "server/src/main/res/words.txt",
                    "src/main/res/words.txt",
                    "res/words.txt",
                    "words.txt"
            };

            boolean loaded = false;
            for (String path : possiblePaths) {
                try {
                    List<String> lines = Files.readAllLines(Paths.get(path));
                    for (String word : lines) {
                        dictionary.add(word.toLowerCase().trim());
                    }
                    System.out.println("Dictionary loaded from: " + path + " (" + dictionary.size() + " words)");
                    loaded = true;
                    break;
                } catch (IOException ignored) {
                    // Try next path
                }
            }

            if (!loaded) {
                System.err.println("WARNING: Could not load words.txt from any expected location");
                System.err.println("Word validation will accept any word. Please place words.txt in the server resources.");
            }

        } catch (Exception e) {
            System.err.println("Error loading dictionary: " + e.getMessage());
        }
    }

    /**
     * Validates if a word is in the dictionary.
     *
     * @param word the word to check
     * @return true if word is in dictionary
     */
    public boolean isValidWord(String word) {
        if (dictionary.isEmpty()) {
            System.out.println("WARNING: Dictionary is empty. Accepting all words as valid.");
            return true;
        }
        return dictionary.contains(word.toLowerCase().trim());
    }

    /**
     * Checks if a word can be formed from the available letters.
     *
     * @param word the word to check
     * @param availableLetters the available letters (can be used multiple times if present)
     * @return true if word can be formed from available letters
     */
    public boolean canFormWord(String word, String availableLetters) {
        word = word.toUpperCase();
        availableLetters = availableLetters.toUpperCase();

        // Count available letters
        int[] availableCount = new int[26];
        for (char c : availableLetters.toCharArray()) {
            if (c >= 'A' && c <= 'Z') {
                availableCount[c - 'A']++;
            }
        }

        // Check if word can be formed
        for (char c : word.toCharArray()) {
            if (c < 'A' || c > 'Z') {
                continue; // Skip non-alphabetic characters
            }
            if (availableCount[c - 'A'] > 0) {
                availableCount[c - 'A']--;
            } else {
                return false; // Not enough of this letter
            }
        }

        return true;
    }

    /**
     * Performs complete validation: checks dictionary and letter availability.
     *
     * @param word the word to validate
     * @param availableLetters the available letters
     * @return ValidationResult with success status and message
     */
    public ValidationResult validateWord(String word, String availableLetters) {
        if (word == null || word.trim().isEmpty()) {
            return new ValidationResult(false, "Word cannot be empty");
        }

        word = word.trim();

        if (word.length() < 5) {
            return new ValidationResult(false, "Word must be at least 5 letters long");
        }

        if (!canFormWord(word, availableLetters)) {
            return new ValidationResult(false, "Word cannot be formed from available letters");
        }

        if (!isValidWord(word)) {
            return new ValidationResult(false, "Word is not in the dictionary");
        }

        return new ValidationResult(true, "Word is valid");
    }

    /**
     * Result class for word validation.
     */
    public static class ValidationResult {
        public final boolean valid;
        public final String message;

        public ValidationResult(boolean valid, String message) {
            this.valid = valid;
            this.message = message;
        }
    }
}
