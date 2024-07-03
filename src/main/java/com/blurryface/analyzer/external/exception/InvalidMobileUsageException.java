package com.blurryface.analyzer.external.exception;

import com.blurryface.analyzer.exception.custom.CustomException;
import org.springframework.http.HttpStatus;

public class InvalidMobileUsageException extends CustomException {

    public InvalidMobileUsageException() {
        super("The mobile is associated to another account! Please use a different one.", HttpStatus.FORBIDDEN);
    }
}
