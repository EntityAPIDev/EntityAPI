package org.entityapi.nbt;

import org.entityapi.nbt.exception.NBTReadException;
import org.entityapi.nbt.exception.NBTWriteException;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class NBTTagFloat extends NBTNumber<Float> {

    private float data;

    public NBTTagFloat() {}

    public NBTTagFloat(float f) {
        this.data = f;
    }

    @Override
    public byte getTypeId() {
        return 5;
    }

    @Override
    public Float getValue() {
        return this.data;
    }

    @Override
    public String toString() {
        return "" + this.data + "f";
    }

    @Override
    public boolean equals(Object object) {
        if (super.equals(object)) {
            NBTTagFloat nbttagfloat = (NBTTagFloat) object;

            return this.data == nbttagfloat.data;
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return super.hashCode() ^ Float.floatToIntBits(this.data);
    }

    @Override
    void write(DataOutput dataoutput) {
        try {
            dataoutput.writeFloat(this.data);
        } catch (IOException e) {
            throw new NBTWriteException(e);
        }
    }

    @Override
    void load(DataInput datainput, int depth) {
        try {
            this.data = datainput.readFloat();
        } catch (IOException e) {
            throw new NBTReadException(e);
        }
    }

    @Override
    public NBTBase clone() {
        return new NBTTagFloat(this.data);
    }
}
