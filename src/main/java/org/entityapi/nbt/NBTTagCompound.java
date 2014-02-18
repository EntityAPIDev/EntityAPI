package org.entityapi.nbt;

import org.entityapi.nbt.exception.NBTException;

import java.io.DataInput;
import java.io.DataOutput;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class NBTTagCompound extends NBTBase {

    public Map<String, NBTBase> VALUES = new HashMap<>();

    public NBTTagCompound() {
    }

    @Override
    public byte getTypeId() {
        return 10;
    }

    @Override
    public NBTBase clone() {
        return null;
    }

    public void remove(String s) {
        this.VALUES.remove(s);
    }

    public Set<String> getKeys() {
        return this.VALUES.keySet();
    }

    public void set(String key, NBTBase value) {
        this.VALUES.put(key, value);
    }

    public NBTBase get(String key) {
        return this.VALUES.get(key);
    }

    public void setByte(String key, byte b) {
        this.set(key, new NBTTagByte(b));
    }

    public void setByteArray(String key, byte[] b) {
        this.set(key, new NBTTagByteArray(b));
    }

    public void setDouble(String key, double d) {
        this.set(key, new NBTTagDouble(d));
    }

    public void setFloat(String key, float f) {
        this.set(key, new NBTTagFloat(f));
    }

    public void setInt(String key, int i) {
        this.set(key, new NBTTagInt(i));
    }

    public void setIntArray(String key, int[] i) {
        this.set(key, new NBTTagIntArray(i));
    }

    public void setLong(String key, long l) {
        this.set(key, new NBTTagLong(l));
    }

    public void setShort(String key, short s) {
        this.set(key, new NBTTagShort(s));
    }

    public void setString(String key, String s) {
        this.set(key, new NBTTagString(s));
    }

    public void setBoolean(String key, boolean flag) {
        this.setByte(key, (byte) (flag ? 1 : 0));
    }

    public void setNBTTagCompound(String key, NBTTagCompound nbtTagCompound) {
        this.set(key, nbtTagCompound);
    }

    public byte getByte(String key) {
        try {
            if (hasKey(key))
                return ((NBTNumber<Byte>) get(key)).getValue();
            return 0;
        } catch (ClassCastException e) {
            return 0;
        }
    }

    public byte[] getByteArray(String key) {
        try {
            if (hasKey(key))
                return ((NBTReturnable<byte[]>) get(key)).getValue();
            return new byte[]{};
        } catch (ClassCastException e) {
            return new byte[]{};
        }
    }

    public double getDouble(String key) {
        try {
            if (hasKey(key))
                return ((NBTNumber<Double>) get(key)).getValue();
            return 0;
        } catch (ClassCastException e) {
            return 0;
        }
    }

    public float getFloat(String key) {
        try {
            if (hasKey(key))
                return ((NBTNumber<Float>) get(key)).getValue();
            return 0;
        } catch (ClassCastException e) {
            return 0;
        }
    }

    public int getInt(String key) {
        try {
            if (hasKey(key))
                return ((NBTNumber<Integer>) get(key)).getValue();
            return 0;
        } catch (ClassCastException e) {
            return 0;
        }
    }

    public int[] getIntArray(String key) {
        try {
            if (hasKey(key))
                return ((NBTReturnable<int[]>) get(key)).getValue();
            return new int[]{};
        } catch (ClassCastException e) {
            return new int[]{};
        }
    }

    public long getLong(String key) {
        try {
            if (hasKey(key))
                return ((NBTNumber<Long>) get(key)).getValue();
            return 0;
        } catch (ClassCastException e) {
            return 0;
        }
    }

    public short getShort(String key) {
        try {
            if (hasKey(key))
                return ((NBTNumber<Short>) get(key)).getValue();
            return 0;
        } catch (ClassCastException e) {
            return 0;
        }
    }

    public String getString(String key) {
        try {
            if (hasKey(key))
                return ((NBTReturnable<String>) get(key)).getValue();
            return "";
        } catch (ClassCastException e) {
            return "";
        }
    }

    public boolean getBoolean(String key) {
        return this.getByte(key) != 0;
    }

    public NBTTagCompound getNBTTagCompound(String key) {
        try {
            if (hasKey(key))
                return (NBTTagCompound) this.get(key);
            return new NBTTagCompound();
        } catch (ClassCastException e) {
            return new NBTTagCompound();
        }
    }

    public boolean hasKey(String key) {
        return this.VALUES.containsKey(key);
    }

    static NBTBase read(byte id, DataInput input, int depth) {
        NBTBase nbtBase = NBTBase.createTagById(id);

        try {
            nbtBase.load(input, depth);
            return nbtBase;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    static NBTBase readTag(byte b, String s, DataInput datainput, int i) {
        try {
            NBTBase nbtbase = NBTBase.createTagById(b);
            nbtbase.load(datainput, i);
            return nbtbase;
        } catch (Exception e) {
            throw new NBTException("Failed to load NBT!\nTag-name: " + s + "\nTag-type: " + b);
        }
    }

    @Override
    void write(DataOutput dataoutput) {
        try {
            Iterator iterator = this.VALUES.keySet().iterator();

            while (iterator.hasNext()) {
                String s = (String) iterator.next();
                NBTBase nbtbase = (NBTBase) this.VALUES.get(s);

                write(s, nbtbase, dataoutput);
            }

            dataoutput.writeByte(0);
        } catch (Exception e) {
            throw new NBTException(e);
        }
    }

    @Override
    void load(DataInput datainput, int depth) {
        if (depth > 512) {
            throw new RuntimeException("Tried to read NBT tag with too high complexity, depth > 512");
        } else {
            this.VALUES.clear();

            byte b;

            while ((b = asByte(datainput)) != 0) {
                String s = asString(datainput);
                NBTBase nbtbase = readTag(b, s, datainput, depth + 1);

                this.VALUES.put(s, nbtbase);
            }
        }
    }

    private static void write(String s, NBTBase nbtbase, DataOutput dataoutput) {
        try {
            dataoutput.writeByte(nbtbase.getTypeId());
            if (nbtbase.getTypeId() != 0) {
                dataoutput.writeUTF(s);
                nbtbase.write(dataoutput);
            }
        } catch (Exception e) {
            throw new NBTException(e);
        }
    }

    private static byte asByte(DataInput datainput) {
        try {
            return datainput.readByte();
        } catch (Exception e) {
            throw new NBTException(e);
        }
    }

    private static String asString(DataInput datainput) {
        try {
            return datainput.readUTF();
        } catch (Exception e) {
            throw new NBTException(e);
        }
    }

    @Override
    public boolean equals(Object object) {
        if (super.equals(object)) {
            NBTTagCompound nbttagcompound = (NBTTagCompound) object;

            return this.VALUES.entrySet().equals(nbttagcompound.VALUES.entrySet());
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return super.hashCode() ^ this.VALUES.hashCode();
    }

    @Override
    public String toString() {
        String String = "{";

        String values;

        for (Iterator iterator = this.VALUES.keySet().iterator(); iterator.hasNext(); String = String + values + ':' + this.VALUES.get(values) + ',') {
            values = (String) iterator.next();
        }

        return String + "}";
    }

    public boolean isEmpty() {
        return this.VALUES.isEmpty();
    }
}
