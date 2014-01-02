package io.snw.entityapi.reflection;

public interface FieldAccessor<T> {

    boolean set(Object instance, T value);

    T get(Object instance);

    T transfer(Object from, Object to);

}
