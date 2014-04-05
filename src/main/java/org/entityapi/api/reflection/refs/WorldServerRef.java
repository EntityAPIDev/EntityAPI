package org.entityapi.api.reflection.refs;

import org.entityapi.api.reflection.ClassTemplate;
import org.entityapi.api.reflection.FieldAccessor;
import org.entityapi.api.reflection.NMSClassTemplate;

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
}
