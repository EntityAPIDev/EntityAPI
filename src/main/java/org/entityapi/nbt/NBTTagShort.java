package org.entityapi.nbt;

import org.entityapi.nbt.exception.NBTReadException;
import org.entityapi.nbt.exception.NBTWriteException;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class NBTTagShort extends NBTNumber<Short> {

    private short data;

    public NBTTagShort() {}

    public NBTTagShort(short s) {
        this.data = s;
    }

    @Override
    public byte getTypeId() {
        return 2;
    }

    @Override
    public Short getValue() {
        return this.data;
    }

    @Override
    public String toString() {
        return "" + this.data + "s";
    }

    @Override
    public boolean equals(Object object) {
        if (super.equals(object)) {
            NBTTagShort nbttagshort = (NBTTagShort) object;

            return this.data == nbttagshort.data;
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
            dataoutput.writeShort(this.data);
        } catch (IOException e) {
            throw new NBTWriteException(e);
        }
    }

    @Override
    void load(DataInput datainput, int depth) {
        try {
            this.data = datainput.readShort();
        } catch (IOException e) {
            throw new NBTReadException(e);
        }
    }

    @Override
    public NBTBase clone() {
        return new NBTTagShort(this.data);
    }
}
