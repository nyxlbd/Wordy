package com.wordy.player.service;

import com.wordy.player.model.GameState;
import com.wordy.player.model.GameUpdate;
import com.wordy.player.model.LeaderboardData;
import com.wordy.proto.JoinGameResponse;
import com.wordy.proto.LoginResponse;
import com.wordy.proto.LogoutResponse;
import com.wordy.proto.WordResponse;
import io.grpc.stub.StreamObserver;

import java.util.ArrayList;
import java.util.List;

/**
 * Game State Manager (Singleton)
 * Responsible Team Member: NICOLE DEOCALES
 * Central manager for game state and service coordination.
 * Provides a high-level interface for UI components to interact with backend services.
 */
public class GameStateManager {
    private static GameStateManager instance;
    
    private AuthServiceClient authServiceClient;
    private GameServiceClient gameServiceClient;
    private LeaderboardServiceClient leaderboardServiceClient;
    
    private String currentUsername;
    private String currentUserRole;
    private GameState currentGameState;
    private List<GameUpdateListener> gameUpdateListeners;

    private GameStateManager() {
        this.authServiceClient = new AuthServiceClient();
        this.gameServiceClient = new GameServiceClient();
        this.leaderboardServiceClient = new LeaderboardServiceClient();
        this.gameUpdateListeners = new ArrayList<>();
    }

    /**
     * Gets the singleton instance of the game state manager.
     */
    public static synchronized GameStateManager getInstance() {
        if (instance == null) {
            instance = new GameStateManager();
        }
        return instance;
    }

    // ==================== Authentication Methods ====================

    /**
     * Attempts to login with provided credentials.
     *
     * @param username the player's username
     * @param password the player's password
     * @param isAdmin whether this is an admin login
     * @return true if login was successful
     */
    public boolean login(String username, String password, boolean isAdmin) {
        LoginResponse response = authServiceClient.login(username, password, isAdmin);
        if (response.getSuccess()) {
            this.currentUsername = username;
            this.currentUserRole = response.getUserRole();
            System.out.println("Logged in as: " + username + " (Role: " + currentUserRole + ")");
            return true;
        } else {
            System.err.println("Login failed: " + response.getMessage());
            return false;
        }
    }

    /**
     * Logs out the current user.
     *
     * @return true if logout was successful
     */
    public boolean logout() {
        if (currentUsername == null) {
            System.err.println("No user currently logged in");
            return false;
        }

        LogoutResponse response = authServiceClient.logout(currentUsername);
        if (response.getSuccess()) {
            System.out.println("Logged out: " + currentUsername);
            this.currentUsername = null;
            this.currentUserRole = null;
            this.currentGameState = null;
            return true;
        } else {
            System.err.println("Logout failed: " + response.getMessage());
            return false;
        }
    }

    /**
     * Sets the current logged-in user directly (used after UI login has already succeeded).
     * This avoids making a second login call when the UI has already authenticated the user.
     *
     * @param username the username of the logged-in player
     * @param userRole the role of the user (PLAYER, ADMIN, etc.)
     */
    public void setCurrentUser(String username, String userRole) {
        this.currentUsername = username;
        this.currentUserRole = userRole;
        System.out.println("Current user set to: " + username + " (Role: " + userRole + ")");
    }

    // ==================== Game Methods ====================

    /**
     * Joins a game with the current player.
     *
     * @return true if join was successful
     */
    public boolean joinGame() {
        if (currentUsername == null) {
            System.err.println("No user logged in");
            return false;
        }

        JoinGameResponse response = gameServiceClient.joinGame(currentUsername);
        if (response.getSuccess()) {
            System.out.println("Successfully joined game");
            // Create a new game state
            this.currentGameState = new GameState("game-" + System.currentTimeMillis(), currentUsername);
            return true;
        } else {
            System.err.println("Failed to join game: " + response.getMessage());
            return false;
        }
    }

