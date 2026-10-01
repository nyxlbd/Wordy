package com.wordy.server.service;

import com.wordy.proto.GameServiceGrpc;
import com.wordy.proto.JoinGameRequest;
import com.wordy.proto.JoinGameResponse;
import com.wordy.proto.GameRequest;
import com.wordy.proto.WordRequest;
import com.wordy.proto.WordResponse;
import com.wordy.proto.GameUpdate;
import com.wordy.server.model.GameSession;
import com.wordy.server.util.LetterGenerator;
import com.wordy.server.util.WordValidator;
import com.wordy.server.dao.UserDAO;
import com.wordy.server.dao.WordDAO;
import com.wordy.server.dao.GameResultDAO;
import io.grpc.stub.StreamObserver;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Game Service Backend Implementation
 * Responsible Team Members:
 *   - Game Screen Backend: NICOLE DEOCALES (overall game mechanics, word submission)
 *   - Game Lobby Backend: KATHRINA SHAYNE RAGOS (game joining, lobby management)
 * Handles: joining games, submitting words, streaming game updates
 */
public class GameServiceImpl extends GameServiceGrpc.GameServiceImplBase {
    
    private final GameManager gameManager = GameManager.getInstance();
    private final WordValidator wordValidator = WordValidator.getInstance();
    private final UserDAO userDAO = new UserDAO();
    private final WordDAO wordDAO = new WordDAO();
    private final GameResultDAO gameResultDAO = new GameResultDAO();
    
    // Track client streams for sending updates: gameId -> list of StreamObservers
    private Map<String, List<StreamObserver<GameUpdate>>> gameStreams = new ConcurrentHashMap<>();

    /**
     * Handles player joining a game or creating a new game session.
     * Responsible Team Member: KATHRINA SHAYNE RAGOS (Game Lobby Backend)
     * 
     * @param request Contains username of player joining
     * @param responseObserver Observer for sending join response
     */
    @Override
    public void joinGame(JoinGameRequest request, StreamObserver<JoinGameResponse> responseObserver) {
        String username = request.getUsername();

        try {
            // Join or create a game
            String gameId = gameManager.joinGame(username);
            GameSession session = gameManager.getGameSession(gameId);

            if (session == null) {
                responseObserver.onNext(JoinGameResponse.newBuilder()
                        .setSuccess(false)
                        .setMessage("Failed to create game session")
                        .build());
                responseObserver.onCompleted();
                return;
            }

            // If this is the first player, they wait
            if (session.getPlayerCount() == 1) {
                responseObserver.onNext(JoinGameResponse.newBuilder()
                        .setSuccess(true)
                        .setMessage("Game created. Waiting for another player...")
                        .build());
            } else if (session.getPlayerCount() == 2) {
                // Start the game!
                startGame(gameId);
                responseObserver.onNext(JoinGameResponse.newBuilder()
                        .setSuccess(true)
                        .setMessage("Game started! " + String.join(" vs ", session.getPlayers()))
                        .build());
            }

            responseObserver.onCompleted();

        } catch (Exception e) {
            e.printStackTrace();
            responseObserver.onNext(JoinGameResponse.newBuilder()
                    .setSuccess(false)
                    .setMessage("Error joining game: " + e.getMessage())
                    .build());
            responseObserver.onCompleted();
        }
    }

    /**
     * Processes word submission from a player during active game.
     * Responsible Team Member: NICOLE DEOCALES (Game Screen Backend)
     * 
     * Validates word against available letters, saves to database, and sends response
     * 
     * @param request Contains username and word submission
     * @param responseObserver Observer for sending word validation response
     */
    @Override
    public void submitWord(WordRequest request, StreamObserver<WordResponse> responseObserver) {
        String username = request.getUsername();
        String word = request.getWord();

        try {
            GameSession session = gameManager.getPlayerGame(username);
            
            if (session == null) {
                responseObserver.onNext(WordResponse.newBuilder()
                        .setValid(false)
                        .setMessage("You are not in an active game")
                        .build());
                responseObserver.onCompleted();
                return;
            }

            // Validate word
            WordValidator.ValidationResult result = wordValidator.validateWord(
                    word, 
                    session.getCurrentLetters()
            );

            if (result.valid) {
                session.submitWord(username, word);
                
                // Save the word to the database
                boolean saved = wordDAO.saveWord(username, word);
                if (saved) {
                    System.out.println("Word saved to database: " + username + " - " + word);
                } else {
                    System.err.println("Failed to save word to database: " + username + " - " + word);
                }
                
                responseObserver.onNext(WordResponse.newBuilder()
                        .setValid(true)
                        .setMessage("Word submitted: " + word + " (" + word.length() + " letters)")
                        .build());
            } else {
                responseObserver.onNext(WordResponse.newBuilder()
                        .setValid(false)
                        .setMessage(result.message)
                        .build());
            }

            responseObserver.onCompleted();

        } catch (Exception e) {
            e.printStackTrace();
            responseObserver.onNext(WordResponse.newBuilder()
                    .setValid(false)
                    .setMessage("Error processing word: " + e.getMessage())
                    .build());
            responseObserver.onCompleted();
        }
    }

