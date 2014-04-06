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

import org.entityapi.exceptions.NBTException;
import org.entityapi.exceptions.NBTReadException;
import org.entityapi.exceptions.NBTWriteException;

import java.io.*;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public class NBTStreamTools {

    /**
     * Reads an NBTTagCompound out of a given InputStream
     *
     * @param inputStream
     * @return
     */
    public static NBTTagCompound read(InputStream inputStream) {
        try {
            DataInputStream datainputstream = new DataInputStream(new BufferedInputStream(new GZIPInputStream(inputStream)));

            NBTTagCompound nbttagcompound;

            try {
                nbttagcompound = read((DataInput) datainputstream);
            } finally {
                datainputstream.close();
            }

            return nbttagcompound;
        } catch (Exception e) {
            throw new NBTReadException(e);
        }
    }

    public static NBTTagCompound read(DataInput datainput) {
        try {
            NBTBase nbtbase = read(datainput, 0);

            if (nbtbase instanceof NBTTagCompound) {
                return (NBTTagCompound) nbtbase;
            } else {
                throw new IOException("Root tag must be a named compound tag");
            }
        } catch (Exception e) {
            throw new NBTReadException(e);
        }
    }

    private static NBTBase read(DataInput datainput, int i) {
        try {
            byte id = datainput.readByte();

            if (id == 0) {
                return new NBTTagEnd();
            } else {
                datainput.readUTF();
                NBTBase nbtbase = NBTBase.createTagById(id);

                nbtbase.load(datainput, i);
                return nbtbase;
            }
        } catch (Exception e) {
            throw new NBTReadException(e);
        }
    }

    public static void write(NBTTagCompound nbttagcompound, OutputStream outputstream) {
        try {
            DataOutputStream dataoutputstream = new DataOutputStream(new GZIPOutputStream(outputstream));

            try {
                write(nbttagcompound, (DataOutput) dataoutputstream);
            } finally {
                dataoutputstream.close();
            }
        } catch (Exception e) {
            throw new NBTWriteException(e);
        }
    }

    public static void write(NBTTagCompound nbttagcompound, DataOutput dataoutput) {
        write((NBTBase) nbttagcompound, dataoutput);
    }

    private static void write(NBTBase nbtbase, DataOutput dataoutput) {
        try {
            dataoutput.writeByte(nbtbase.getTypeId());
            if (nbtbase.getTypeId() != 0) {
                dataoutput.writeUTF("");
                nbtbase.write(dataoutput);
            }
        } catch (Exception e) {
            throw new NBTWriteException(e);
        }
    }

    public static byte[] toByteArray(NBTTagCompound nbttagcompound) {
        try {
            ByteArrayOutputStream bytearrayoutputstream = new ByteArrayOutputStream();
            DataOutputStream dataoutputstream = new DataOutputStream(new GZIPOutputStream(bytearrayoutputstream));

            try {
                write(nbttagcompound, (DataOutput) dataoutputstream);
            } finally {
                dataoutputstream.close();
            }

            return bytearrayoutputstream.toByteArray();
        } catch (Exception e) {
            throw new NBTException(e);
        }
    }

    public static NBTTagCompound fromByteArray(byte[] abyte) {
        try {
            DataInputStream datainputstream = new DataInputStream(new BufferedInputStream(new GZIPInputStream(new ByteArrayInputStream(abyte))));

            NBTTagCompound nbttagcompound;

            try {
                nbttagcompound = read((DataInput) datainputstream);
            } finally {
                datainputstream.close();
            }

            return nbttagcompound;
        } catch (Exception e) {
            throw new NBTException(e);
        }
    }
}
