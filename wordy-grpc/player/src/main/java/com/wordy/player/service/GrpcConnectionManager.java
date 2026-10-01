package com.wordy.player.service;

import io.grpc.Channel;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import com.wordy.common.config.ClientConfiguration;
import java.util.concurrent.TimeUnit;

/**
 * gRPC Connection Manager (Player Module Infrastructure)
 * Responsible Team Members: NICOLE DEOCALES, JACKSON MARIANO
 * Singleton class to manage gRPC channel connection to the Wordy server.
 * Provides a single connection instance used by all service clients.
 * Server host and port are configured via ClientConfiguration.
 */
public class GrpcConnectionManager {
    private static GrpcConnectionManager instance;
    private ManagedChannel channel;
    private ClientConfiguration config;
    private String serverHost;
    private int serverPort;

    private GrpcConnectionManager() {
        // Load configuration
        config = new ClientConfiguration();
        this.serverHost = config.getServerHost();
        this.serverPort = config.getServerPort();
    }

    /**
     * Gets the singleton instance of the connection manager.
     */
    public static synchronized GrpcConnectionManager getInstance() {
        if (instance == null) {
            instance = new GrpcConnectionManager();
        }
        return instance;
    }

    /**
     * Gets or creates the gRPC channel connection.
     */
    public synchronized Channel getChannel() {
        if (channel == null || channel.isShutdown()) {
            channel = ManagedChannelBuilder
                    .forAddress(serverHost, serverPort)
                    .usePlaintext()  // No TLS encryption for local network
                    .build();
            System.out.println("gRPC Channel connected to " + serverHost + ":" + serverPort);
        }
        return channel;
    }

    /**
     * Changes the server address and reconnects.
     */
    public synchronized void setServerAddress(String host, int port) {
        this.serverHost = host;
        this.serverPort = port;
        config.setServerAddress(host, port);
        
        // Reconnect with new address
        if (channel != null && !channel.isShutdown()) {
            try {
                channel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
            } catch (InterruptedException e) {
                channel.shutdownNow();
            }
        }
        channel = null;
        System.out.println("Server address updated to: " + host + ":" + port);
        getChannel(); // Reconnect
    }

    /**
     * Checks if the channel is alive and connected.
     */
    public boolean isConnected() {
        return channel != null && !channel.isShutdown() && !channel.isTerminated();
    }

    /**
     * Gracefully shuts down the channel connection.
     */
    public synchronized void shutdown() {
        if (channel != null && !channel.isShutdown()) {
            try {
                channel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
                System.out.println("gRPC Channel shutdown completed");
            } catch (InterruptedException e) {
                System.err.println("Error during channel shutdown: " + e.getMessage());
                channel.shutdownNow();
            }
            channel = null;
        }
    }

    /**
     * Gets the server host address.
     */
    public String getServerHost() {
        return serverHost;
    }

    /**
     * Gets the server port.
     */
    public int getServerPort() {
        return serverPort;
    }
}
