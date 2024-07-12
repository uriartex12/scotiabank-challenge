package com.api.Scotiabank.exceptionHandler;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class ErrorCodedException extends RuntimeException {
    private static final long serialVersionUID = 1L;
    private Integer status;
    private String message;
    private String code;

    public ErrorCodedException(ErrorsHandle errors) {
        this.status = errors.getError().getStatus();
        this.message = errors.getError().getMessage();
        this.code = errors.getError().getCode();
    }
}
