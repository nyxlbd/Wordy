package com.wordy.player.model;

public class WordRecord {
    private String username;
    private String word;
    private int length;

    public WordRecord(String username, String word, int length) {
        this.username = username;
        this.word = word;
        this.length = length;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    @Override
    public String toString() {
        return "WordRecord{" +
                "username='" + username + '\'' +
                ", word='" + word + '\'' +
                ", length=" + length +
                '}';
    }
}
