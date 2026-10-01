package com.wordy.player.model;

public class PlayerStats {
    private String username;
    private int wins;

    public PlayerStats(String username, int wins) {
        this.username = username;
        this.wins = wins;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getWins() {
        return wins;
    }

    public void setWins(int wins) {
        this.wins = wins;
    }

    @Override
    public String toString() {
        return "PlayerStats{" +
                "username='" + username + '\'' +
                ", wins=" + wins +
                '}';
    }
}
