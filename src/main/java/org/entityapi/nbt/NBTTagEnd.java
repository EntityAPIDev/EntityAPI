package org.entityapi.nbt;

import java.io.DataInput;
import java.io.DataOutput;

public class NBTTagEnd extends NBTBase {
    @Override
    public byte getTypeId() {
        return 0;
    }

    @Override
    public String toString() {
        return "END";
    }

    @Override
    void write(DataOutput dataoutput) {}

    @Override
    void load(DataInput datainput, int depth) {}

    @Override
    public NBTBase clone() {
        return new NBTTagEnd();
    }
}
