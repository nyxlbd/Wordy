package com.wordy.player.model;

import java.util.List;

public class LeaderboardData {
    private List<PlayerStats> topPlayers;
    private List<WordRecord> longestWords;

    public LeaderboardData(List<PlayerStats> topPlayers, List<WordRecord> longestWords) {
        this.topPlayers = topPlayers;
        this.longestWords = longestWords;
    }

    public List<PlayerStats> getTopPlayers() {
        return topPlayers;
    }

    public void setTopPlayers(List<PlayerStats> topPlayers) {
        this.topPlayers = topPlayers;
    }

    public List<WordRecord> getLongestWords() {
        return longestWords;
    }

    public void setLongestWords(List<WordRecord> longestWords) {
        this.longestWords = longestWords;
    }

    @Override
    public String toString() {
        return "LeaderboardData{" +
                "topPlayers=" + topPlayers +
                ", longestWords=" + longestWords +
                '}';
    }
}
