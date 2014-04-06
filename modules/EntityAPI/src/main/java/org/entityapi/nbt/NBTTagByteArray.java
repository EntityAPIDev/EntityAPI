/*
 * This file is part of EntityAPI.
 *
 * EntityAPI is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * EntityAPI is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with EntityAPI.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.entityapi.nbt;

import org.entityapi.exceptions.NBTReadException;
import org.entityapi.exceptions.NBTWriteException;

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
