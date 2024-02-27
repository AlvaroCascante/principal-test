package com.example.principaltest.exceptions;

import com.example.principaltest.models.ApiResponse;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class RecordExceptionAdvice {

    @ResponseBody
    @ExceptionHandler(value = {RecordNotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiResponse recordNotFoundExceptionHandler(RecordNotFoundException e) {
        return ApiResponse.builder()
                .timestamp(LocalDateTime.now())
                .message(HttpStatus.NOT_FOUND.getReasonPhrase())
                .errors(List.of(e.getMessage()))
                .build();
    }

    @ResponseBody
    @ExceptionHandler(value = {RecordConflictException.class})
    @ResponseStatus(HttpStatus.CONFLICT)
    public ApiResponse recordConflictExceptionHandler(RecordConflictException e) {
        return ApiResponse.builder()
                .timestamp(LocalDateTime.now())
                .message(HttpStatus.CONFLICT.getReasonPhrase())
                .errors(List.of(e.getMessage()))
                .build();
    }
    @ResponseBody
    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiResponse methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException e) {
        return ApiResponse.builder()
                .timestamp(LocalDateTime.now())
                .message(HttpStatus.BAD_REQUEST.getReasonPhrase())
                .errors(e.getBindingResult().getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.toList()))
                .build();
    }
}