    /**
     * Establishes a bidirectional stream for real-time game updates.
     * Responsible Team Member: NICOLE DEOCALES (Game Screen Backend)
     * 
     * Registers player stream, sends initial game state, and maintains connection for updates
     * 
     * @param request Contains username of player
     * @param responseObserver Observer for sending continuous game updates
     */
    @Override
    public void streamGame(GameRequest request, StreamObserver<GameUpdate> responseObserver) {
        String username = request.getUsername();

        try {
            GameSession session = gameManager.getPlayerGame(username);
            
            if (session == null) {
                responseObserver.onError(new Exception("Not in an active game"));
                return;
            }

            String gameId = session.getGameId();
            
            // Register this stream
            gameStreams.computeIfAbsent(gameId, k -> new ArrayList<>()).add(responseObserver);
            System.out.println("Player " + username + " connected to game stream: " + gameId);

            // If game has already started, send the START update to this player
            if (session.hasGameStarted()) {
                String currentLetters = session.getCurrentLetters();
                System.out.println("DEBUG: streamGame - game started, sending START with letters: " + currentLetters);
                responseObserver.onNext(GameUpdate.newBuilder()
                        .setType("START")
                        .setMessage("Game is starting! You have " + (session.getRoundDuration() / 1000) + " seconds per round.")
                        .setLetters(currentLetters)
                        .setRoundNumber(session.getCurrentRound())
                        .setRoundDuration((int)(session.getRoundDuration() / 1000))
                        .build());
            } else {
                // Send initial WAITING update
                System.out.println("DEBUG: streamGame - game not started, sending WAITING");
                responseObserver.onNext(GameUpdate.newBuilder()
                        .setType("WAITING")
                        .setMessage("Waiting for another player to join...")
                        .setRoundNumber(session.getCurrentRound())
                        .build());
            }

            // Note: Game loop is started by startGame() when the second player joins via joinGame()

        } catch (Exception e) {
            e.printStackTrace();
            responseObserver.onError(e);
        }
    }

    // ==================== Private Helper Methods ====================

    /**
     * Starts a game: generates letters and broadcasts START update, and initiates the game loop.
     */
    private void startGame(String gameId) {
        GameSession session = gameManager.getGameSession(gameId);
        if (session != null) {
            String letters = LetterGenerator.generateLetters();
            session.setCurrentLetters(letters);
            session.markGameStarted(); // Mark that game has started
            
            System.out.println("DEBUG: Starting game " + gameId + " with letters: " + letters);
            
            broadcastGameUpdate(gameId, GameUpdate.newBuilder()
                    .setType("START")
                    .setMessage("Game is starting! You have " + (session.getRoundDuration() / 1000) + " seconds per round.")
                    .setLetters(letters)
                    .setRoundNumber(session.getCurrentRound())
                    .setRoundDuration((int)(session.getRoundDuration() / 1000))
                    .build());
            
            // Start the game loop in a new thread
            new Thread(() -> runGameLoop(gameId)).start();
        }
    }

