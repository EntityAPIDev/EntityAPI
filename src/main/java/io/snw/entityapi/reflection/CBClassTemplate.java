package io.snw.entityapi.reflection;

import io.snw.entityapi.EntityAPICore;

public class CBClassTemplate extends ClassTemplate<Object> {

    public CBClassTemplate() {
        setCBClass(getClass().getSimpleName());
    }

    public CBClassTemplate(String className) {
        setCBClass(className);
    }

    protected void setCBClass(String name) {
        Class clazz = EntityAPICore.SERVER.getCBClass(name);
        if (clazz == null) {
            EntityAPICore.LOGGER_REFLECTION.warning("Failed to find a matching class with name: " + name);
        }
        setClass(clazz);
    }

    public static CBClassTemplate create(String className) {
        return new CBClassTemplate(className);
    }
}
