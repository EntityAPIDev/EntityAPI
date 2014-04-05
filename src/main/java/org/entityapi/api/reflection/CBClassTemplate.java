package org.entityapi.api.reflection;

import org.entityapi.api.plugin.EntityAPI;

public class CBClassTemplate extends ClassTemplate<Object> {

    public CBClassTemplate() {
        setCBClass(getClass().getSimpleName());
    }

    public CBClassTemplate(String className) {
        setCBClass(className);
    }

    protected void setCBClass(String name) {
        Class clazz = EntityAPI.getCore().getAPIServer().getCBClass(name);
        if (clazz == null) {
            EntityAPI.LOGGER_REFLECTION.warning("Failed to find a matching class with name: " + name);
        }
        setClass(clazz);
    }

    public static CBClassTemplate create(String className) {
        return new CBClassTemplate(className);
    }
}
