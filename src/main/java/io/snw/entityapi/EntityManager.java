package io.snw.entityapi;

import com.google.common.collect.Maps;
import io.snw.entityapi.api.ControllableEntity;
import io.snw.entityapi.api.ControllableEntityHandle;
import io.snw.entityapi.api.ControllableEntityType;
import io.snw.entityapi.exceptions.NameRequiredException;
import io.snw.entityapi.reflection.SafeConstructor;
import io.snw.entityapi.utils.WorldUtil;
import net.minecraft.server.v1_7_R1.World;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.plugin.Plugin;

import java.util.*;

public class EntityManager {

    private final Plugin owningPlugin;
    private boolean keepEntitiesInMemory;

    private final Map<Integer, ControllableEntity> ENTITIES = Maps.newConcurrentMap();

    private final ChunkManager CHUNK_MANAGER;
    private final int taskId;

    public EntityManager(Plugin plugin, final boolean keepEntitiesInMemory) {
        this.owningPlugin = plugin;
        this.keepEntitiesInMemory = keepEntitiesInMemory;

        this.CHUNK_MANAGER = new ChunkManager(this);

        Bukkit.getPluginManager().registerEvents(this.CHUNK_MANAGER, EntityAPICore.getCore());

        this.taskId = Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {
            @Override
            public void run() {
                Iterator<Map.Entry<Integer, ControllableEntity>> iterator = ENTITIES.entrySet().iterator();

                while (iterator.hasNext()) {
                    Map.Entry<Integer, ControllableEntity> entry = iterator.next();

                    if (entry.getValue().getHandle() == null) {
                        if (!keepEntitiesInMemory)
                            iterator.remove();
                    } else {
                        entry.getValue().getHandle().C();
                        if(!entry.getValue().getHandle().isAlive()) {
                           //TODO despawn if shitty
                        }
                    }
                }
            }
        }, 1L, 1L);
    }

    public Plugin getOwningPlugin() {
        return this.owningPlugin;
    }

    public boolean isKeepEntitiesInMemory() {
        return this.keepEntitiesInMemory;
    }

    public void setKeepEntitiesInMemory(boolean bool) {
        this.keepEntitiesInMemory = bool;
    }

    protected Integer getNextID() {
        return getNextID(Integer.MIN_VALUE);
    }

    protected Integer getNextID(int index) {
        Set<Integer> ids = this.ENTITIES.keySet();
        while (ids.contains(index)) {
            index++;
        }
        return index;
    }

    public ControllableEntity spawnEntity(ControllableEntityType entityType, Location location) {
        return spawnEntity(entityType, location, true);
    }

    public ControllableEntity spawnEntity(ControllableEntityType entityType, Location location, boolean prepare) {
        if (entityType.isNameRequired())
            throw new NameRequiredException();

        Integer id = getNextID();
        ControllableEntity entity = createEntity(entityType, id);

        if (entity == null)
            return null;

        SafeConstructor<ControllableEntityHandle> entityConstructor = new SafeConstructor<ControllableEntityHandle>(entityType.getHandleClass(), World.class, ControllableEntity.class);
        ControllableEntityHandle handle = entityConstructor.newInstance(WorldUtil.toNMSWorld(location.getWorld()), entity);

        /**
         * TODO: do the spawning
         */

        return entity;
    }

    protected ControllableEntity createEntity(ControllableEntityType entityType, int id) {
        try {

            SafeConstructor<? extends ControllableEntity> constructor = new SafeConstructor<>(entityType.getControllableClass());
            ControllableEntity entity = constructor.newInstance(id, this);
            this.ENTITIES.put(id, entity);
            return entity;

        } catch (Throwable throwable) {
            EntityAPICore.LOGGER.warning("Failed to create an Entity handle for type: " + entityType.getName());
            throwable.printStackTrace();
            return null;
        }
    }

    public Collection<ControllableEntity> getEntities() {
        return Collections.unmodifiableCollection(this.ENTITIES.values());
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder(128);
        builder.append("EntityManager{plugin=" + this.owningPlugin.getName() + ",")
                .append("entities-spawned=" + this.ENTITIES.size() + "}");
        return builder.toString();
    }

    @Override
    public int hashCode() {
        return this.toString().hashCode();
    }
}
