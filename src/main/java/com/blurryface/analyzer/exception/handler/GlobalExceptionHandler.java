package com.blurryface.analyzer.exception.handler;

import com.blurryface.analyzer.exception.ExceptionsDetails;
import com.blurryface.analyzer.exception.custom.CustomException;
import com.blurryface.analyzer.exception.custom.EntityConstructorNotFoundException;
import com.blurryface.analyzer.exception.custom.ExternalServiceException;
import com.blurryface.analyzer.exception.custom.ResponseConstructorNotFoundException;
import com.blurryface.analyzer.external.exception.DuplicateUserException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.crypto.NoSuchPaddingException;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

//    private final LoggerUtil loggerUtil = LoggerUtil.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<?> handleCustomException(CustomException exception) {
        return new ResponseEntity<>(
                errorResponseBuilder(ExceptionsDetails.CUSTOM_EXCEPTION.CODE, ExceptionsDetails.CUSTOM_EXCEPTION.TITLE,
                        exception.getMessage(), null, exception.getCode()),
                exception.getCode()
        );
    }

//    @ExceptionHandler(ConstraintViolationException.class)
//    public ResponseEntity<?> handleConstraintViolationException(ConstraintViolationException exception) {
//        List<String> errors = new ArrayList<>();
//        exception.getConstraintViolations().forEach(cv -> errors.add(cv.getMessage()));
//        Map<String, Object> errorsMeta = new HashMap<>();
//        errorsMeta.put("data", errors);
//        return new ResponseEntity<>(
//                errorResponseBuilder(ExceptionsDetails.CONSTRAINTS_VIOLATION.CODE, ExceptionsDetails.CONSTRAINTS_VIOLATION.TITLE,
//                        ExceptionsDetails.CONSTRAINTS_VIOLATION.MESSAGE, errorsMeta, HttpStatus.UNPROCESSABLE_ENTITY),
//                HttpStatus.UNPROCESSABLE_ENTITY
//        );
//    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
        List<String> errors = exception.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.toList());
        Map<String, Object> errorsMeta = new HashMap<>();
        errorsMeta.put("data", errors);
        return new ResponseEntity<>(
                errorResponseBuilder(ExceptionsDetails.INVALID_METHOD_ARGUMENTS.CODE, ExceptionsDetails.INVALID_METHOD_ARGUMENTS.TITLE,
                        ExceptionsDetails.INVALID_METHOD_ARGUMENTS.MESSAGE, errorsMeta, HttpStatus.UNPROCESSABLE_ENTITY),
                HttpStatus.UNPROCESSABLE_ENTITY
        );
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Object> handleMessageNotReadableException(HttpMessageNotReadableException exception) {
        String message = "Invalid Request";
        try {
            if (exception.getCause() instanceof InvalidFormatException instanceOfInvalidFormatException) {
                message = "[INVALID REQUEST] :: The value \'" + instanceOfInvalidFormatException.getValue() + "\' for the field [" +
            instanceOfInvalidFormatException.getPath().get(0).getFieldName() + "] is not valid!";
            }
        } catch (Exception ignored) {

        }
        return new ResponseEntity<>(
                errorResponseBuilder(ExceptionsDetails.INVALID_METHOD_ARGUMENTS.CODE, ExceptionsDetails.INVALID_METHOD_ARGUMENTS.TITLE,
                        message, null, HttpStatus.BAD_REQUEST),
                HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<Object> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException exception) {
        String message = "Invalid Request";
        try {
            message = "[INVALID REQUEST] :: The value \'" + exception.getValue() + "\' for the parameter [" +
                        exception.getName() + "] is not valid!";
        } catch (Exception ignored) {

        }
        return new ResponseEntity<>(
                errorResponseBuilder(ExceptionsDetails.INVALID_METHOD_ARGUMENTS.CODE, ExceptionsDetails.INVALID_METHOD_ARGUMENTS.TITLE,
                        message, null, HttpStatus.BAD_REQUEST),
                HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(DuplicateUserException.class)
    public ResponseEntity<Object> handleDuplicateUserException(DuplicateUserException exception) {
        HashMap<String, Object> errorsMeta = new HashMap<>();
        errorsMeta.put("platform", exception.getService());
        return new ResponseEntity<>(
                errorResponseBuilder(ExceptionsDetails.DUPLICATE_USER.CODE, ExceptionsDetails.DUPLICATE_USER.TITLE,
                        exception.getMessage(), errorsMeta, exception.getCode()),
                HttpStatus.UNPROCESSABLE_ENTITY
        );
    }

//    @ExceptionHandler(InvalidAuthenticationMethodException.class)
//    public ResponseEntity<Object> handleInvalidAuthenticationMethodException(InvalidAuthenticationMethodException exception) {
//        HashMap<String, Object> errorsMeta = new HashMap<>();
//        errorsMeta.put("platform", exception.getAllowedAuthenticationStrategy().toString());
//        errorsMeta.put("creds", exception.getCreds());
//        errorsMeta.put("token", exception.getToken());
//        return new ResponseEntity<>(
//                errorResponseBuilder(ExceptionsDetails.INVALID_AUTHENTICATION_STRATEGY.CODE, ExceptionsDetails.INVALID_AUTHENTICATION_STRATEGY.TITLE,
//                        exception.getMessage(), errorsMeta, exception.getCode()),
//                HttpStatus.UNPROCESSABLE_ENTITY
//        );
//    }

//    @ExceptionHandler({
//            InvalidKeyException.class,
//            IllegalBlockSizeException.class,
//            BadPaddingException.class
//    })
//    public ResponseEntity<?> handlePasswordDecryptionError(Exception e) throws PasswordDecryptionError {
//        //TODO: Log the Error Message
//        throw new PasswordDecryptionError();
//    }

    @ExceptionHandler({
            //CryptoService around JWT
//            JWTCreationException.class,
            //CoreStrategy
//            RequestTransformerNotFoundException.class,
            EntityConstructorNotFoundException.class,
            ResponseConstructorNotFoundException.class,
//            UserProfileFilterNotFoundException.class,
//            WebClientResponseException.class,
            //UtilityService around Keys
            NoSuchAlgorithmException.class,
            InvalidKeySpecException.class,
//            RSAKeyEntityNotFound.class,
            //CryptoService Algo Not found for Password Decryption
            NoSuchPaddingException.class,
            GeneralSecurityException.class,
            IOException.class,
            ExternalServiceException.class,
            //Generic
            Exception.class
    })
    public ResponseEntity<?> handleInternalExceptions(Exception exception) {
//        loggerUtil.error(exception.getMessage());
//        loggerUtil.stackTrace(exception);
        return new ResponseEntity<>(
                errorResponseBuilder(ExceptionsDetails.INTERNAL_ERROR.CODE, ExceptionsDetails.INTERNAL_ERROR.TITLE,
                        ExceptionsDetails.INTERNAL_ERROR.MESSAGE, null, HttpStatus.INTERNAL_SERVER_ERROR),
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }


    private Object errorResponseBuilder(String errorCode, String errorTitle, String message,
                                                    Map<String, Object> meta, HttpStatus status) {

//        ErrorResponse errorResponse = ErrorResponse.ErrorResponseBuilder()
//                .code(errorCode)
//                .title(errorTitle)
//                .message(message)
//                .meta(meta)
//                .build();
//
//        GenericResponse<?> response = new GenericResponse<>();
//        response.setStatus(Constants.StatusEnum.ERROR);
//        response.setStatusCode(status.value());
//        response.setError(errorResponse);

        return new Object();
    }

}
