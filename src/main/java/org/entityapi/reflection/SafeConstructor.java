package org.entityapi.reflection;

import org.entityapi.EntityAPICore;

import java.lang.reflect.Constructor;

public class SafeConstructor<T> {

    private Constructor<T> constructor;
    private Class[] params;

    public SafeConstructor(Constructor constructor) {
        setConstructor(constructor);
    }

    public SafeConstructor(Class<?> coreClass, Class<?>... params) {
        try {
            Constructor constructor = coreClass.getConstructor(params);
            setConstructor(constructor);
        } catch (NoSuchMethodException e) {
            EntityAPICore.LOGGER_REFLECTION.warning("No such constructor!");
        }
    }

    protected void setConstructor(Constructor constructor) {
        if (constructor == null) {
            throw new UnsupportedOperationException("Cannot create a new constructor!");
        }
        this.constructor = constructor;
        this.params = constructor.getParameterTypes();
    }

    public Constructor getConstructor() {
        return this.constructor;
    }

    public T newInstance(Object... params) {
        try {
            return (T) this.getConstructor().newInstance(params);
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }
}
