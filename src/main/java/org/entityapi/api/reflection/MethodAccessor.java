package org.entityapi.api.reflection;

public interface MethodAccessor<T> {

    T invoke(Object instance, Object... args);

}
