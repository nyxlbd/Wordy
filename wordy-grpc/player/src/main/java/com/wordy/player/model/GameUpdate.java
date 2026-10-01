package com.wordy.player.model;

/**
 * Represents a game update received from the server.
 * Types: WAITING, START, ROUND, RESULT, GAME_OVER
 */
public class GameUpdate {
    private String type;
    private String message;
    private String letters;
    private String winner;
    private int roundNumber;
    private int roundDuration;

    public GameUpdate(String type, String message, String letters, String winner, int roundNumber) {
        this.type = type;
        this.message = message;
        this.letters = letters;
        this.winner = winner;
        this.roundNumber = roundNumber;
        this.roundDuration = 0;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getLetters() {
        return letters;
    }

    public void setLetters(String letters) {
        this.letters = letters;
    }

    public String getWinner() {
        return winner;
    }

    public void setWinner(String winner) {
        this.winner = winner;
    }

    public int getRoundNumber() {
        return roundNumber;
    }

    public void setRoundNumber(int roundNumber) {
        this.roundNumber = roundNumber;
    }

    public int getRoundDuration() {
        return roundDuration;
    }

    public void setRoundDuration(int roundDuration) {
        this.roundDuration = roundDuration;
    }

    @Override
    public String toString() {
        return "GameUpdate{" +
                "type='" + type + '\'' +
                ", message='" + message + '\'' +
                ", letters='" + letters + '\'' +
                ", winner='" + winner + '\'' +
                ", roundNumber=" + roundNumber +
                ", roundDuration=" + roundDuration +
                '}';
    }
}
