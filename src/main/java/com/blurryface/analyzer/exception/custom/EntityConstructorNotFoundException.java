package com.blurryface.analyzer.exception.custom;

import org.springframework.http.HttpStatus;

public class EntityConstructorNotFoundException extends CustomException {
    public EntityConstructorNotFoundException() {
        super("Entity Constructor not found!", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
