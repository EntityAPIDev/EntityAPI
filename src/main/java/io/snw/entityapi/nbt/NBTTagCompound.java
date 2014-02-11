package io.snw.entityapi.nbt;

import io.snw.entityapi.nbt.exception.NBTException;

import java.io.DataInput;
import java.io.DataOutput;
import java.util.*;

public class NBTTagCompound extends NBTBase {

    public Map<String, NBTBase> VALUES = new HashMap<>();

    public NBTTagCompound() {}

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
        String string = "{";

        String values;

        for (Iterator iterator = this.VALUES.keySet().iterator(); iterator.hasNext(); string = string + values + ':' + this.VALUES.get(values) + ',') {
            values = (String) iterator.next();
        }

        return string + "}";
    }

    public boolean isEmpty() {
        return this.VALUES.isEmpty();
    }
}
