package com.wordy.server.model;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import com.wordy.server.dao.ConfigDAO;

/**
 * Represents an active game session.
 * Tracks players, letters, rounds, and submissions.
 */
public class GameSession {
    private String gameId;
    private List<String> players;
    private String currentLetters;
    private int currentRound;
    private Map<String, Integer> roundWins; // player -> number of round wins
    private Map<String, List<String>> roundSubmissions; // player -> submitted words this round
    private String roundWinner;
    private long roundStartTime;
    private boolean gameActive;
    private boolean gameStarted; // True once startGame() has been called
    private int roundDuration; // Milliseconds - loaded from database
    private int waitDuration; // Milliseconds - loaded from database
    private static final int WINS_TO_WIN_GAME = 3; // Number of round wins needed to win the game
    private static final int DEFAULT_ROUND_DURATION = 30; // Default in seconds
    private static final int DEFAULT_WAIT_DURATION = 10; // Default in seconds

    public GameSession(String gameId, String firstPlayer) {
        this.gameId = gameId;
        this.players = Collections.synchronizedList(new ArrayList<>());
        this.players.add(firstPlayer);
        this.currentRound = 1;
        this.roundWins = new ConcurrentHashMap<>();
        this.roundSubmissions = new ConcurrentHashMap<>();
        this.gameActive = true;
        this.gameStarted = false;
        this.roundWins.put(firstPlayer, 0);
        
        // Load configuration from database
        ConfigDAO configDAO = new ConfigDAO();
        this.roundDuration = configDAO.getRoundDuration(DEFAULT_ROUND_DURATION);
        this.waitDuration = configDAO.getWaitTime(DEFAULT_WAIT_DURATION);
    }

    // ==================== Player Management ====================

    public void addPlayer(String username) {
        if (!players.contains(username)) {
            players.add(username);
            roundWins.put(username, 0);
        }
    }

    public List<String> getPlayers() {
        return new ArrayList<>(players);
    }

    public int getPlayerCount() {
        return players.size();
    }

    // ==================== Round Management ====================

    public void initializeRound() {
        // Initialize submission list for each player (for the first round)
        roundSubmissions.clear();
        roundWinner = null;
        roundStartTime = System.currentTimeMillis();
        
        for (String player : players) {
            roundSubmissions.put(player, new ArrayList<>());
        }
    }

    public void startNewRound() {
        currentRound++;
        roundSubmissions.clear();
        roundWinner = null;
        roundStartTime = System.currentTimeMillis();
        
        // Initialize submission list for each player
        for (String player : players) {
            roundSubmissions.put(player, new ArrayList<>());
        }
    }

    public void submitWord(String username, String word) {
        if (roundSubmissions.containsKey(username)) {
            roundSubmissions.get(username).add(word);
        }
    }

    public Map<String, List<String>> getRoundSubmissions() {
        return new HashMap<>(roundSubmissions);
    }

    public void setRoundWinner(String username) {
        this.roundWinner = username;
        // Only update roundWins if username is not null
        if (username != null && roundWins.containsKey(username)) {
            roundWins.put(username, roundWins.get(username) + 1);
        }
    }

    public String getRoundWinner() {
        return roundWinner;
    }

    public Map<String, Integer> getRoundWins() {
        return new HashMap<>(roundWins);
    }

    // ==================== Game State ====================

    public void setCurrentLetters(String letters) {
        this.currentLetters = letters;
    }

    public String getCurrentLetters() {
        return currentLetters;
    }

    public int getCurrentRound() {
        return currentRound;
    }

    public boolean isGameActive() {
        return gameActive;
    }

    public void endGame() {
        this.gameActive = false;
    }

    public boolean hasGameStarted() {
        return gameStarted;
    }

    public void markGameStarted() {
        this.gameStarted = true;
    }

    public String getWinner() {
        // Find player with 3 round wins (first to reach WINS_TO_WIN_GAME)
        for (String player : players) {
            if (roundWins.getOrDefault(player, 0) >= WINS_TO_WIN_GAME) {
                return player;
            }
        }
        return null;
    }

    public boolean isGameOver() {
        return getWinner() != null;
    }

    // ==================== Time Management ====================

    public long getRoundElapsedTime() {
        return System.currentTimeMillis() - roundStartTime;
    }

    public long getRoundTimeRemaining() {
        long elapsed = getRoundElapsedTime();
        return Math.max(0, roundDuration - elapsed);
    }

    public boolean isRoundTimeUp() {
        return getRoundElapsedTime() >= roundDuration;
    }

    public int getRoundDuration() {
        return roundDuration;
    }

    public int getWaitDuration() {
        return waitDuration;
    }

    public static int getWinsToWinGame() {
        return WINS_TO_WIN_GAME;
    }

    // ==================== Getters ====================

    public String getGameId() {
        return gameId;
    }

    @Override
    public String toString() {
        return "GameSession{" +
                "gameId='" + gameId + '\'' +
                ", players=" + players +
                ", currentRound=" + currentRound +
                ", roundWins=" + roundWins +
                ", gameActive=" + gameActive +
                '}';
    }
}
