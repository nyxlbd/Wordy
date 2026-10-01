package com.wordy.server.service;

import com.wordy.proto.LeaderboardServiceGrpc;
import com.wordy.proto.Empty;
import com.wordy.proto.LeaderboardResponse;
import com.wordy.proto.PlayerStats;
import com.wordy.proto.WordRecord;
import com.wordy.server.dao.UserDAO;
import com.wordy.server.dao.WordDAO;
import io.grpc.stub.StreamObserver;

import java.util.List;

/**
 * Leaderboard Service Backend Implementation
 * Responsible Team Member: JACKSON MARIANO
 * Fetches top players by wins and longest words submitted, provides ranking data
 * Used for: Top Players UI, Round Result/Game End Backend
 */
public class LeaderboardServiceImpl extends LeaderboardServiceGrpc.LeaderboardServiceImplBase {
    
    private final UserDAO userDAO = new UserDAO();
    private final WordDAO wordDAO = new WordDAO();

    /**
     * Retrieves leaderboard data including top players and longest words.
     * Responsible Team Member: JACKSON MARIANO
     * 
     * @param request Empty request (no parameters needed)
     * @param responseObserver Observer for sending leaderboard response
     */
    @Override
    public void getLeaderboard(Empty request, StreamObserver<LeaderboardResponse> responseObserver) {
        try {
            // Fetch top 5 players by wins
            List<String[]> topPlayers = userDAO.getTopPlayersByWins(5);
            
            // Fetch top 5 longest words
            List<String[]> longestWords = wordDAO.getTopLongestWords(5);

            LeaderboardResponse.Builder builder = LeaderboardResponse.newBuilder();

            // Add top players
            if (topPlayers != null) {
                for (String[] player : topPlayers) {
                    if (player.length >= 2) {
                        builder.addTopPlayers(PlayerStats.newBuilder()
                                .setUsername(player[0])
                                .setWins(Integer.parseInt(player[1]))
                                .build());
                    }
                }
            }

            // Add longest words
            if (longestWords != null) {
                for (String[] word : longestWords) {
                    if (word.length >= 3) {
                        builder.addLongestWords(WordRecord.newBuilder()
                                .setUsername(word[0])
                                .setWord(word[1])
                                .setLength(Integer.parseInt(word[2]))
                                .build());
                    }
                }
            }

            responseObserver.onNext(builder.build());
            responseObserver.onCompleted();

        } catch (Exception e) {
            e.printStackTrace();
            responseObserver.onError(new Exception("Error fetching leaderboard: " + e.getMessage()));
        }
    }
}
