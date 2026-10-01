package com.wordy.admin.service;

import io.grpc.Channel;
import java.util.ArrayList;
import java.util.List;
import com.wordy.proto.AdminServiceGrpc;
import com.wordy.proto.AdminServiceGrpc.AdminServiceBlockingStub;
import com.wordy.proto.Player;
import com.wordy.proto.PlayerId;
import com.wordy.proto.SearchQuery;
import com.wordy.proto.PlayerList;
import com.wordy.proto.Status;
import com.wordy.proto.Config;
import com.wordy.proto.Empty;

/**
 * AdminServiceClient
 * Responsible Team Member: CHARLES KENNETH DESEAR
 * Client wrapper for admin gRPC service - handles player management and game settings
 */
public class AdminServiceClient {

    private Channel channel;
    private AdminServiceBlockingStub adminStub;

    /**
     * Constructor for AdminServiceClient
     * Responsible Team Member: CHARLES KENNETH DESEAR
     * Initializes connection to admin service backend
     */
    public AdminServiceClient() {
        this.channel = GrpcConnectionManager.getInstance().getChannel();
        this.adminStub = AdminServiceGrpc.newBlockingStub(channel);
    }

    /**
     * Fetches all players from the server
     * Responsible Team Member: CHARLES KENNETH DESEAR
     * 
     * @return List of player data containing username, status, wins, and join date
     */
    public List<PlayerData> getAllPlayers() {
        List<PlayerData> players = new ArrayList<>();
        try {
            SearchQuery request = SearchQuery.newBuilder()
                .setKeyword("")
                .build();
            
            PlayerList response = adminStub.searchPlayer(request);
            
            if (response != null && response.getPlayersCount() > 0) {
                for (Player player : response.getPlayersList()) {
                    players.add(new PlayerData(
                        player.getUsername(),
                        "Active",
                        player.getWins(),
                        String.valueOf(player.getId())
                    ));
                }
            }
            
            System.out.println("Fetched " + players.size() + " players from server");
            return players;
            
        } catch (Exception e) {
            System.err.println("Error fetching players: " + e.getMessage());
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    /**
     * Searches for players by username
     * Responsible Team Member: CHARLES KENNETH DESEAR
     * 
     * @param query search term for username
     * @return List of matching players
     */
    public List<PlayerData> searchPlayers(String query) {
        List<PlayerData> results = new ArrayList<>();
        try {
            if (query == null || query.trim().isEmpty()) {
                return getAllPlayers(); // Return all if empty query
            }
            
            SearchQuery request = SearchQuery.newBuilder()
                .setKeyword(query.trim())
                .build();
            
            PlayerList response = adminStub.searchPlayer(request);
            
            if (response != null && response.getPlayersCount() > 0) {
                for (Player player : response.getPlayersList()) {
                    results.add(new PlayerData(
                        player.getUsername(),
                        "Active",
                        player.getWins(),
                        String.valueOf(player.getId())
                    ));
                }
            }
            
            System.out.println("Found " + results.size() + " players matching query: " + query);
            return results;
            
        } catch (Exception e) {
            System.err.println("Error searching players: " + e.getMessage());
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    /**
     * Creates a new player account
     * Responsible Team Member: CHARLES KENNETH DESEAR
     * 
     * @param username new player username
     * @param password player password
     * @return success status
     */
    public boolean createPlayer(String username, String password) {
        try {
            if (username == null || username.trim().isEmpty() || 
                password == null || password.trim().isEmpty()) {
                System.err.println("Username and password cannot be empty");
                return false;
            }
            
            Player newPlayer = Player.newBuilder()
                .setId(0) // Server will assign ID
                .setUsername(username.trim())
                .setPassword(password.trim())
                .setWins(0)
                .build();
            
            Status response = adminStub.createPlayer(newPlayer);
            
            if (response != null && response.getSuccess()) {
                System.out.println("Player created successfully: " + username);
                return true;
            } else {
                System.err.println("Failed to create player: " + (response != null ? response.getMessage() : "Unknown error"));
                return false;
            }
            
        } catch (Exception e) {
            System.err.println("Error creating player: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Updates an existing player account
     * Responsible Team Member: CHARLES KENNETH DESEAR
     * 
     * @param username player to update
     * @param password new password
     * @param status new status
     * @return success status
     */
    public boolean updatePlayer(String username, String password, String status) {
        try {
            if (username == null || username.trim().isEmpty()) {
                System.err.println("Username cannot be empty");
                return false;
            }
            
            // Search for player to get their ID
            SearchQuery searchRequest = SearchQuery.newBuilder()
                .setKeyword(username.trim())
                .build();
            PlayerList searchResponse = adminStub.searchPlayer(searchRequest);
            
            if (searchResponse == null || searchResponse.getPlayersCount() == 0) {
                System.err.println("Player not found: " + username);
                return false;
            }
            
            Player existingPlayer = searchResponse.getPlayers(0);
            
            Player updatedPlayer = Player.newBuilder()
                .setId(existingPlayer.getId())
                .setUsername(username.trim())
                .setPassword(password != null ? password.trim() : existingPlayer.getPassword())
                .setWins(existingPlayer.getWins())
                .build();
            
            Status response = adminStub.updatePlayer(updatedPlayer);
            
            if (response != null && response.getSuccess()) {
                System.out.println("Player updated successfully: " + username);
                return true;
            } else {
                System.err.println("Failed to update player: " + (response != null ? response.getMessage() : "Unknown error"));
                return false;
            }
            
        } catch (Exception e) {
            System.err.println("Error updating player: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Deletes a player account
     * Responsible Team Member: CHARLES KENNETH DESEAR
     * 
     * @param username player to delete
     * @return success status
     */
    public boolean deletePlayer(String username) {
        try {
            if (username == null || username.trim().isEmpty()) {
                System.err.println("Username cannot be empty");
                return false;
            }
            
            // Search for player to get their ID
            SearchQuery searchRequest = SearchQuery.newBuilder()
                .setKeyword(username.trim())
                .build();
            PlayerList searchResponse = adminStub.searchPlayer(searchRequest);
            
            if (searchResponse == null || searchResponse.getPlayersCount() == 0) {
                System.err.println("Player not found: " + username);
                return false;
            }
            
            int playerId = searchResponse.getPlayers(0).getId();
            
            PlayerId request = PlayerId.newBuilder()
                .setId(playerId)
                .build();
            
            Status response = adminStub.deletePlayer(request);
            
            if (response != null && response.getSuccess()) {
                System.out.println("Player deleted successfully: " + username);
                return true;
            } else {
                System.err.println("Failed to delete player: " + (response != null ? response.getMessage() : "Unknown error"));
                return false;
            }
            
        } catch (Exception e) {
            System.err.println("Error deleting player: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Fetches current game configuration settings
     * Responsible Team Member: CHARLES KENNETH DESEAR
     * 
     * @return GameSettings containing waiting time and round duration
     */
    public GameSettings getGameSettings() {
        try {
            Empty request = Empty.getDefaultInstance();
            Config response = adminStub.getConfig(request);
            
            if (response != null) {
                int waitTime = response.getWaitTime();
                int roundDuration = response.getRoundDuration();
                System.out.println("Retrieved game settings - Waiting: " + waitTime + "s, Round: " + roundDuration + "s");
                return new GameSettings(waitTime, roundDuration);
            }
            
            System.out.println("Using default game settings - Waiting: 10s, Round: 30s");
            return new GameSettings(10, 30);
            
        } catch (Exception e) {
            System.err.println("Error fetching settings: " + e.getMessage());
            e.printStackTrace();
            return new GameSettings(10, 30);
        }
    }

    /**
     * Updates game configuration settings
     * Responsible Team Member: CHARLES KENNETH DESEAR
     * 
     * @param waitingTime time before game starts (seconds)
     * @param roundDuration duration of each round (seconds)
     * @return success status
     */
    public boolean updateGameSettings(int waitingTime, int roundDuration) {
        try {
            if (waitingTime <= 0 || roundDuration <= 0) {
                System.err.println("Waiting time and round duration must be greater than 0");
                return false;
            }
            
            Config newConfig = Config.newBuilder()
                .setWaitTime(waitingTime)
                .setRoundDuration(roundDuration)
                .build();
            
            Status response = adminStub.updateConfig(newConfig);
            
            if (response != null && response.getSuccess()) {
                System.out.println("Game settings updated - Waiting: " + waitingTime + "s, Round: " + roundDuration + "s");
                return true;
            } else {
                System.err.println("Failed to update settings: " + (response != null ? response.getMessage() : "Unknown error"));
                return false;
            }
            
        } catch (Exception e) {
            System.err.println("Error updating settings: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Data transfer object for player information
     */
    public static class PlayerData {
        public String username;
        public String status;
        public int wins;
        public String playerId;

        public PlayerData(String username, String status, int wins, String playerId) {
            this.username = username;
            this.status = status;
            this.wins = wins;
            this.playerId = playerId;
        }
    }

    /**
     * Data transfer object for game settings
     */
    public static class GameSettings {
        public int waitingTime;
        public int roundDuration;

        public GameSettings(int waitingTime, int roundDuration) {
            this.waitingTime = waitingTime;
            this.roundDuration = roundDuration;
        }
    }

    /**
     * Closes the gRPC connection
     * Responsible Team Member: CHARLES KENNETH DESEAR
     */
    public void shutdown() {
        // Channel is managed by GrpcConnectionManager, don't close it directly
        System.out.println("AdminServiceClient disconnecting from server");
    }
}
