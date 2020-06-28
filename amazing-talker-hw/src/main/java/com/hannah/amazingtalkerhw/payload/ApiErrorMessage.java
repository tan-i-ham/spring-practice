package com.hannah.amazingtalkerhw.payload;

public class ApiErrorMessage {
    private String error;

    public ApiErrorMessage(String error) {
        this.error = error;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
