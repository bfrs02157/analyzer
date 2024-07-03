package com.blurryface.analyzer.exception.custom;

import org.springframework.http.HttpStatus;

public class ResponseConstructorNotFoundException extends CustomException {
    public ResponseConstructorNotFoundException() {
        super("Response Constructor not found!", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
