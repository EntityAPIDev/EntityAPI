package org.entityapi.nbt;

import org.entityapi.exceptions.NBTReadException;
import org.entityapi.exceptions.NBTWriteException;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class NBTTagLong extends NBTNumber<Long> {

    private long data;

    public NBTTagLong() {
    }

    public NBTTagLong(long l) {
        this.data = l;
    }

    @Override
    public byte getTypeId() {
        return 4;
    }

    @Override
    public Long getValue() {
        return this.data;
    }

    @Override
    public String toString() {
        return "" + this.data + "l";
    }

    @Override
    public boolean equals(Object object) {
        if (super.equals(object)) {
            NBTTagLong nbttaglong = (NBTTagLong) object;

            return this.data == nbttaglong.data;
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return super.hashCode() ^ (int) (this.data ^ this.data >>> 32);
    }

    @Override
    void write(DataOutput dataoutput) {
        try {
            dataoutput.writeLong(this.data);
        } catch (IOException e) {
            throw new NBTWriteException(e);
        }
    }

    @Override
    void load(DataInput datainput, int depth) {
        try {
            this.data = datainput.readLong();
        } catch (IOException e) {
            throw new NBTReadException(e);
        }
    }

    @Override
    public NBTBase clone() {
        return new NBTTagLong(this.data);
    }
}
