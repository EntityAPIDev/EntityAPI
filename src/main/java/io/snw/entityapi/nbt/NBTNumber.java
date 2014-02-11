package io.snw.entityapi.nbt;

public abstract class NBTNumber<T> extends NBTBase {

    protected NBTNumber() {}

   public abstract T getValue();

}
