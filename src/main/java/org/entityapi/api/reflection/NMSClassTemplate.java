package org.entityapi.api.reflection;

import org.entityapi.api.plugin.EntityAPI;

public class NMSClassTemplate extends ClassTemplate {

    protected NMSClassTemplate() {
        setNMSClass(getClass().getSimpleName());
    }

    public NMSClassTemplate(String className) {
        setNMSClass(className);
    }

    protected void setNMSClass(String name) {
        Class clazz = EntityAPI.getCore().getAPIServer().getNMSClass(name);
        if (clazz == null) {
            EntityAPI.LOGGER_REFLECTION.warning("Failed to find a matching class with name: " + name);
        }
        setClass(clazz);
    }

    public static NMSClassTemplate create(String className) {
        return new NMSClassTemplate(className);
    }
}
