package io.snw.entityapi.hooks;

import com.google.common.collect.Lists;
import io.snw.entityapi.EntityAPI;
import io.snw.entityapi.api.events.CreaturePreSpawnEvent;
import io.snw.entityapi.reflection.FieldAccessor;
import io.snw.entityapi.reflection.SafeField;
import io.snw.entityapi.reflection.refs.ChunkProviderServerRef;
import io.snw.entityapi.reflection.refs.WorldServerRef;
import io.snw.entityapi.utils.WorldUtil;
import net.minecraft.server.v1_7_R1.*;
import org.bukkit.World;
import org.bukkit.entity.EntityType;

import java.util.List;

/**
 * I literally stole this class from BKCommonLib.
 */
public class ChunkProviderServerHook extends ChunkProviderServer {

    protected static World bukkitWorld;

    private final CreaturePreSpawnEvent creaturePreSpawnEvent = new CreaturePreSpawnEvent();
    private final FieldAccessor<Integer> biomeMetaChance = new SafeField<Integer>(WeightedRandomChoice.class, "a");
    private final List<BiomeMeta> creaturePreSpawnMobs = Lists.newArrayList();

    public ChunkProviderServerHook(Object worldserver, Object ichunkloader, Object ichunkprovider) {
        super((WorldServer) worldserver, (IChunkLoader) ichunkloader, (IChunkProvider) ichunkprovider);

        EntityAPI.LOGGER.info("Fixed ChunkProviderServer for world: " + this.bukkitWorld.getName());
    }

    public org.bukkit.World getWorld() {
        return super.world.getWorld();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<BiomeMeta> getMobsFor(EnumCreatureType enumcreaturetype, int x, int y, int z) {
        List<BiomeMeta> mobs = super.getMobsFor(enumcreaturetype, x, y, z);
        if (EntityAPI.hasInstance()) {
            org.bukkit.World world = this.world.getWorld();

            creaturePreSpawnMobs.clear();
            for (BiomeMeta inputMeta : mobs) {
                EntityType oldEntityType = NMSEntityType.getByClass(inputMeta.b).asBukkitEntity();

                /** for(NMSEntityType nmsType : NMSEntityType.values()) {
                 if(nmsType.getMobClass().equals(inputMeta.b)) {
                 oldEntityType = nmsType.asBukkitEntity();
                 break;
                 }
                 } */

                // Set up the event
                creaturePreSpawnEvent.cancelled = false;
                creaturePreSpawnEvent.spawnLocation.setWorld(world);
                creaturePreSpawnEvent.spawnLocation.setX(x);
                creaturePreSpawnEvent.spawnLocation.setY(y);
                creaturePreSpawnEvent.spawnLocation.setZ(z);
                creaturePreSpawnEvent.spawnLocation.setYaw(0.0f);
                creaturePreSpawnEvent.spawnLocation.setPitch(0.0f);
                creaturePreSpawnEvent.entityType = oldEntityType;
                creaturePreSpawnEvent.minSpawnCount = inputMeta.c;
                creaturePreSpawnEvent.maxSpawnCount = inputMeta.d;

                // Raise it and handle spawn cancel
                if (EntityAPI.getInstance().callEvent(creaturePreSpawnEvent).isCancelled() || (creaturePreSpawnEvent.minSpawnCount == 0 && creaturePreSpawnEvent.maxSpawnCount == 0)) {
                    continue;
                }

                final Class<?> entityClass;
                if (creaturePreSpawnEvent.getCustomEntityClass() != null) {
                    entityClass = creaturePreSpawnEvent.getCustomEntityClass();
                } else if (oldEntityType == creaturePreSpawnEvent.entityType) {
                    entityClass = inputMeta.b;
                } else {
                    entityClass = NMSEntityType.fromBukkitType(creaturePreSpawnEvent.entityType).getMobClass();
                    // Unknown or unsupported Entity Type - ignore spawning
                    if (entityClass == null) {
                        continue;
                    }
                }

                // Add element to buffer
                final BiomeMeta outputMeta = new BiomeMeta(null, 0, 0, 0);
                outputMeta.b = entityClass;
                outputMeta.c = creaturePreSpawnEvent.minSpawnCount;
                outputMeta.d = creaturePreSpawnEvent.maxSpawnCount;
                biomeMetaChance.transfer(inputMeta, outputMeta);

                creaturePreSpawnMobs.add(outputMeta);
            }
            return creaturePreSpawnMobs;
        } else {
            return mobs;
        }
    }

    private static <T> T getCPS(org.bukkit.World world, Class<T> type) {
        return (T) WorldServerRef.chunkProviderServer.get(WorldUtil.toWorldServer(world));
    }

    private static IChunkLoader getLoader(Object cps) {
        return (IChunkLoader) ChunkProviderServerRef.CHUNK_LOADER.get(cps);
    }

    public static void hook(org.bukkit.World world) {
        ChunkProviderServer oldCPS = getCPS(world, ChunkProviderServer.class);
        if (oldCPS instanceof ChunkProviderServerHook) {
            return;
        }
        bukkitWorld = world;
        ChunkProviderServerHook newCPS = new ChunkProviderServerHook(oldCPS.world, getLoader(oldCPS), oldCPS.chunkProvider);
        ChunkProviderServerRef.TEMPLATE.transfer(oldCPS, newCPS);
        WorldServerRef.chunkProviderServer.set(newCPS.world, newCPS);
    }

    public static void unhook(org.bukkit.World world) {
        ChunkProviderServerHook oldCPS = getCPS(world, ChunkProviderServerHook.class);
        if (oldCPS == null) {
            return;
        }
        ChunkProviderServer newCPS = new ChunkProviderServer(oldCPS.world, getLoader(oldCPS), oldCPS.chunkProvider);
        ChunkProviderServerRef.TEMPLATE.transfer(oldCPS, newCPS);
        WorldServerRef.chunkProviderServer.set(newCPS.world, newCPS);
    }
}
