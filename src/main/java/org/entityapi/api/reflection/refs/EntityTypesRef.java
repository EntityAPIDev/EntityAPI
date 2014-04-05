package org.entityapi.api.reflection.refs;

import org.entityapi.api.reflection.ClassTemplate;
import org.entityapi.api.reflection.FieldAccessor;
import org.entityapi.api.reflection.MethodAccessor;
import org.entityapi.api.reflection.NMSClassTemplate;
import org.entityapi.internal.Constants;

import java.util.Map;

public class EntityTypesRef {

    public static final ClassTemplate TEMPLATE = NMSClassTemplate.create(Constants.EntityTypes.CLASS_NAME);

    public static final FieldAccessor<Map<String, Class<?>>> NAME_TO_CLASS = TEMPLATE.getField(Constants.EntityTypes.MAP_NAME_TO_CLASS);
    public static final FieldAccessor<Map<Class<?>, String>> CLASS_TO_NAME = TEMPLATE.getField(Constants.EntityTypes.MAP_CLASS_TO_NAME);
    public static final FieldAccessor<Map<Integer, Class<?>>> ID_TO_CLASS = TEMPLATE.getField(Constants.EntityTypes.MAP_ID_TO_CLASS);
    public static final FieldAccessor<Map<Class<?>, Integer>> CLASS_TO_ID = TEMPLATE.getField(Constants.EntityTypes.MAP_CLASS_TO_ID);
    public static final FieldAccessor<Map<String, Integer>> NAME_TO_ID = TEMPLATE.getField(Constants.EntityTypes.MAP_NAME_TO_ID);

    public static final MethodAccessor<Void> REGISTER_ENTITY = TEMPLATE.getMethod(Constants.EntityTypes.METHOD_REGISTER_ENTITY, Class.class, String.class, int.class);

    public static void registerEntiy(Class<?> clazz, String name, int id) {
        NAME_TO_CLASS.get(null).put(name, clazz);
        CLASS_TO_NAME.get(null).put(clazz, name);
        ID_TO_CLASS.get(null).put(id, clazz);
        CLASS_TO_ID.get(null).put(clazz, id);
        NAME_TO_ID.get(null).put(name, id);
    }
}
