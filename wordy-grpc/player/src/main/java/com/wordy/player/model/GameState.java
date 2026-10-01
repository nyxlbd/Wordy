package com.wordy.player.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Manages the local game state during active gameplay.
 */
public class GameState {
    private String gameId;
    private String currentUsername;
    private List<String> currentPlayers;
    private String currentLetters;
    private int currentRound;
    private int playerRoundWins;
    private int opponentRoundWins;
    private String gameStatus; // WAITING, STARTED, IN_PROGRESS, FINISHED
    private List<String> submittedWords;
    private String roundWinner;

    public GameState(String gameId, String currentUsername) {
        this.gameId = gameId;
        this.currentUsername = currentUsername;
        this.currentPlayers = new ArrayList<>();
        this.currentRound = 0;
        this.playerRoundWins = 0;
        this.opponentRoundWins = 0;
        this.gameStatus = "WAITING";
        this.submittedWords = new ArrayList<>();
    }

    public String getGameId() {
        return gameId;
    }

    public String getCurrentUsername() {
        return currentUsername;
    }

    public List<String> getCurrentPlayers() {
        return currentPlayers;
    }

    public void setCurrentPlayers(List<String> currentPlayers) {
        this.currentPlayers = currentPlayers;
    }

    public String getCurrentLetters() {
        return currentLetters;
    }

    public void setCurrentLetters(String currentLetters) {
        this.currentLetters = currentLetters;
    }

    public int getCurrentRound() {
        return currentRound;
    }

    public void setCurrentRound(int currentRound) {
        this.currentRound = currentRound;
    }

    public int getPlayerRoundWins() {
        return playerRoundWins;
    }

    public void setPlayerRoundWins(int playerRoundWins) {
        this.playerRoundWins = playerRoundWins;
    }

    public int getOpponentRoundWins() {
        return opponentRoundWins;
    }

    public void setOpponentRoundWins(int opponentRoundWins) {
        this.opponentRoundWins = opponentRoundWins;
    }

    public String getGameStatus() {
        return gameStatus;
    }

    public void setGameStatus(String gameStatus) {
        this.gameStatus = gameStatus;
    }

    public List<String> getSubmittedWords() {
        return submittedWords;
    }

    public void addSubmittedWord(String word) {
        this.submittedWords.add(word);
    }

    public String getRoundWinner() {
        return roundWinner;
    }

    public void setRoundWinner(String roundWinner) {
        this.roundWinner = roundWinner;
    }

    public void resetForNewRound() {
        this.currentLetters = null;
        this.submittedWords.clear();
        this.roundWinner = null;
        this.currentRound++;
    }

    @Override
    public String toString() {
        return "GameState{" +
                "gameId='" + gameId + '\'' +
                ", currentUsername='" + currentUsername + '\'' +
                ", currentRound=" + currentRound +
                ", playerWins=" + playerRoundWins +
                ", opponentWins=" + opponentRoundWins +
                ", gameStatus='" + gameStatus + '\'' +
                '}';
    }
}
