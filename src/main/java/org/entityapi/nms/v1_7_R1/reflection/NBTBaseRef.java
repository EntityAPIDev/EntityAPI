package org.entityapi.nms.v1_7_R1.reflection;

import net.minecraft.server.v1_7_R1.NBTBase;
import org.entityapi.api.reflection.ClassTemplate;
import org.entityapi.api.reflection.MethodAccessor;
import org.entityapi.api.reflection.NMSClassTemplate;

public class NBTBaseRef {

    public static final ClassTemplate<NBTBase> TEMPLATE = NMSClassTemplate.create("NBTBase");

    public static final MethodAccessor<NBTBase> CREATE_TAG_BY_ID = TEMPLATE.getMethod("createTag", byte.class);
}
