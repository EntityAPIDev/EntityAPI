package io.snw.entityapi.reflection;

import io.snw.entityapi.EntityAPI;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

public class SafeMethod<T> implements MethodAccessor<T> {

    private Method method;
    private Class[] params;
    private boolean isStatic;

    public SafeMethod() {
    }

    public SafeMethod(Method method) {
        setMethod(method);
    }

    public SafeMethod(Class<?> coreClass, String methodname, Class<?>... params) {
        try {
            Method method = coreClass.getDeclaredMethod(methodname, params);
            setMethod(method);
        } catch (NoSuchMethodException e) {
            EntityAPI.LOGGER_REFLECTION.warning("Failed to find a matching method with name: " + methodname);
        }
    }

    protected void setMethod(Method method) {
        if (method == null) {
            EntityAPI.LOGGER_REFLECTION.warning("Cannot create a SafeMethod with a null method!");
        }
        if (!method.isAccessible()) {
            method.setAccessible(true);
        }
        this.method = method;
        this.params = method.getParameterTypes();
        this.isStatic = Modifier.isStatic(method.getModifiers());
    }

    @Override
    public T invoke(Object instance, Object... args) {
        if (this.method != null) {

            //check if the instance is right
            if (instance == null && !isStatic) {
                throw new UnsupportedOperationException("Non-static methods require a valid instance passed in!");
            }

            //check if param lenght is right
            if (args.length != this.params.length) {
                throw new UnsupportedOperationException("Not enough arguments!");
            }

            //if we got trough the above stuff then eventually invoke the method
            try {
                return (T) this.method.invoke(instance, args);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
