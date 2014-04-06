package org.entityapi.api.reflection.refs;

import org.entityapi.api.reflection.ClassTemplate;
import org.entityapi.api.reflection.MethodAccessor;
import org.entityapi.api.reflection.NMSClassTemplate;

public class NBTBaseRef {

    public static final ClassTemplate TEMPLATE = NMSClassTemplate.create("NBTBase");

    public static final MethodAccessor CREATE_TAG_BY_ID = TEMPLATE.getMethod("createTag", byte.class);
}
