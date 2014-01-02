package io.snw.entityapi.reflection.refs;

import io.snw.entityapi.reflection.ClassTemplate;
import io.snw.entityapi.reflection.FieldAccessor;
import io.snw.entityapi.reflection.NMSClassTemplate;
import io.snw.entityapi.reflection.SafeDirectField;

public class WorldServerRef {

    public static final ClassTemplate CLASS_TEMPLATE = NMSClassTemplate.create("WorldServer");
    public static final ClassTemplate WORLD_CLASS_TEMPLATE = NMSClassTemplate.create("World");

    public static final FieldAccessor ENTITY_TRACKER = CLASS_TEMPLATE.getField("tracker");
    public static final FieldAccessor PLAYER_CHUNK_MAP = CLASS_TEMPLATE.getField("manager");
    public static final FieldAccessor CHUNK_PROVIDER_SERVER = CLASS_TEMPLATE.getField("chunkProviderServer");
    public static final FieldAccessor<Boolean> SAVING_SWITCH = CLASS_TEMPLATE.getField("savingDisabled");

    public static Object getEntityTracker(Object worldServer) {
        return ENTITY_TRACKER.get(worldServer);
    }

    public static Object getPlayerChunkMap(Object worldServer) {
        return PLAYER_CHUNK_MAP.get(worldServer);
    }

    public static Object getChunkProviderServer(Object worldServer) {
        return CHUNK_PROVIDER_SERVER.get(worldServer);
    }

    public static boolean isSafeEnabled(Object worldServer) {
        return SAVING_SWITCH.get(worldServer);
    }

    public static final FieldAccessor<Object> chunkProviderServer = new SafeDirectField<Object>() {
        private final FieldAccessor field1 = CLASS_TEMPLATE.getField("chunkProviderServer");
        private final FieldAccessor field2 = WORLD_CLASS_TEMPLATE.getField("chunkProvider");

        @Override
        public Object get(Object instance) {
            return field1.get(instance);
        }

        @Override
        public boolean set(Object instance, Object value) {
            return field1.set(instance, value) && field2.set(instance, value);
        }
    };
}
