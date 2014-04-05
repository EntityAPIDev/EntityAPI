package org.entityapi.exceptions;

public class NBTReadException extends NBTException {

    public NBTReadException() {
    }

    public NBTReadException(String message) {
        super(message);
    }

    public NBTReadException(Exception exception) {
        super(exception);
    }
}
