package io.snw.entityapi;

import com.google.common.collect.Lists;
import io.snw.entityapi.api.ControllableEntity;
import io.snw.entityapi.api.ControllableEntityHandle;
import io.snw.entityapi.api.ControllableEntityType;
import io.snw.entityapi.api.mind.Mind;
import io.snw.entityapi.api.mind.behaviour.Behaviour;
import io.snw.entityapi.reflection.SafeConstructor;
import io.snw.entityapi.utils.WorldUtil;
import net.minecraft.server.v1_7_R1.World;
import org.bukkit.Location;

import java.util.Arrays;
import java.util.List;

public class EntityCreator {

    private EntityCreator INSTANCE;

    private EntityManager ENTITYMANAGER;
    private int ID;
    private ControllableEntityType TYPE;
    private String NAME;
    private Location LOCATION;
    private ControllableEntity ENTITY;
    private boolean PREPARE;
    private Mind MIND;
    private List<Behaviour> BEHAVIOURS;

    {
        this.BEHAVIOURS = Lists.newArrayList();
        this.PREPARE = false;
    }

    public EntityCreator(EntityManager entityManager) {
        this.ENTITYMANAGER = entityManager;
        this.INSTANCE = this;
    }

    public EntityCreator withID(int id) {
        this.ID = id;
        return this.INSTANCE;
    }

    public EntityCreator withType(ControllableEntityType entityType) {
        this.TYPE = entityType;
        return this.INSTANCE;
    }

    public EntityCreator withName(String name) {
        this.NAME = name;
        return this.INSTANCE;
    }

    public EntityCreator atLocation(Location location) {
        this.LOCATION = location;
        return this.INSTANCE;
    }

    public EntityCreator withMind(Mind mind) {
        this.MIND = mind;
        return this.INSTANCE;
    }

    public EntityCreator withBehaviours(Behaviour... behaviour) {
        this.BEHAVIOURS = Arrays.asList(behaviour);
        return this.INSTANCE;
    }

    public EntityCreator withDefaults() {
        this.PREPARE = true;
        return this.INSTANCE;
    }

    public <T extends ControllableEntity> T  create() {
        SafeConstructor<? extends ControllableEntity> constructor = new SafeConstructor<>(this.TYPE.getControllableClass());
        ControllableEntity entity = constructor.newInstance(this.ID, this);

        SafeConstructor<ControllableEntityHandle> entityConstructor = new SafeConstructor<ControllableEntityHandle>(this.TYPE.getHandleClass(), World.class, ControllableEntity.class);
        ControllableEntityHandle handle = entityConstructor.newInstance(WorldUtil.toNMSWorld(this.LOCATION.getWorld()), entity);

        return null;
    }
}
