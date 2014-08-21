/*
 * Copyright (C) EntityAPI Team
 *
 * This file is part of EntityAPI.
 *
 * EntityAPI is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * EntityAPI is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with EntityAPI.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.entityapi.api;

import com.captainbern.reflection.Reflection;
import org.bukkit.Location;
import org.bukkit.plugin.Plugin;
import org.entityapi.api.entity.ControllableEntity;
import org.entityapi.api.entity.ControllableEntityType;
import org.entityapi.api.entity.mind.Mind;
import org.entityapi.api.entity.mind.behaviour.Behaviour;
import org.entityapi.api.plugin.EntityAPI;
import java.util.HashMap;
import java.util.Map;

public class EntityBuilder {

    private EntityManager ENTITYMANAGER;
    private ControllableEntityType TYPE;
    private String NAME;
    private Location LOCATION;
    private boolean FORCE_SPAWN;
    private boolean PREPARE;
    private Mind MIND;
    private HashMap<Behaviour, Integer> BEHAVIOURS;

    public EntityBuilder(Plugin plugin) {
        this(EntityAPI.getManagerFor(plugin));
    }

    public EntityBuilder(EntityManager entityManager) {
        if (entityManager == null) {
            throw new IllegalArgumentException("EntityManager cannot be NULL!");
        }
        this.ENTITYMANAGER = entityManager;
        this.BEHAVIOURS = new HashMap<>();
        this.PREPARE = false;
    }

    public EntityBuilder withType(ControllableEntityType entityType) {
        this.TYPE = entityType;
        return this;
    }

    public EntityBuilder withName(String name) {
        this.NAME = name;
        return this;
    }

    public EntityBuilder atLocation(Location location) {
        this.LOCATION = location;
        return this;
    }

    public EntityBuilder forceSpawn(boolean flag) {
        this.FORCE_SPAWN = flag;
        return this;
    }

    public EntityBuilder withMind(Mind mind) {
        this.MIND = mind;
        return this;
    }

    public EntityBuilder withBehaviours(Behaviour... behaviours) {
        for (Behaviour behaviour1 : behaviours) {
            this.BEHAVIOURS.put(behaviour1, 1);
        }
        return this;
    }

    public EntityBuilder withBehaviours(HashMap<Behaviour, Integer> prioritisedBehaviours) {
        for (Map.Entry<Behaviour, Integer> entry : prioritisedBehaviours.entrySet()) {
            this.BEHAVIOURS.put(entry.getKey(), entry.getValue());
        }
        return this;
    }

    public EntityBuilder withDefaults() {
        this.PREPARE = true;
        return this;
    }

    public ControllableEntity create() {
        if (this.TYPE == null) {
            throw new NullPointerException("ControllableEntity Type cannot be null.");
        }
        if (this.LOCATION == null) {
            throw new NullPointerException("Location cannot be null.");
        }

        int id = this.ENTITYMANAGER.getNextID();
        ControllableEntity entity = new Reflection().reflect(this.TYPE.getControllableClass()).getSafeConstructor(int.class, EntityManager.class).getAccessor().invoke(id, this.ENTITYMANAGER);
        if (entity != null) {
            if (this.PREPARE || this.MIND == null) {
                this.MIND = new Mind();
            }
            this.MIND.setControllableEntity(entity);
            if (this.PREPARE) {
                entity.setDefaultBehaviours();
            } else {
                for (Map.Entry<Behaviour, Integer> entry : this.BEHAVIOURS.entrySet()) {
                    entity.getMind().getMovementBehaviourSelector().addBehaviour(entry.getKey(), entry.getValue());
                }
            }
            if (this.NAME != null) {
                entity.setName(this.NAME);
            }

            if (FORCE_SPAWN) {
                if (!this.LOCATION.getChunk().isLoaded()) {
                    this.LOCATION.getChunk().load();
                }
            }
            ENTITYMANAGER.getChunkManager().queueSpawn(entity, this.LOCATION);
            return entity;
        }
        return null;
    }
}
