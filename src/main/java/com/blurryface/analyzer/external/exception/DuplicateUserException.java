package com.blurryface.analyzer.external.exception;

import com.blurryface.analyzer.exception.custom.CustomException;

import com.blurryface.analyzer.helpers.enums.CredentialsType;
import com.blurryface.analyzer.helpers.enums.ServiceName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.http.HttpStatus;

@Data
@EqualsAndHashCode(callSuper = true)
public class DuplicateUserException extends CustomException {

    private CredentialsType credentialsType;
    private ServiceName service;

    public DuplicateUserException(CredentialsType credentialsType, ServiceName service) {
        super("A user with this " + credentialsType.name().toLowerCase() + " already exists!", HttpStatus.CONFLICT);
        this.credentialsType = credentialsType;
        this.service = service;
    }
}
