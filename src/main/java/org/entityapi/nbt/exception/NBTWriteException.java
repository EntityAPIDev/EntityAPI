package org.entityapi.nbt.exception;

public class NBTWriteException extends NBTException {

    public NBTWriteException() {}

    public NBTWriteException(String message) {
        super(message);
    }

    public NBTWriteException(Exception exception) {
        super(exception);
    }
}
