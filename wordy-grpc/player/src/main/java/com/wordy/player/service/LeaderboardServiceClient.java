package com.wordy.player.service;

import com.wordy.player.model.LeaderboardData;
import com.wordy.player.model.PlayerStats;
import com.wordy.player.model.WordRecord;
import com.wordy.proto.Empty;
import com.wordy.proto.LeaderboardServiceGrpc;
import com.wordy.proto.LeaderboardResponse;
import io.grpc.Channel;

import java.util.ArrayList;
import java.util.List;

/**
 * Leaderboard Service Client
 * Responsible Team Member: JACKSON MARIANO
 * Wrapper client for gRPC LeaderboardService.
 * Handles fetching leaderboard data (top players and longest words).
 */
public class LeaderboardServiceClient {
    private LeaderboardServiceGrpc.LeaderboardServiceBlockingStub leaderboardStub;

    public LeaderboardServiceClient() {
        Channel channel = GrpcConnectionManager.getInstance().getChannel();
        this.leaderboardStub = LeaderboardServiceGrpc.newBlockingStub(channel);
    }

    /**
     * Fetches leaderboard data from the server.
     *
     * @return LeaderboardData containing top players and longest words
     */
    public LeaderboardData getLeaderboard() {
        try {
            Empty request = Empty.newBuilder().build();
            LeaderboardResponse response = leaderboardStub.getLeaderboard(request);

            // Convert proto PlayerStats to model PlayerStats
            List<PlayerStats> topPlayers = new ArrayList<>();
            for (com.wordy.proto.PlayerStats protoStats : response.getTopPlayersList()) {
                topPlayers.add(new PlayerStats(protoStats.getUsername(), protoStats.getWins()));
            }

            // Convert proto WordRecord to model WordRecord
            List<WordRecord> longestWords = new ArrayList<>();
            for (com.wordy.proto.WordRecord protoRecord : response.getLongestWordsList()) {
                longestWords.add(new WordRecord(
                        protoRecord.getUsername(),
                        protoRecord.getWord(),
                        protoRecord.getLength()
                ));
            }

            LeaderboardData leaderboardData = new LeaderboardData(topPlayers, longestWords);
            System.out.println("Leaderboard fetched successfully");
            return leaderboardData;

        } catch (Exception e) {
            System.err.println("Error fetching leaderboard: " + e.getMessage());
            return new LeaderboardData(new ArrayList<>(), new ArrayList<>());
        }
    }
}
