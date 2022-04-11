package com.chat.reactchat.exception.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.http.HttpStatus;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
public class ApiErrorDto implements Serializable {
    private HttpStatus status;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private LocalDateTime timestamp;
    private String message;
    private String exceptionClass;
    private String path;

    private ApiErrorDto() {
        timestamp = LocalDateTime.now();
    }

    public ApiErrorDto(HttpStatus status) {
        this();
        this.status = status;
    }

    public ApiErrorDto(HttpStatus status, Throwable ex, String path) {
        this();
        this.status = status;
        this.message = ex.getMessage();
        this.exceptionClass = ex.getClass().getName();
        this.path = path;
    }

    public ApiErrorDto(HttpStatus status, String message, Throwable ex, String path) {
        this();
        this.status = status;
        this.message = message;
        this.exceptionClass = ex.getClass().getName();
        this.path = path;
    }
}
