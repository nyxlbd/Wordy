package com.wordy.player.service;

import com.wordy.proto.AuthServiceGrpc;
import com.wordy.proto.LoginRequest;
import com.wordy.proto.LoginResponse;
import com.wordy.proto.LogoutRequest;
import com.wordy.proto.LogoutResponse;
import io.grpc.Channel;

/**
 * Authentication Service Client
 * Responsible Team Member: NICOLE DEOCALES
 * Wrapper client for gRPC AuthService.
 * Handles login and logout operations with the server.
 */
public class AuthServiceClient {
    private AuthServiceGrpc.AuthServiceBlockingStub authStub;

    public AuthServiceClient() {
        Channel channel = GrpcConnectionManager.getInstance().getChannel();
        this.authStub = AuthServiceGrpc.newBlockingStub(channel);
    }

    /**
     * Attempts to login with the given credentials.
     *
     * @param username the player's username
     * @param password the player's password
     * @param isAdmin whether this is an admin login
     * @return LoginResponse containing success status and user role
     */
    public LoginResponse login(String username, String password, boolean isAdmin) {
        try {
            LoginRequest request = LoginRequest.newBuilder()
                    .setUsername(username)
                    .setPassword(password)
                    .setIsAdmin(isAdmin)
                    .build();

            LoginResponse response = authStub.login(request);
            System.out.println("Login response: " + response.getMessage());
            return response;

        } catch (Exception e) {
            System.err.println("Error during login: " + e.getMessage());
            return LoginResponse.newBuilder()
                    .setSuccess(false)
                    .setMessage("Connection error: " + e.getMessage())
                    .build();
        }
    }

    /**
     * Attempts to logout the given user.
     *
     * @param username the player's username to logout
     * @return LogoutResponse containing success status
     */
    public LogoutResponse logout(String username) {
        try {
            LogoutRequest request = LogoutRequest.newBuilder()
                    .setUsername(username)
                    .build();

            LogoutResponse response = authStub.logout(request);
            System.out.println("Logout response: " + response.getMessage());
            return response;

        } catch (Exception e) {
            System.err.println("Error during logout: " + e.getMessage());
            return LogoutResponse.newBuilder()
                    .setSuccess(false)
                    .setMessage("Connection error: " + e.getMessage())
                    .build();
        }
    }
}
