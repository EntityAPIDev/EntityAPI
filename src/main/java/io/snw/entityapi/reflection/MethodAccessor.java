package io.snw.entityapi.reflection;

public interface MethodAccessor<T> {

    T invoke(Object instance, Object... args);

}
