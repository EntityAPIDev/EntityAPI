package org.entityapi.api.nbt;

import com.google.common.primitives.Primitives;
import org.apache.commons.lang.ClassUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public enum NbtTagType {

    TAG_END(0, "TAG_End", void.class),
    TAG_BYTE(1, "TAG_Byte", byte.class),
    TAG_SHORT(2, "TAG_Short", short.class),
    TAG_INT(3, "TAG_Int", int.class),
    TAG_LONG(4, "TAG_Long", long.class),
    TAG_FLOAT(5, "TAG_Float", float.class),
    TAG_DOUBLE(6, "TAG_Double", double.class),
    TAG_BYTE_ARRAY(7, "TAG_Byte_Array", byte[].class),
    TAG_STRING(8, "TAG_String", String.class),
    TAG_LIST(9, "TAG_LIST", List.class),
    TAG_COMPOUND(10, "TAG_Compound", Map.class),
    TAG_INT_ARRAY(11, "TAG_Int_Array", int[].class);

    private static NbtTagType[] LOOKUP;
    private static Map<Class<?>, NbtTagType> CLASS_LOOKUP = new HashMap<>();

    static {

        NbtTagType[] values = NbtTagType.values();
        LOOKUP = new NbtTagType[values.length];

        for(NbtTagType tagType : values) {

            int rawId = tagType.getId();
            Class<?> type = tagType.getType();

            LOOKUP[rawId] = tagType;
            CLASS_LOOKUP.put(type, tagType);

            if(type.isPrimitive())
                CLASS_LOOKUP.put(Primitives.wrap(type), tagType);
        }

        CLASS_LOOKUP.put(org.entityapi.api.nbt.NbtTagCompound.class, TAG_COMPOUND);
        CLASS_LOOKUP.put(org.entityapi.api.nbt.NbtTagList.class, TAG_LIST);
    }

    protected int id;
    protected String name;
    protected Class<?> type;   // The "data" store type. Eg: NBTTagInt has an "int" field which holds the data

    private NbtTagType(int id, String name, Class<?> type) {
        this.id = id;
        this.name = name;
        this.type = type;
    }

    public int getId() {
        return this.id;
    }

    public String getName() {
        return this.name();
    }

    public Class<?> getType() {
        return this.type;
    }

    public static NbtTagType getTypeById(int id) {
        if(id > LOOKUP.length || id < 0)
            throw new IndexOutOfBoundsException("Invalid ID: " + id);

        return LOOKUP[id];
    }

    public static NbtTagType getTypeByClass(Class<?> typeClass) {
        NbtTagType result = CLASS_LOOKUP.get(typeClass);

        if(result != null) {
            return result;
        } else {
            for(Object interfaceClass : ClassUtils.getAllSuperclasses(typeClass)) {
                return getTypeByClass(interfaceClass.getClass());
            }

            throw new IllegalArgumentException("Invalid class type: " + typeClass.getCanonicalName());
        }
    }
}
