package org.entityapi.nbt;

import org.entityapi.nbt.exception.NBTReadException;
import org.entityapi.nbt.exception.NBTWriteException;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Arrays;

public class NBTTagByteArray extends NBTReturnable<byte[]> {

    private byte[] data;

    public NBTTagByteArray() {
    }

    public NBTTagByteArray(byte[] b) {
        this.data = b;
    }

    @Override
    public byte getTypeId() {
        return 7;
    }

    @Override
    public byte[] getValue() {
        return this.data;
    }

    @Override
    public String toString() {
        return "[" + this.data.length + " bytes]";
    }

    @Override
    public boolean equals(Object object) {
        return super.equals(object) ? Arrays.equals(this.data, ((NBTTagByteArray) object).data) : false;
    }

    @Override
    public int hashCode() {
        return super.hashCode() ^ Arrays.hashCode(this.data);
    }

    @Override
    void write(DataOutput dataoutput) {
        try {
            dataoutput.writeInt(this.data.length);
            dataoutput.write(this.data);
        } catch (IOException e) {
            new NBTWriteException(e);
        }
    }

    @Override
    void load(DataInput datainput, int depth) {
        try {
            int lenght = datainput.readInt();
            this.data = new byte[lenght];
            datainput.readFully(this.data);
        } catch (IOException e) {
            throw new NBTReadException(e);
        }
    }

    @Override
    public NBTBase clone() {
        byte[] abyte = new byte[this.data.length];

        System.arraycopy(this.data, 0, abyte, 0, this.data.length);
        return new NBTTagByteArray(abyte);
    }
}
