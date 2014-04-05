package org.entityapi.nbt.vanilla;

import org.entityapi.api.plugin.EntityAPI;
import org.entityapi.nbt.NBTBase;
import org.entityapi.wrapper.AbstractWrapper;

public class AbstractNBTTag<T extends NBTBase> extends AbstractWrapper {

    private Class<T> TAG_TYPE;
    private NBTBase TAG_TYPE_HANDLE;

    public AbstractNBTTag(NBTBase handle) {
        try {
            this.TAG_TYPE = (Class<T>) handle.getClass();
        } catch (ClassCastException e) {
            EntityAPI.LOGGER_REFLECTION.warning("Incompatible TAG_TYPE and TAG_HANDLE!");
        }
        this.TAG_TYPE_HANDLE = handle;
    }

    public NBTBase getTag() {
        return this.TAG_TYPE_HANDLE;
    }
}
