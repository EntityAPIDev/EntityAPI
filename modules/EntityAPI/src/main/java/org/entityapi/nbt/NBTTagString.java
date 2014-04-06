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

public class NBTTagString extends NBTReturnable<String> {

    private String data;

    public NBTTagString() {
        this(" ");
    }

    public NBTTagString(String name) {
        this.data = name;
        if (name == null)
            throw new IllegalArgumentException("Empty string not allowed!");
    }

    @Override
    public byte getTypeId() {
        return 8;
    }

    @Override
    public String getValue() {
        return this.data;
    }

    @Override
    public String toString() {
        return "\"" + this.data + "\"";
    }

    @Override
    void write(DataOutput dataoutput) {
        try {
            dataoutput.writeUTF(this.data);
        } catch (IOException e) {
            throw new NBTWriteException(e);
        }
    }

    @Override
    void load(DataInput datainput, int depth) {
        try {
            this.data = datainput.readUTF();
        } catch (IOException e) {
            throw new NBTReadException(e);
        }
    }

    @Override
    public boolean equals(Object object) {
        if (!super.equals(object)) {
            return false;
        } else {
            NBTTagString nbttagstring = (NBTTagString) object;

            return this.data == null && nbttagstring.data == null || this.data != null && this.data.equals(nbttagstring.data);
        }
    }

    @Override
    public NBTBase clone() {
        return new NBTTagString(this.data);
    }
}
