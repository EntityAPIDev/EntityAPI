package org.entityapi.exceptions;

public class ControllableEntitySpawnException extends RuntimeException {

    public ControllableEntitySpawnException() {
        super("Failed to spawn ControllableEntity.");
    }
}
