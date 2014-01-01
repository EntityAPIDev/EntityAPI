package io.snw.entityapi.utils;

import io.snw.entityapi.reflection.MethodAccessor;
import io.snw.entityapi.reflection.SafeMethod;
import io.snw.entityapi.reflection.refs.EntityTypesRef;
import org.bukkit.entity.Entity;

public class EntityUtil {

    public static final MethodAccessor<?> GET_HANDLE = new SafeMethod(Entity.class, "getHandle");

    public static Object getHandle(Entity entity) {
        return GET_HANDLE.invoke(entity);
    }

    public static void registerEntity(Class<?> clazz, String name, int id) {
        EntityTypesRef.registerEntiy(clazz, name, id);
    }
}
