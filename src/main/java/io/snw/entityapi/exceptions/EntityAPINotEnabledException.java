package io.snw.entityapi.exceptions;

public class EntityAPINotEnabledException extends RuntimeException {

    public EntityAPINotEnabledException() {
        super("EntityAPI needs to be enabled to perform this action!");
    }
}
