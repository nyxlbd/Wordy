package com.wordy.common.config;

import java.io.*;
import java.util.Properties;

/**
 * Manages client configuration settings like server host and port.
 * Persists settings to wordy-client.properties file.
 */
public class ClientConfiguration {
    private static final String CONFIG_FILE = "wordy-client.properties";
    private static final String SERVER_HOST_KEY = "server.host";
    private static final String SERVER_PORT_KEY = "server.port";
    private static final String DEFAULT_HOST = "10.135.137.146";
    private static final int DEFAULT_PORT = 50051;
    
    private Properties properties;
    private String serverHost;
    private int serverPort;

    public ClientConfiguration() {
        properties = new Properties();
        loadConfiguration();
    }

    /**
     * Loads configuration from file or creates default if not found.
     */
    private void loadConfiguration() {
        File configFile = new File(CONFIG_FILE);
        
        if (configFile.exists()) {
            try (InputStream input = new FileInputStream(configFile)) {
                properties.load(input);
                serverHost = properties.getProperty(SERVER_HOST_KEY, DEFAULT_HOST);
                serverPort = Integer.parseInt(
                    properties.getProperty(SERVER_PORT_KEY, String.valueOf(DEFAULT_PORT))
                );
                System.out.println("Loaded configuration from: " + CONFIG_FILE);
                System.out.println("Server: " + serverHost + ":" + serverPort);
            } catch (IOException | NumberFormatException e) {
                System.err.println("Error loading config, using defaults: " + e.getMessage());
                serverHost = DEFAULT_HOST;
                serverPort = DEFAULT_PORT;
            }
        } else {
            // Use defaults if file doesn't exist
            serverHost = DEFAULT_HOST;
            serverPort = DEFAULT_PORT;
            System.out.println("Config file not found, using defaults: " + serverHost + ":" + serverPort);
        }
    }

    /**
     * Saves current configuration to file.
     */
    public void saveConfiguration() {
        properties.setProperty(SERVER_HOST_KEY, serverHost);
        properties.setProperty(SERVER_PORT_KEY, String.valueOf(serverPort));
        
        try (OutputStream output = new FileOutputStream(CONFIG_FILE)) {
            properties.store(output, "Wordy Client Configuration");
            System.out.println("Configuration saved to: " + CONFIG_FILE);
        } catch (IOException e) {
            System.err.println("Error saving configuration: " + e.getMessage());
        }
    }

    /**
     * Gets the configured server host.
     */
    public String getServerHost() {
        return serverHost;
    }

    /**
     * Gets the configured server port.
     */
    public int getServerPort() {
        return serverPort;
    }

    /**
     * Sets the server host and saves to file.
     */
    public void setServerHost(String host) {
        this.serverHost = host;
        saveConfiguration();
    }

    /**
     * Sets the server port and saves to file.
     */
    public void setServerPort(int port) {
        this.serverPort = port;
        saveConfiguration();
    }

    /**
     * Sets both host and port and saves to file.
     */
    public void setServerAddress(String host, int port) {
        this.serverHost = host;
        this.serverPort = port;
        saveConfiguration();
    }

    /**
     * Gets the full server address string.
     */
    public String getServerAddress() {
        return serverHost + ":" + serverPort;
    }
}
