package com.wordy.server.service;

import com.wordy.proto.AdminServiceGrpc;
import com.wordy.proto.Player;
import com.wordy.proto.PlayerId;
import com.wordy.proto.SearchQuery;
import com.wordy.proto.PlayerList;
import com.wordy.proto.Config;
import com.wordy.proto.Status;
import com.wordy.proto.Empty;
import com.wordy.server.dao.UserDAO;
import com.wordy.server.dao.ConfigDAO;
import io.grpc.stub.StreamObserver;

/**
 * Admin Service Backend Implementation
 * Responsible Team Members:
 *   - Admin Dashboard Backend: KATHRINA SHAYNE RAGOS (overall admin operations)
 *   - Manage Players Backend: CHARLES KENNETH DESEAR (player CRUD operations)
 *   - Game Setting Backend: CHARLES KENNETH DESEAR (configuration management)
 * Handles player management and game configuration updates.
 */
public class AdminServiceImpl extends AdminServiceGrpc.AdminServiceImplBase {
    
    private final UserDAO userDAO = new UserDAO();
    private final ConfigDAO configDAO = new ConfigDAO();

    /**
     * Creates a new player account.
     * Responsible Team Member: CHARLES KENNETH DESEAR (Manage Players Backend)
     * 
     * @param request Contains new player username and password
     * @param responseObserver Observer for sending creation status
     */
    @Override
    public void createPlayer(Player request, StreamObserver<Status> responseObserver) {
        String username = request.getUsername();
        String password = request.getPassword();

        try {
            // Check if player already exists
            if (userDAO.userExists(username)) {
                responseObserver.onNext(Status.newBuilder()
                        .setSuccess(false)
                        .setMessage("Player " + username + " already exists")
                        .build());
                responseObserver.onCompleted();
                return;
            }

            // Create new player with default role "player"
            boolean created = userDAO.createUser(username, password, "player");
            
            if (created) {
                responseObserver.onNext(Status.newBuilder()
                        .setSuccess(true)
                        .setMessage("Player " + username + " created successfully")
                        .build());
            } else {
                responseObserver.onNext(Status.newBuilder()
                        .setSuccess(false)
                        .setMessage("Failed to create player " + username)
                        .build());
            }

            responseObserver.onCompleted();

        } catch (Exception e) {
            e.printStackTrace();
            responseObserver.onNext(Status.newBuilder()
                    .setSuccess(false)
                    .setMessage("Error creating player: " + e.getMessage())
                    .build());
            responseObserver.onCompleted();
        }
    }

    /**
     * Updates an existing player account information.
     * Responsible Team Member: CHARLES KENNETH DESEAR (Manage Players Backend)
     * 
     * @param request Contains username and updated password
     * @param responseObserver Observer for sending update status
     */
    @Override
    public void updatePlayer(Player request, StreamObserver<Status> responseObserver) {
        String username = request.getUsername();
        String password = request.getPassword();

        try {
            // Check if player exists
            if (!userDAO.userExists(username)) {
                responseObserver.onNext(Status.newBuilder()
                        .setSuccess(false)
                        .setMessage("Player " + username + " does not exist")
                        .build());
                responseObserver.onCompleted();
                return;
            }

            // Update player (password and role)
            boolean updated = userDAO.updateUser(username, password, "player");
            
            if (updated) {
                responseObserver.onNext(Status.newBuilder()
                        .setSuccess(true)
                        .setMessage("Player " + username + " updated successfully")
                        .build());
            } else {
                responseObserver.onNext(Status.newBuilder()
                        .setSuccess(false)
                        .setMessage("Failed to update player " + username)
                        .build());
            }

            responseObserver.onCompleted();

        } catch (Exception e) {
            e.printStackTrace();
            responseObserver.onNext(Status.newBuilder()
                    .setSuccess(false)
                    .setMessage("Error updating player: " + e.getMessage())
                    .build());
            responseObserver.onCompleted();
        }
    }

