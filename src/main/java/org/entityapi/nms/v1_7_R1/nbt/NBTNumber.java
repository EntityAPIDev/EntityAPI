package org.entityapi.nms.v1_7_R1.nbt;

public abstract class NBTNumber<T extends Number> extends NBTBase {

    protected NBTNumber() {
    }

    public abstract T getValue();

}
