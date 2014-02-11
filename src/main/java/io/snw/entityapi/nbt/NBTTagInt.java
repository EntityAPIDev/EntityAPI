package io.snw.entityapi.nbt;

import io.snw.entityapi.nbt.exception.NBTReadException;
import io.snw.entityapi.nbt.exception.NBTWriteException;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class NBTTagInt extends NBTNumber<Integer> {

    private int data;

    public NBTTagInt() {}

    public NBTTagInt(int i) {
        this.data = i;
    }

    @Override
    public byte getTypeId() {
        return 3;
    }

    @Override
    public Integer getValue() {
        return this.data;
    }

    @Override
    public String toString() {
        return "" + this.data + "i";
    }

    @Override
    public boolean equals(Object object) {
        if (super.equals(object)) {
            NBTTagInt nbttagint = (NBTTagInt) object;

            return this.data == nbttagint.data;
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return super.hashCode() ^ this.data;
    }

    @Override
    void write(DataOutput dataoutput) {
        try {
            dataoutput.writeInt(this.data);
        } catch (IOException e) {
            throw new NBTWriteException(e);
        }
    }

    @Override
    void load(DataInput datainput, int depth) {
        try {
            this.data = datainput.readInt();
        } catch (IOException e) {
            throw new NBTReadException(e);
        }
    }

    @Override
    public NBTBase clone() {
        return new NBTTagInt(this.data);
    }
}
