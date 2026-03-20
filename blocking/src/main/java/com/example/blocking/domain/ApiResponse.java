package com.example.blocking.domain;

import java.util.List;

public class ApiResponse<T> {
    private boolean success;
    private String message;
    private int status;
    private T data;
    private List<String> errorList;

    public ApiResponse() {}

    public ApiResponse(boolean success, String message, int status, T data, List<String> errorList) {
        this.success = success;
        this.message = message;
        this.status = status;
        this.data = data;
        this.errorList = errorList;
    }

    public boolean isSuccess() { return success; }
    public void setSuccess(boolean success) { this.success = success; }
    
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
    
    public int getStatus() { return status; }
    public void setStatus(int status) { this.status = status; }
    
    public T getData() { return data; }
    public void setData(T data) { this.data = data; }
    
    public List<String> getErrorList() { return errorList; }
    public void setErrorList(List<String> errorList) { this.errorList = errorList; }
}
