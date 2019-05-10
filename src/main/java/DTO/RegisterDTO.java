package DTO;

import model.User;

public class RegisterDTO {
    private String JWTToken;
    private String userId;

    public RegisterDTO(String token, String userId) {
        this.JWTToken = token;
        this.userId = userId;
    }

    public RegisterDTO() {
    }

    public String getJWTToken() {
        return JWTToken;
    }

    public void setJWTToken(String JWTToken) {
        this.JWTToken = JWTToken;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
