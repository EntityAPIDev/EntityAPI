/*
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

package org.entityapi;

import org.bukkit.Location;
import org.entityapi.api.ControllableEntity;
import org.entityapi.api.ControllableEntityType;
import org.entityapi.api.EntityManager;
import org.entityapi.api.mind.behaviour.Behaviour;
import org.entityapi.api.mind.Mind;
import org.entityapi.api.reflection.SafeConstructor;
import org.entityapi.exceptions.ControllableEntitySpawnException;

import java.util.HashMap;
import java.util.Map;

public class EntityCreator {

    private EntityManager ENTITYMANAGER;
    private int ID;
    private ControllableEntityType TYPE;
    private String NAME;
    private Location LOCATION;
    private boolean PREPARE;
    private Mind MIND;
    private HashMap<Behaviour, Integer> BEHAVIOURS;

    public EntityCreator(EntityManager entityManager) {
        this.ENTITYMANAGER = entityManager;
        this.BEHAVIOURS = new HashMap<>();
        this.PREPARE = false;
    }

    public EntityCreator withID(int id) {
        this.ID = id;
        return this;
    }

    public EntityCreator withType(ControllableEntityType entityType) {
        this.TYPE = entityType;
        return this;
    }

    public EntityCreator withName(String name) {
        this.NAME = name;
        return this;
    }

    public EntityCreator atLocation(Location location) {
        this.LOCATION = location;
        return this;
    }

    public EntityCreator withMind(Mind mind) {
        this.MIND = mind;
        return this;
    }

    public EntityCreator withBehaviours(Behaviour... behaviours) {
        for (Behaviour behaviour1 : behaviours) {
            this.BEHAVIOURS.put(behaviour1, 1);
        }
        return this;
    }

    public EntityCreator withBehaviours(HashMap<Behaviour, Integer> prioritisedBehaviours) {
        for (Map.Entry<Behaviour, Integer> entry : prioritisedBehaviours.entrySet()) {
            this.BEHAVIOURS.put(entry.getKey(), entry.getValue());
        }
        return this;
    }

    public EntityCreator withDefaults() {
        this.PREPARE = true;
        return this;
    }

    public <T extends ControllableEntity> T create() {
        if (this.TYPE == null) {
            throw new NullPointerException("ControllableEntity Type cannot be null.");
        }
        if (this.LOCATION == null) {
            throw new NullPointerException("Location cannot be null.");
        }
        SafeConstructor<? extends ControllableEntity> constructor = new SafeConstructor<>(this.TYPE.getControllableClass());
        ControllableEntity entity = constructor.newInstance(this.ID, this.ENTITYMANAGER);
        if (entity != null) {
            if (entity.spawnEntity(this.LOCATION)) {
                if (this.PREPARE || this.MIND == null) {
                    this.MIND = new Mind();
                }
                this.MIND.setControllableEntity(entity);
                if (this.PREPARE) {
                    entity.setDefaultBehaviours();
                } else {
                    for (Map.Entry<Behaviour, Integer> entry : this.BEHAVIOURS.entrySet()) {
                        entity.getMind().getBehaviourSelector().addBehaviour(entry.getKey(), entry.getValue());
                    }
                }
                if (this.NAME != null) {
                    entity.setName(this.NAME);
                }
                try {
                    return (T) entity;
                } catch (ClassCastException e) {
                    throw new ControllableEntitySpawnException(e);
                }
            } else {
                throw new ControllableEntitySpawnException();
            }
        }
        return null;
    }
}