    /**
     * Deletes a player account from the system.
     * Responsible Team Member: CHARLES KENNETH DESEAR (Manage Players Backend)
     * 
     * @param request Contains player ID
     * @param responseObserver Observer for sending deletion status
     */
    @Override
    public void deletePlayer(PlayerId request, StreamObserver<Status> responseObserver) {
        try {
            int playerId = request.getId();
            
            // Get username from ID
            String username = userDAO.getUsernameById(playerId);
            
            if (username == null) {
                responseObserver.onNext(Status.newBuilder()
                        .setSuccess(false)
                        .setMessage("Player with ID " + playerId + " not found")
                        .build());
                responseObserver.onCompleted();
                return;
            }
            
            // Delete the user
            boolean deleted = userDAO.deleteUser(username);
            
            if (deleted) {
                responseObserver.onNext(Status.newBuilder()
                        .setSuccess(true)
                        .setMessage("Player " + username + " deleted successfully")
                        .build());
            } else {
                responseObserver.onNext(Status.newBuilder()
                        .setSuccess(false)
                        .setMessage("Failed to delete player " + username)
                        .build());
            }
            
            responseObserver.onCompleted();
            
        } catch (Exception e) {
            e.printStackTrace();
            responseObserver.onNext(Status.newBuilder()
                    .setSuccess(false)
                    .setMessage("Error deleting player: " + e.getMessage())
                    .build());
            responseObserver.onCompleted();
        }
    }

    /**
     * Searches for players by username pattern.
     * Responsible Team Member: CHARLES KENNETH DESEAR (Manage Players Backend)
     * 
     * @param request Contains search query/username pattern
     * @param responseObserver Observer for sending list of matching players
     */
    @Override
    public void searchPlayer(SearchQuery request, StreamObserver<PlayerList> responseObserver) {
        try {
            String keyword = request.getKeyword();
            java.util.List<Object[]> playerList = userDAO.searchPlayers(keyword);
            
            PlayerList.Builder playerListBuilder = PlayerList.newBuilder();
            
            for (Object[] playerData : playerList) {
                int id = (int) playerData[0];
                String username = (String) playerData[1];
                int wins = (int) playerData[2];
                
                Player player = Player.newBuilder()
                        .setId(id)
                        .setUsername(username)
                        .setWins(wins)
                        .build();
                        
                playerListBuilder.addPlayers(player);
            }
            
            responseObserver.onNext(playerListBuilder.build());
            responseObserver.onCompleted();
            
        } catch (Exception e) {
            e.printStackTrace();
            responseObserver.onNext(PlayerList.newBuilder().build());
            responseObserver.onCompleted();
        }
    }

    /**
     * Updates game configuration settings.
     * Responsible Team Member: CHARLES KENNETH DESEAR (Game Setting Backend)
     * 
     * @param request Contains new wait_time and round_duration values
     * @param responseObserver Observer for sending update status
     */
    @Override
    public void updateConfig(Config request, StreamObserver<Status> responseObserver) {
        try {
            int waitTime = request.getWaitTime();
            int roundDuration = request.getRoundDuration();

            // Validate values
            if (waitTime < 0 || roundDuration < 0) {
                responseObserver.onNext(Status.newBuilder()
                        .setSuccess(false)
                        .setMessage("Invalid config values: wait_time and round_duration must be non-negative")
                        .build());
                responseObserver.onCompleted();
                return;
            }

            // Update configuration
            boolean updated = configDAO.updateConfig("wait_time", String.valueOf(waitTime))
                    && configDAO.updateConfig("round_duration", String.valueOf(roundDuration));

            if (updated) {
                responseObserver.onNext(Status.newBuilder()
                        .setSuccess(true)
                        .setMessage("Configuration updated: wait_time=" + waitTime + "s, round_duration=" + roundDuration + "s")
                        .build());
            } else {
                responseObserver.onNext(Status.newBuilder()
                        .setSuccess(false)
                        .setMessage("Failed to update configuration")
                        .build());
            }

            responseObserver.onCompleted();

        } catch (Exception e) {
            e.printStackTrace();
            responseObserver.onNext(Status.newBuilder()
                    .setSuccess(false)
                    .setMessage("Error updating config: " + e.getMessage())
                    .build());
            responseObserver.onCompleted();
        }
    }

    @Override
    public void getConfig(Empty request, StreamObserver<Config> responseObserver) {
        try {
            int waitTime = configDAO.getConfigAsInt("wait_time", 10);
            int roundDuration = configDAO.getConfigAsInt("round_duration", 30);

            responseObserver.onNext(Config.newBuilder()
                    .setWaitTime(waitTime)
                    .setRoundDuration(roundDuration)
                    .build());
            responseObserver.onCompleted();

        } catch (Exception e) {
            e.printStackTrace();
            responseObserver.onError(new Exception("Error fetching config: " + e.getMessage()));
        }
    }
}
