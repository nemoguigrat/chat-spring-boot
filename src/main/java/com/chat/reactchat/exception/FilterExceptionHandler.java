//package com.chat.reactchat.exception;
//
//import com.chat.reactchat.exception.dto.ApiErrorDto;
//import com.chat.reactchat.service.util.JsonConverterUtils;
//import com.fasterxml.jackson.core.JsonProcessingException;
//import io.jsonwebtoken.ExpiredJwtException;
//import io.jsonwebtoken.MalformedJwtException;
//import io.jsonwebtoken.UnsupportedJwtException;
//import io.jsonwebtoken.security.SignatureException;
//import lombok.AllArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.MediaType;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//import org.springframework.web.bind.annotation.ResponseBody;
//import org.springframework.web.bind.annotation.RestControllerAdvice;
//import org.springframework.web.context.request.WebRequest;
//
//@RestControllerAdvice
//@Slf4j
//@AllArgsConstructor
//public class FilterExceptionHandler {
//    private final JsonConverterUtils converterUtils;
//
//    @ExceptionHandler(UsernameNotFoundException.class)
//    public @ResponseBody ResponseEntity<String> handleUserNotFound(RuntimeException ex, WebRequest request)
//            throws JsonProcessingException {
//        log.error(ex.getMessage());
//        ApiErrorDto error = new ApiErrorDto(HttpStatus.NOT_FOUND, ex, request.getDescription(false));
//        return ResponseEntity.status(HttpStatus.NOT_FOUND)
//                .contentType(MediaType.APPLICATION_JSON).body(converterUtils.convert(error));
//    }
//
//    @ExceptionHandler({MalformedJwtException.class, UnsupportedJwtException.class, ExpiredJwtException.class,
//            SignatureException.class})
//    public @ResponseBody ResponseEntity<String> handleInvalidJwt(RuntimeException ex, WebRequest request)
//            throws JsonProcessingException {
//        log.error(ex.getMessage());
//        ApiErrorDto error = new ApiErrorDto(HttpStatus.UNAUTHORIZED, ex, request.getDescription(false));
//        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
//                .contentType(MediaType.APPLICATION_JSON).body(converterUtils.convert(error));
//    }
//}
