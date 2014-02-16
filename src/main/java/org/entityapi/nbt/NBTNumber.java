package org.entityapi.nbt;

public abstract class NBTNumber<T extends Number> extends NBTBase {

    protected NBTNumber() {
    }

    public abstract T getValue();

}
