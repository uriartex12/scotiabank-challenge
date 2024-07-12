package com.api.Scotiabank.exceptionHandler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import reactor.core.publisher.Mono;

@RestControllerAdvice
@Slf4j
public class ApiControllerHandler {

    @ExceptionHandler(value = ErrorCodedException.class)
    public Mono<ResponseEntity<ModelError>> handleErrorCodedException(ErrorCodedException exception) {
        log.error("ErrorCodedException : ", exception.getMessage());
        return Mono.just(
                ResponseEntity.status(exception.getStatus())
                        .body(
                                new ModelError(
                                        exception.getStatus(), exception.getCode(), exception.getMessage())));
    }
}
