package org.entityapi.nms.v1_7_R1.nbt;

import org.entityapi.api.plugin.EntityAPI;
import org.entityapi.exceptions.NBTReadException;
import org.entityapi.exceptions.NBTWriteException;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class NBTTagList extends NBTBase {

    private List list = new ArrayList();
    private byte type = 0;

    public NBTTagList() {
    }

    @Override
    public byte getTypeId() {
        return 9;
    }

    public void add(NBTBase nbtbase) {
        if (this.type == 0) {
            this.type = nbtbase.getTypeId();
        } else if (this.type != nbtbase.getTypeId()) {
            EntityAPI.LOGGER.warning("Tried adding mismatching data-types to tag-list!");
            return;
        }

        this.list.add(nbtbase);
    }

    public NBTTagCompound get(int i) {
        if (i >= 0 && i < this.list.size()) {
            NBTBase nbtbase = (NBTBase) this.list.get(i);

            return nbtbase.getTypeId() == 10 ? (NBTTagCompound) nbtbase : new NBTTagCompound();
        } else {
            return new NBTTagCompound();
        }
    }

    @Override
    public String toString() {
        String s = "[";
        int i = 0;

        for (Iterator iterator = this.list.iterator(); iterator.hasNext(); ++i) {
            NBTBase nbtbase = (NBTBase) iterator.next();

            s = s + "" + i + ':' + nbtbase + ',';
        }

        return s + "]";
    }

    @Override
    public boolean equals(Object object) {
        if (super.equals(object)) {
            NBTTagList nbttaglist = (NBTTagList) object;

            if (this.type == nbttaglist.type) {
                return this.list.equals(nbttaglist.list);
            }
        }

        return false;
    }

    @Override
    public int hashCode() {
        return super.hashCode() ^ this.list.hashCode();
    }

    @Override
    void write(DataOutput dataoutput) {
        try {
            if (!this.list.isEmpty()) {
                this.type = ((NBTBase) this.list.get(0)).getTypeId();
            } else {
                this.type = 0;
            }

            dataoutput.writeByte(this.type);
            dataoutput.writeInt(this.list.size());

            for (int i = 0; i < this.list.size(); ++i) {
                ((NBTBase) this.list.get(i)).write(dataoutput);
            }
        } catch (IOException e) {
            throw new NBTWriteException(e);
        }
    }

    @Override
    void load(DataInput datainput, int depth) {
        try {
            if (depth > 512) {
                throw new RuntimeException("Tried to read NBT tag with too high complexity, depth > 512");
            } else {
                this.type = datainput.readByte();
                int j = datainput.readInt();

                this.list = new ArrayList();

                for (int k = 0; k < j; ++k) {
                    NBTBase nbtbase = NBTBase.createTagById(this.type);

                    nbtbase.load(datainput, depth + 1);
                    this.list.add(nbtbase);
                }
            }
        } catch (IOException e) {
            throw new NBTReadException(e);
        }
    }

    public int size() {
        return this.list.size();
    }

    @Override
    public NBTBase clone() {
        NBTTagList nbttaglist = new NBTTagList();

        nbttaglist.type = this.type;
        Iterator iterator = this.list.iterator();

        while (iterator.hasNext()) {
            NBTBase nbtbase = (NBTBase) iterator.next();
            NBTBase nbtbase1 = nbtbase.clone();

            nbttaglist.list.add(nbtbase1);
        }

        return nbttaglist;
    }
}
