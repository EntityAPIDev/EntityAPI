package io.snw.entityapi.reflection.refs;

import io.snw.entityapi.reflection.ClassTemplate;
import io.snw.entityapi.reflection.FieldAccessor;
import io.snw.entityapi.reflection.NMSClassTemplate;
import io.snw.entityapi.utils.WorldUtil;
import org.bukkit.World;

public class ChunkProviderServerRef {

    public static final ClassTemplate TEMPLATE = NMSClassTemplate.create("ChunkProviderServer");

    public static final FieldAccessor CHUNK_PROVIDER = TEMPLATE.getField("chunkProvider");
    public static final FieldAccessor CHUNK_LOADER = TEMPLATE.getField("f");
    public static final FieldAccessor WORD_SERVER = TEMPLATE.getField("world");

    public static Object getIChunkProvider(World world) {
        return CHUNK_PROVIDER.get(WorldUtil.getChunkProviderServer(world));
    }

    public static Object getChunkLoader(World world) {
        return CHUNK_LOADER.get(WorldUtil.getChunkProviderServer(world));
    }

    public static Object getWorldServer(World world) {
        return WORD_SERVER.get(WorldUtil.getChunkProviderServer(world));
    }
}
