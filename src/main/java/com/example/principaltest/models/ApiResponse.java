package com.example.principaltest.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Setter
@Getter
@Builder
@AllArgsConstructor
public class ApiResponse {

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonView(ApiResponseView.Always.class)
    private LocalDateTime timestamp;

    @JsonView(ApiResponseView.Always.class)
    private String message;

    @JsonView(ApiResponseView.Always.class)
    private List<String> errors;

    @JsonView(ApiResponseView.Always.class)
    private Map<String, Object> data;

    public ApiResponse() {
        this.message = HttpStatus.OK.getReasonPhrase();
        this.timestamp = LocalDateTime.now();
        this.data = new HashMap<>();
    }

    // Constructor for Errors
    public ApiResponse(String message, List<String> errors) {
        this();
        this.message = message;
        this.errors = errors;
    }

    public ApiResponse(String tag, Object value) {
        this();
        this.data.put(tag, value);
    }

    public void setError(String error) {
        this.errors.add(error);
    }

    public void addData(String tag, Object value) {
        this.data.put(tag, value);
    }

    @Override
    public String toString() {
        return "ApiResponse{" +
                "timestamp=" + timestamp +
                ", message='" + message + '\'' +
                ", errors=" + errors +
                ", data=" + data +
                '}';
    }
}
