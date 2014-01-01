package io.snw.entityapi.reflection;

import io.snw.entityapi.EntityAPI;

public class NMSClassTemplate extends ClassTemplate {

    protected NMSClassTemplate() {
        setNMSClass(getClass().getSimpleName());
    }

    public NMSClassTemplate(String className) {
        setNMSClass(className);
    }

    protected void setNMSClass(String name) {
        Class clazz = EntityAPI.SERVER.getNMSClass(name);
        if (clazz == null) {
            EntityAPI.LOGGER_REFLECTION.warning("Failed to find a matching class with name: " + name);
        }
        setClass(clazz);
    }

    public static NMSClassTemplate create(String className) {
        return new NMSClassTemplate(className);
    }
}
