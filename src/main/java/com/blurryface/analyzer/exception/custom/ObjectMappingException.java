package com.blurryface.analyzer.exception.custom;

public class ObjectMappingException extends CustomException {
    public ObjectMappingException() {
        super("Unable to process request! Some error occurred while mapping the objects!");
    }
}
