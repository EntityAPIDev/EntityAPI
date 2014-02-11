package io.snw.entityapi.nbt.exception;

public class NBTReadException extends NBTException {

    public NBTReadException() {}

    public NBTReadException(String message) {
        super(message);
    }

    public NBTReadException(Exception exception) {
        super(exception);
    }
}
