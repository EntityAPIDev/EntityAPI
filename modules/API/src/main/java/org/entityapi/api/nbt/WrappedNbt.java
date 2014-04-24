package org.entityapi.api.nbt;

public class WrappedNbt<TType> implements org.entityapi.api.nbt.INbtWrapper<TType> {

    protected Object handle;

    public WrappedNbt(Object handle) {
         this.handle = handle;
    }

    @Override
    public Object getHandle() {
        return null;
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public void setName() {

    }

    @Override
    public TType getValue() {
        return null;
    }

    @Override
    public void setValue(TType value) {

    }
}
