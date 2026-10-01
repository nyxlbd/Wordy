package com.wordy.server;

import com.wordy.server.service.AuthServiceImpl;
import com.wordy.server.service.GameServiceImpl;
import com.wordy.server.service.LeaderboardServiceImpl;
import com.wordy.server.service.AdminServiceImpl;
import io.grpc.Server;
import io.grpc.ServerBuilder;

import java.io.IOException;
import java.net.InetAddress;

public class WordyServer {
    private static final int PORT = 50051;
    private Server server;

    public void start() throws IOException {
        server = ServerBuilder.forPort(PORT)
                .addService(new AuthServiceImpl())
                .addService(new GameServiceImpl())
                .addService(new LeaderboardServiceImpl())
                .addService(new AdminServiceImpl())
                .build()
                .start();

        System.out.println("Wordy gRPC Server started, listening on port " + PORT);
        
        // Print network information
        try {
            String hostname = InetAddress.getLocalHost().getHostName();
            String ipAddress = InetAddress.getLocalHost().getHostAddress();
            System.out.println("Server hostname: " + hostname);
            System.out.println("Server IP address: " + ipAddress);
            System.out.println("Clients can connect to: " + ipAddress + ":" + PORT);
        } catch (Exception e) {
            System.out.println("Could not determine server address: " + e.getMessage());
        }

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("Shutting down gRPC server...");
            WordyServer.this.stop();
        }));
    }

    public void stop() {
        if (server != null) {
            server.shutdown();
        }
    }

    public void blockUntilShutdown() throws InterruptedException {
        if (server != null) {
            server.awaitTermination();
        }
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        WordyServer server = new WordyServer();
        server.start();
        server.blockUntilShutdown();
    }
}
