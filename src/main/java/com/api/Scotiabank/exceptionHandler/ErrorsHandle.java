package com.api.Scotiabank.exceptionHandler;

import lombok.Getter;

@Getter
public enum ErrorsHandle {
    E_ALUMNO_ID_DUPLICATED(
            new ModelError(500, "E_STUDENT_ID_DUPLICATED", "The student ID is duplicated."));

    private final ModelError error;

    ErrorsHandle(ModelError modelError) {
        this.error = modelError;
    }
}
