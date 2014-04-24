package org.entityapi.api.nbt;

import org.entityapi.api.reflection.MethodAccessor;

public class NbtFactory {

    private static MethodAccessor<Object> CREATE_TAG;
    private static MethodAccessor<Integer> READ_ID;
    private static MethodAccessor<Object> READ_TAG_OF_ITEM;

    private static MethodAccessor<Void> LOAD_NBT;
    private static MethodAccessor<Object> READ_NBT;
}
