package org.entityapi.nms.v1_7_R1.nbt.vanilla;

import org.entityapi.nms.v1_7_R1.nbt.*;
import org.entityapi.nms.v1_7_R1.reflection.NBTBaseRef;
import org.entityapi.api.reflection.ClassTemplate;
import org.entityapi.api.reflection.NMSClassTemplate;

import java.util.List;
import java.util.Map;

public class Converter {

    private enum TagType {
        TAG_BYTE(NMSClassTemplate.create("NBTTagByte"), NBTTagByte.class, byte.class),
        TAG_BYTE_ARRAY(NMSClassTemplate.create("NBTTagByteArray"), NBTTagByteArray.class, byte[].class),
        TAG_COMPOUND(NMSClassTemplate.create("NBTTagCompound"), NBTTagCompound.class, Map.class),
        TAG_DOUBLE(NMSClassTemplate.create("NBTTagDouble"), NBTTagDouble.class, double.class),
        TAG_END(NMSClassTemplate.create("NBTTagEnd"), NBTTagEnd.class, null),
        TAG_FLOAT(NMSClassTemplate.create("NBTTagFloat"), NBTTagFloat.class, float.class),
        TAG_INT(NMSClassTemplate.create("NBTTagInt"), NBTTagInt.class, int.class),
        TAG_INT_ARRAY(NMSClassTemplate.create("NBTTagIntArray"), NBTTagIntArray.class, int[].class),
        TAG_LIST(NMSClassTemplate.create("NBTTagList"), NBTTagList.class, List.class),
        TAG_LONG(NMSClassTemplate.create("NBTTagLong"), NBTTagLong.class, long.class),
        TAG_SHORT(NMSClassTemplate.create("NBTTagShort"), NBTTagShort.class, short.class),
        TAG_STRING(NMSClassTemplate.create("NBTTagString"), NBTTagString.class, String.class);

        private ClassTemplate<net.minecraft.server.v1_7_R1.NBTBase> nmsClass;
        private Class<? extends NBTBase> tagClass;
        private Class<?> storageType;

        private TagType(ClassTemplate<net.minecraft.server.v1_7_R1.NBTBase> nmsClass, Class<? extends NBTBase> tagClass, Class<?> storageType) {
            this.nmsClass = nmsClass;
            this.tagClass = tagClass;
            this.storageType = storageType;
        }

        public ClassTemplate<net.minecraft.server.v1_7_R1.NBTBase> getNMSClass() {
            return this.nmsClass;
        }

        public Class<? extends NBTBase> getTagClass() {
            return this.tagClass;
        }

        public Class<?> getStorageType() {
            return this.storageType;
        }
    }

    public static AbstractNBTTag convert(NBTBase base) {
        Object vannilaTag = NBTBaseRef.CREATE_TAG_BY_ID.invoke(null, base.getTypeId());

        return null;
    }
}
