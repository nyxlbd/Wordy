package com.wordy.player.service;

import com.wordy.player.model.GameUpdate;
import com.wordy.proto.GameServiceGrpc;
import com.wordy.proto.JoinGameRequest;
import com.wordy.proto.JoinGameResponse;
import com.wordy.proto.GameRequest;
import com.wordy.proto.WordRequest;
import com.wordy.proto.WordResponse;
import io.grpc.Channel;
import io.grpc.stub.StreamObserver;

/**
 * Game Service Client
 * Responsible Team Member: NICOLE DEOCALES
 * Wrapper client for gRPC GameService.
 * Handles game operations: join game, submit words, and stream game updates.
 */
public class GameServiceClient {
    private GameServiceGrpc.GameServiceBlockingStub gameBlockingStub;
    private GameServiceGrpc.GameServiceStub gameAsyncStub;

    public GameServiceClient() {
        Channel channel = GrpcConnectionManager.getInstance().getChannel();
        this.gameBlockingStub = GameServiceGrpc.newBlockingStub(channel);
        this.gameAsyncStub = GameServiceGrpc.newStub(channel);
    }

    /**
     * Sends a request to join the game.
     *
     * @param username the player's username
     * @return JoinGameResponse containing success status and message
     */
    public JoinGameResponse joinGame(String username) {
        try {
            JoinGameRequest request = JoinGameRequest.newBuilder()
                    .setUsername(username)
                    .build();

            JoinGameResponse response = gameBlockingStub.joinGame(request);
            System.out.println("Join game response: " + response.getMessage());
            return response;

        } catch (Exception e) {
            System.err.println("Error joining game: " + e.getMessage());
            return JoinGameResponse.newBuilder()
                    .setSuccess(false)
                    .setMessage("Connection error: " + e.getMessage())
                    .build();
        }
    }

    /**
     * Submits a word to the server during the game.
     *
     * @param username the player's username
     * @param word the word to submit
     * @return WordResponse containing validation status
     */
    public WordResponse submitWord(String username, String word) {
        try {
            WordRequest request = WordRequest.newBuilder()
                    .setUsername(username)
                    .setWord(word)
                    .build();

            WordResponse response = gameBlockingStub.submitWord(request);
            System.out.println("Word response: " + response.getMessage());
            return response;

        } catch (Exception e) {
            System.err.println("Error submitting word: " + e.getMessage());
            return WordResponse.newBuilder()
                    .setValid(false)
                    .setMessage("Connection error: " + e.getMessage())
                    .build();
        }
    }

    /**
     * Initiates a server-streaming connection to receive game updates.
     * Updates are delivered to the provided StreamObserver callback.
     *
     * @param username the player's username
     * @param updateCallback called when server sends game updates
     */
    public void streamGameUpdates(String username, StreamObserver<GameUpdate> updateCallback) {
        try {
            GameRequest request = GameRequest.newBuilder()
                    .setUsername(username)
                    .build();

            // Create a StreamObserver that converts proto GameUpdate to model GameUpdate
            StreamObserver<com.wordy.proto.GameUpdate> protoObserver = new StreamObserver<com.wordy.proto.GameUpdate>() {
                @Override
                public void onNext(com.wordy.proto.GameUpdate protoUpdate) {
                    // Convert proto GameUpdate to model GameUpdate
                    GameUpdate modelUpdate = new GameUpdate(
                            protoUpdate.getType(),
                            protoUpdate.getMessage(),
                            protoUpdate.getLetters(),
                            protoUpdate.getWinner(),
                            protoUpdate.getRoundNumber()
                    );
                    modelUpdate.setRoundDuration(protoUpdate.getRoundDuration());
                    updateCallback.onNext(modelUpdate);
                }

                @Override
                public void onError(Throwable t) {
                    System.err.println("Stream error: " + t.getMessage());
                    updateCallback.onError(t);
                }

                @Override
                public void onCompleted() {
                    System.out.println("Game stream completed");
                    updateCallback.onCompleted();
                }
            };

            // Start the server streaming
            gameAsyncStub.streamGame(request, protoObserver);
            System.out.println("Started streaming game updates for user: " + username);

        } catch (Exception e) {
            System.err.println("Error starting game stream: " + e.getMessage());
            updateCallback.onError(e);
        }
    }
}