    /**
     * Main game loop: handles rounds, determines winners, ends game.
     */
    private void runGameLoop(String gameId) {
        GameSession session = gameManager.getGameSession(gameId);
        if (session == null) return;

        try {
            // Initialize roundSubmissions for the first round
            session.initializeRound();
            
            while (!session.isGameOver()) {
                // Send round start with letters
                String letters = LetterGenerator.generateLetters();
                session.setCurrentLetters(letters);
                
                broadcastGameUpdate(gameId, GameUpdate.newBuilder()
                        .setType("ROUND")
                        .setMessage("Round " + session.getCurrentRound() + " started!")
                        .setLetters(letters)
                        .setRoundNumber(session.getCurrentRound())
                        .setRoundDuration((int)(session.getRoundDuration() / 1000))
                        .build());

                // Wait for round duration
                Thread.sleep(session.getRoundDuration());

                // Determine round winner
                String roundWinner = determineRoundWinner(session);
                session.setRoundWinner(roundWinner);

                // Send result
                String resultMessage;
                if (roundWinner != null) {
                    resultMessage = roundWinner + " won this round with their word!";
                } else {
                    resultMessage = "No winner this round. No valid words or tie.";
                }

                broadcastGameUpdate(gameId, GameUpdate.newBuilder()
                        .setType("RESULT")
                        .setMessage(resultMessage)
                        .setWinner(roundWinner != null ? roundWinner : "")
                        .setRoundNumber(session.getCurrentRound())
                        .build());

                // Pause between rounds
                Thread.sleep(2000);

                // Check if game is over
                if (session.isGameOver()) {
                    String gameWinner = session.getWinner();
                    Map<String, Integer> wins = session.getRoundWins();
                    
                    broadcastGameUpdate(gameId, GameUpdate.newBuilder()
                            .setType("GAME_OVER")
                            .setMessage(gameWinner + " wins the game with " + GameSession.getWinsToWinGame() + " round victories! Final: " + wins)
                            .setWinner(gameWinner)
                            .setRoundNumber(session.getCurrentRound())
                            .build());
                    
                    session.endGame();
                    
                    // Record game result to database
                    recordGameResult(gameId, session);
                } else {
                    // Start next round
                    session.startNewRound();
                }
            }

            // Close all streams for this game
            closeGameStreams(gameId);
            
            // Remove game session after a delay
            Thread.sleep(5000);
            gameManager.removeGameSession(gameId);

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println("Game loop interrupted: " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Determines the round winner (player with longest valid word).
     * Returns null if there's a tie (multiple players with same longest length)
     * or if no valid words are submitted.
     */
    private String determineRoundWinner(GameSession session) {
        Map<String, List<String>> submissions = session.getRoundSubmissions();
        
        if (submissions == null || submissions.isEmpty()) {
            return null; // No submissions = no winner
        }
        
        String winner = null;
        int longestLength = 0;
        int playersWithLongestLength = 0;

        // First pass: find the longest word length
        for (String player : submissions.keySet()) {
            List<String> words = submissions.get(player);
            for (String word : words) {
                if (word.length() > longestLength) {
                    longestLength = word.length();
                }
            }
        }

        // If no valid words submitted (longestLength is still 0)
        if (longestLength == 0) {
            return null;
        }

        // Second pass: count how many players have words of the longest length
        for (String player : submissions.keySet()) {
            List<String> words = submissions.get(player);
            for (String word : words) {
                if (word.length() == longestLength) {
                    playersWithLongestLength++;
                    winner = player; // Keep track of the player
                    break; // Only count once per player
                }
            }
        }

        // If multiple players have the same longest length, it's a tie - no winner
        if (playersWithLongestLength > 1) {
            return null;
        }

        // Return the single winner, or null if no one submitted valid words
        return winner;
    }

    /**
     * Broadcasts a game update to all players in a game.
     */
    private void broadcastGameUpdate(String gameId, GameUpdate update) {
        List<StreamObserver<GameUpdate>> streams = gameStreams.getOrDefault(gameId, new ArrayList<>());
        List<StreamObserver<GameUpdate>> deadStreams = new ArrayList<>();

        for (StreamObserver<GameUpdate> stream : streams) {
            try {
                stream.onNext(update);
            } catch (Exception e) {
                System.err.println("Error sending update to client: " + e.getMessage());
                deadStreams.add(stream);
            }
        }

        // Remove dead streams
        streams.removeAll(deadStreams);
    }

    /**
     * Closes all streams for a game.
     */
    private void closeGameStreams(String gameId) {
        List<StreamObserver<GameUpdate>> streams = gameStreams.get(gameId);
        if (streams != null) {
            for (StreamObserver<GameUpdate> stream : streams) {
                try {
                    stream.onCompleted();
                } catch (Exception e) {
                    System.err.println("Error closing stream: " + e.getMessage());
                }
            }
            gameStreams.remove(gameId);
        }
    }

    /**
     * Records the game result to the database.
     * Updates the winner's win count.
     */
    private void recordGameResult(String gameId, GameSession session) {
        try {
            String winner = session.getWinner();
            if (winner != null && !winner.isEmpty()) {
                // Increment the winner's win count in the database
                boolean winsUpdated = userDAO.incrementWins(winner);
                
                // Record the full game result
                int totalRounds = session.getCurrentRound();
                boolean resultRecorded = gameResultDAO.recordGameResult(winner, totalRounds);
                
                System.out.println("Game " + gameId + " finished. Winner: " + winner + " after " + totalRounds + " rounds");
                
                if (winsUpdated) {
                    System.out.println("Successfully recorded win for: " + winner);
                } else {
                    System.err.println("Failed to record win for: " + winner);
                }
                
                if (resultRecorded) {
                    System.out.println("Successfully recorded game result for: " + winner);
                } else {
                    System.err.println("Failed to record game result for: " + winner);
                }
            }
        } catch (Exception e) {
            System.err.println("Error recording game result: " + e.getMessage());
        }
    }
}