    /**
     * Submits a word during the game.
     *
     * @param word the word to submit
     * @return true if the word is valid
     */
    public boolean submitWord(String word) {
        if (currentUsername == null || currentGameState == null) {
            System.err.println("Game not in progress");
            return false;
        }

        WordResponse response = gameServiceClient.submitWord(currentUsername, word);
        if (response.getValid()) {
            currentGameState.addSubmittedWord(word);
            System.out.println("Word submitted: " + word);
            return true;
        } else {
            System.err.println("Invalid word: " + response.getMessage());
            return false;
        }
    }

    /**
     * Starts listening to game updates from the server.
     * Responsible Team Member: NICOLE DEOCALES
     * Updates are delivered via the registered GameUpdateListeners.
     */
    public void startGameStream() {
        if (currentUsername == null) {
            System.err.println("No user logged in");
            return;
        }

        StreamObserver<GameUpdate> streamObserver = new StreamObserver<GameUpdate>() {
            @Override
            public void onNext(GameUpdate update) {
                System.out.println("Received game update: " + update.getType());
                
                // Update local game state
                if (update.getLetters() != null && !update.getLetters().isEmpty()) {
                    currentGameState.setCurrentLetters(update.getLetters());
                }
                if (update.getRoundNumber() > 0) {
                    currentGameState.setCurrentRound(update.getRoundNumber());
                }
                if (update.getWinner() != null && !update.getWinner().isEmpty()) {
                    currentGameState.setRoundWinner(update.getWinner());
                }

                currentGameState.setGameStatus(update.getType());

                // Notify all listeners
                notifyGameUpdateListeners(update);
            }

            @Override
            public void onError(Throwable t) {
                System.err.println("Game stream error: " + t.getMessage());
                notifyGameStreamError(t);
            }

            @Override
            public void onCompleted() {
                System.out.println("Game stream completed");
                currentGameState.setGameStatus("FINISHED");
            }
        };

        gameServiceClient.streamGameUpdates(currentUsername, streamObserver);
    }

    // ==================== Leaderboard Methods ====================

    /**
     * Fetches the current leaderboard data.
     *
     * @return LeaderboardData with top players and longest words
     */
    public LeaderboardData getLeaderboard() {
        return leaderboardServiceClient.getLeaderboard();
    }

    // ==================== Listener Management ====================

    /**
     * Registers a listener for game updates.
     */
    public void addGameUpdateListener(GameUpdateListener listener) {
        gameUpdateListeners.add(listener);
    }

    /**
     * Removes a game update listener.
     */
    public void removeGameUpdateListener(GameUpdateListener listener) {
        gameUpdateListeners.remove(listener);
    }

    /**
     * Notifies all listeners of a game update.
     */
    private void notifyGameUpdateListeners(GameUpdate update) {
        for (GameUpdateListener listener : gameUpdateListeners) {
            listener.onGameUpdate(update);
        }
    }

    /**
     * Notifies all listeners of a stream error.
     */
    private void notifyGameStreamError(Throwable error) {
        for (GameUpdateListener listener : gameUpdateListeners) {
            listener.onGameStreamError(error);
        }
    }

    // ==================== State Getters ====================

    public String getCurrentUsername() {
        return currentUsername;
    }

    public String getCurrentUserRole() {
        return currentUserRole;
    }

    public GameState getCurrentGameState() {
        return currentGameState;
    }

    public boolean isLoggedIn() {
        return currentUsername != null;
    }

    public boolean isInGame() {
        return currentGameState != null;
    }

    // ==================== Shutdown ====================

    /**
     * Gracefully shuts down the manager and closes connections.
     */
    public void shutdown() {
        if (currentUsername != null) {
            logout();
        }
        GrpcConnectionManager.getInstance().shutdown();
    }

    // ==================== Listener Interface ====================

    /**
     * Interface for components that want to listen to game updates.
     */
    public interface GameUpdateListener {
        void onGameUpdate(GameUpdate update);
        void onGameStreamError(Throwable error);
    }
}
