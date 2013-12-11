package io.snw.entityapi.reflection.refs;

import io.snw.entityapi.constants.Constants;
import io.snw.entityapi.reflection.ClassTemplate;
import io.snw.entityapi.reflection.FieldAccessor;
import io.snw.entityapi.reflection.MethodAccessor;
import io.snw.entityapi.reflection.NMSClassTemplate;

import java.util.Map;

public class EntityTypesRef {

    public static final ClassTemplate TEMPLATE = NMSClassTemplate.create(Constants.EntityTypes.CLASS_NAME);

    public static final FieldAccessor<Map<String, Class<?>>> NAME_TO_CLASS = TEMPLATE.getField(Constants.EntityTypes.MAP_NAME_TO_CLASS);
    public static final FieldAccessor<Map<Class<?>, String>> CLASS_TO_NAME = TEMPLATE.getField(Constants.EntityTypes.MAP_CLASS_TO_NAME);
    public static final FieldAccessor<Map<Integer, Class<?>>> ID_TO_CLASS = TEMPLATE.getField(Constants.EntityTypes.MAP_ID_TO_CLASS);
    public static final FieldAccessor<Map<Class<?>, Integer>> CLASS_TO_ID = TEMPLATE.getField(Constants.EntityTypes.MAP_CLASS_TO_ID);
    public static final FieldAccessor<Map<String, Integer>> NAME_TO_ID = TEMPLATE.getField(Constants.EntityTypes.MAP_NAME_TO_ID);

    public static final MethodAccessor<Void> REGISTER_ENTITY = TEMPLATE.getMethod(Constants.EntityTypes.METHOD_REGISTER_ENTITY, Class.class, String.class, int.class);

}
