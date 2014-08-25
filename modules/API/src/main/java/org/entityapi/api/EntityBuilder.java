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

    private EntityManager entityManager;
    private ControllableEntityType type;
    private String name;
    private Location location;
    private boolean forceSpawn;
    private boolean prepare;
    private Mind mind;
    private HashMap<Behaviour, Integer> behaviours;

    public EntityBuilder(Plugin plugin) {
        this(EntityAPI.getManagerFor(plugin));
    }

    public EntityBuilder(EntityManager entityManager) {
        if (entityManager == null) {
            throw new IllegalArgumentException("EntityManager cannot be NULL!");
        }
        this.entityManager = entityManager;
        this.behaviours = new HashMap<>();
        this.prepare = false;
    }

    public EntityBuilder withType(ControllableEntityType entityType) {
        this.type = entityType;
        return this;
    }

    public EntityBuilder withName(String name) {
        this.name = name;
        return this;
    }

    public EntityBuilder atLocation(Location location) {
        this.location = location;
        return this;
    }

    public EntityBuilder forceSpawn(boolean flag) {
        this.forceSpawn = flag;
        return this;
    }

    public EntityBuilder withMind(Mind mind) {
        this.mind = mind;
        return this;
    }

    public EntityBuilder withBehaviours(Behaviour... behaviours) {
        for (Behaviour behaviour1 : behaviours) {
            this.behaviours.put(behaviour1, 1);
        }
        return this;
    }

    public EntityBuilder withBehaviours(HashMap<Behaviour, Integer> prioritisedBehaviours) {
        for (Map.Entry<Behaviour, Integer> entry : prioritisedBehaviours.entrySet()) {
            this.behaviours.put(entry.getKey(), entry.getValue());
        }
        return this;
    }

    public EntityBuilder withDefaults() {
        this.prepare = true;
        return this;
    }

    public ControllableEntity create() {
        if (this.type == null)
            throw new NullPointerException("ControllableEntity Type cannot be null.");

        if (this.location == null)
            throw new NullPointerException("Location cannot be null.");

        if (this.type.isNameRequired() && this.name.isEmpty())
            throw new IllegalStateException("Entity: " + this.type.toString() + " requires a name!");

        int id = this.entityManager.getNextID();
        ControllableEntity entity;

        if (this.type.isNameRequired()) {

            // Fix the name, in case it's too long
            if (this.name.length() > 16)
                this.name = name.substring(0, 16);

            entity = new Reflection().reflect(this.type.getControllableClass()).getSafeConstructor(int.class, String.class, EntityManager.class).getAccessor().invoke(id, this.name, this.entityManager);
        } else {
             entity = new Reflection().reflect(this.type.getControllableClass()).getSafeConstructor(int.class, EntityManager.class).getAccessor().invoke(id, this.entityManager);
        }

        if (entity != null) {
            if (this.prepare || this.mind == null) {
                this.mind = new Mind();
            }
            this.mind.setControllableEntity(entity);
            if (this.prepare) {
                entity.setDefaultBehaviours();
            } else {
                for (Map.Entry<Behaviour, Integer> entry : this.behaviours.entrySet()) {
                    entity.getMind().getMovementBehaviourSelector().addBehaviour(entry.getKey(), entry.getValue());
                }
            }
            if (this.name != null) {
                entity.setName(this.name);
            }

            if (forceSpawn) {
                if (!this.location.getChunk().isLoaded()) {
                    this.location.getChunk().load();
                }
            }
            entityManager.getChunkManager().queueSpawn(entity, this.location);
            return entity;
        }

        return null;
    }
}
