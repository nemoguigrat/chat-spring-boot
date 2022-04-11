package com.chat.reactchat.exception;

import com.chat.reactchat.exception.dto.ApiErrorDto;
import com.chat.reactchat.exception.room.RoomNotFoundException;
import com.chat.reactchat.exception.room.UnsupportedRoomActionException;
import com.chat.reactchat.exception.room.UserRoomAccessException;
import com.chat.reactchat.exception.user.UserExistException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
@Slf4j
public class ControllerExceptionHandler extends ResponseEntityExceptionHandler {
    //TODO избавиться от копипаста, сделать более внятные сообщения об ошибках
    @ExceptionHandler(UserExistException.class)
    public ResponseEntity<Object> handleUserExist(RuntimeException ex, WebRequest request) {
        ApiErrorDto error = new ApiErrorDto(HttpStatus.CONFLICT, ex, request.getDescription(false));
        return handleExceptionInternal(ex,
                error,
                new HttpHeaders(),
                HttpStatus.CONFLICT,
                request);
    }

    @ExceptionHandler(UnsupportedRoomActionException.class)
    public ResponseEntity<Object> handleUnsupportedRoomAction(RuntimeException ex, WebRequest request) {
        ApiErrorDto error = new ApiErrorDto(HttpStatus.FORBIDDEN, ex, request.getDescription(false));
        return handleExceptionInternal(ex,
                error,
                new HttpHeaders(),
                HttpStatus.FORBIDDEN,
                request);
    }

    @ExceptionHandler(RoomNotFoundException.class)
    public ResponseEntity<Object> handleRoomNotFound(RuntimeException ex, WebRequest request) {
        ApiErrorDto error = new ApiErrorDto(HttpStatus.NOT_FOUND, ex, request.getDescription(false));
        return handleExceptionInternal(ex,
                error,
                new HttpHeaders(),
                HttpStatus.NOT_FOUND,
                request);
    }

    @ExceptionHandler(UserRoomAccessException.class)
    public ResponseEntity<Object> handleUserRoomAccess(RuntimeException ex, WebRequest request) {
        ApiErrorDto error = new ApiErrorDto(HttpStatus.FORBIDDEN, ex, request.getDescription(false));
        return handleExceptionInternal(ex,
                error,
                new HttpHeaders(),
                HttpStatus.FORBIDDEN,
                request);
    }
}
