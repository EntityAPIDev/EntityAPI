package org.entityapi.reflection.refs;

import net.minecraft.server.v1_7_R1.NBTBase;
import org.entityapi.reflection.ClassTemplate;
import org.entityapi.reflection.MethodAccessor;
import org.entityapi.reflection.NMSClassTemplate;

public class NBTBaseRef {

    public static final ClassTemplate<NBTBase> TEMPLATE = NMSClassTemplate.create("NBTBase");

    public static final MethodAccessor<NBTBase> CREATE_TAG_BY_ID = TEMPLATE.getMethod("createTag", byte.class);
}
