package com.blurryface.analyzer.external.exception;

import com.blurryface.analyzer.exception.custom.CustomException;
import org.springframework.http.HttpStatus;

public class InvalidEmailUsageException extends CustomException {
    public InvalidEmailUsageException() {
        super("The entered email is associated with a non-admin account on Shiprocket! Please log in with an admin account or choose a different email.", HttpStatus.FORBIDDEN);
    }
}
