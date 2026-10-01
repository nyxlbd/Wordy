package com.wordy.server.service;

import com.wordy.server.model.GameSession;
import com.wordy.server.dao.ConfigDAO;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Manages all active game sessions and waiting players.
 * Handles game creation, player matching, and game state tracking.
 */
public class GameManager {
    private static GameManager instance;
    
    private Map<String, GameSession> activeSessions; // gameId -> GameSession
    private Map<String, String> playerToGame; // username -> gameId
    private Queue<String> waitingPlayers; // Players waiting to join a game
    private long waitStartTime;
    private int waitDuration; // Milliseconds - loaded from database
    private static final int DEFAULT_WAIT_DURATION = 10; // Default in seconds
    private static final int MIN_PLAYERS_TO_START = 2;

    private GameManager() {
        this.activeSessions = new ConcurrentHashMap<>();
        this.playerToGame = new ConcurrentHashMap<>();
        this.waitingPlayers = new LinkedList<>();
        
        // Load configuration from database
        ConfigDAO configDAO = new ConfigDAO();
        this.waitDuration = configDAO.getWaitTime(DEFAULT_WAIT_DURATION);
    }

    /**
     * Gets the singleton instance of GameManager.
     */
    public static synchronized GameManager getInstance() {
        if (instance == null) {
            instance = new GameManager();
        }
        return instance;
    }

    // ==================== Game Session Management ====================

    /**
     * Initiates a new game with the first player.
     *
     * @param username the player initiating the game
     * @return gameId of the new game session
     */
    public String createGame(String username) {
        String gameId = "game-" + System.nanoTime();
        GameSession session = new GameSession(gameId, username);
        
        activeSessions.put(gameId, session);
        playerToGame.put(username, gameId);
        waitingPlayers.clear();
        waitingPlayers.add(username);
        waitStartTime = System.currentTimeMillis();
        
        System.out.println("Game created: " + gameId + " by " + username);
        return gameId;
    }

    /**
     * Joins a player to an existing waiting game.
     *
     * @param username the player joining
     * @return gameId if successful, null if no game available
     */
    public String joinGame(String username) {
        // Check if waiting players queue is not empty and wait time hasn't exceeded
        if (!waitingPlayers.isEmpty() && !isWaitTimeExceeded()) {
            // Get the game from the first waiting player
            String firstPlayer = waitingPlayers.peek();
            String gameId = playerToGame.get(firstPlayer);
            
            if (gameId != null && activeSessions.containsKey(gameId)) {
                GameSession session = activeSessions.get(gameId);
                session.addPlayer(username);
                playerToGame.put(username, gameId);
                waitingPlayers.remove(); // Remove first player from waiting queue
                
                System.out.println("Player " + username + " joined game " + gameId);
                
                // Check if game can start with the new player count
                if (session.getPlayerCount() >= MIN_PLAYERS_TO_START && !session.hasGameStarted()) {
                    session.markGameStarted();
                    System.out.println("Game " + gameId + " started with " + session.getPlayerCount() + " players");
                }
                
                return gameId;
            }
        }
        
        // No game available, create new game for this player
        return createGame(username);
    }

    /**
     * Gets the game session for a player.
     *
     * @param username the player
     * @return GameSession or null if player is not in a game
     */
    public GameSession getPlayerGame(String username) {
        String gameId = playerToGame.get(username);
        if (gameId != null) {
            return activeSessions.get(gameId);
        }
        return null;
    }

    /**
     * Gets a specific game session.
     *
     * @param gameId the game ID
     * @return GameSession or null if not found
     */
    public GameSession getGameSession(String gameId) {
        return activeSessions.get(gameId);
    }

    /**
     * Removes a game session (called when game ends).
     *
     * @param gameId the game ID to remove
     */
    public void removeGameSession(String gameId) {
        GameSession session = activeSessions.remove(gameId);
        if (session != null) {
            for (String player : session.getPlayers()) {
                playerToGame.remove(player);
            }
            System.out.println("Game session removed: " + gameId);
        }
    }

    // ==================== Waiting Status ====================

    /**
     * Checks if a game is still waiting for players.
     *
     * @param gameId the game ID
     * @return true if game is waiting for a second player
     */
    public boolean isGameWaiting(String gameId) {
        GameSession session = activeSessions.get(gameId);
        return session != null && session.getPlayerCount() == 1 && !isWaitTimeExceeded();
    }

    /**
     * Checks if the wait time for a game has exceeded the limit.
     *
     * @return true if wait time exceeded
     */
    private boolean isWaitTimeExceeded() {
        return (System.currentTimeMillis() - waitStartTime) > waitDuration;
    }

    /**
     * Gets the time remaining for players to join.
     *
     * @return milliseconds remaining
     */
    public long getWaitTimeRemaining() {
        long elapsed = System.currentTimeMillis() - waitStartTime;
        return Math.max(0, waitDuration - elapsed);
    }

    /**
     * Gets the number of waiting players.
     *
     * @return count of waiting players
     */
    public int getWaitingPlayerCount() {
        return waitingPlayers.size();
    }

    // ==================== Utility ====================

    /**
     * Gets all active games.
     *
     * @return collection of GameSession objects
     */
    public Collection<GameSession> getAllActiveSessions() {
        return new ArrayList<>(activeSessions.values());
    }

    /**
     * Checks if a player is in an active game.
     *
     * @param username the player
     * @return true if player is in a game
     */
    public boolean isPlayerInGame(String username) {
        return playerToGame.containsKey(username);
    }

    /**
     * Gets all players in a specific game.
     *
     * @param gameId the game ID
     * @return list of player usernames
     */
    public List<String> getGamePlayers(String gameId) {
        GameSession session = activeSessions.get(gameId);
        return session != null ? session.getPlayers() : new ArrayList<>();
    }
}
