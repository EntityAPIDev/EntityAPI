package io.snw.entityapi.utils;

public class IDGenerator {

    private static long nextId = Long.MIN_VALUE;

    public static long getNextId() {
        return ++nextId;
    }
}