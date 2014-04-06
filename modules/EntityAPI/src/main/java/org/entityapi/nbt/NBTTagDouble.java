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

public class NBTTagDouble extends NBTNumber<Double> {

    private double data;

    public NBTTagDouble() {
    }

    public NBTTagDouble(double d) {
        this.data = d;
    }

    @Override
    public byte getTypeId() {
        return 6;
    }

    @Override
    public Double getValue() {
        return this.data;
    }

    @Override
    public String toString() {
        return "" + this.data + "d";
    }

    @Override
    public boolean equals(Object object) {
        if (super.equals(object)) {
            NBTTagDouble nbttagdouble = (NBTTagDouble) object;

            return this.data == nbttagdouble.data;
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        long i = Double.doubleToLongBits(this.data);

        return super.hashCode() ^ (int) (i ^ i >>> 32);
    }

    @Override
    void write(DataOutput dataoutput) {
        try {
            dataoutput.writeDouble(this.data);
        } catch (IOException e) {
            throw new NBTWriteException(e);
        }
    }

    @Override
    void load(DataInput datainput, int depth) {
        try {
            this.data = datainput.readDouble();
        } catch (IOException e) {
            throw new NBTReadException(e);
        }
    }

    @Override
    public NBTBase clone() {
        return new NBTTagDouble(this.data);
    }
}
