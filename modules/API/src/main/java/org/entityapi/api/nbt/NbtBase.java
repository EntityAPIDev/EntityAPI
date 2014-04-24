package org.entityapi.api.nbt;

public interface NbtBase<TType> {

    public abstract String getName();

    public abstract void setName();

    public abstract TType getValue();

    public abstract void setValue(TType value);
}
