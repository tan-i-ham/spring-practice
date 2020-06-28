package com.hannah.amazingtalkerhw.payload;

public class ApiResponse {
    private boolean success;
    private String authToken;

    public ApiResponse(boolean success, String authToken) {
        this.success = success;
        this.authToken = authToken;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }
}
