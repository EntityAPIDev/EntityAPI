package org.entityapi.exceptions;

public class ControllableEntitySpawnException extends RuntimeException {

    public ControllableEntitySpawnException() {
        super("Failed to spawn ControllableEntity.");
    }

    public ControllableEntitySpawnException(Exception e) {
        super(e);
    }

    public ControllableEntitySpawnException(String message) {
        super(message);
    }
}
