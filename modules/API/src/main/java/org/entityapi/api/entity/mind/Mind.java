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

package org.entityapi.api.entity.mind;

import org.entityapi.api.entity.ControllableEntity;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Mind {

    protected ControllableEntity controllableEntity;
    protected HashMap<String, Attribute> attributes = new HashMap<>();

    protected BehaviourSelector behaviourSelector;
    protected BehaviourSelector targetSelector;

    protected boolean stationary;
    protected float fixedYaw;
    protected float fixedHeadYaw;
    protected float fixedPitch;

    public Mind() {
    }

    public void setControllableEntity(ControllableEntity controllableEntity) {
        this.controllableEntity = controllableEntity;
        if (this.controllableEntity != null) {
            this.behaviourSelector = new BehaviourSelector(this.controllableEntity);
            this.targetSelector = new BehaviourSelector(this.controllableEntity);
        }
    }

    public ControllableEntity getControllableEntity() {
        return controllableEntity;
    }

    public boolean isStationary() {
        return stationary;
    }

    public void setStationary(boolean stationary) {
        this.stationary = stationary;
    }

    public double getFixedYaw() {
        return fixedYaw;
    }

    public void setFixedYaw(float fixedYaw) {
        this.fixedYaw = fixedYaw;
    }

    public float getFixedHeadYaw() {
        return fixedHeadYaw;
    }

    public void setFixedHeadYaw(float fixedHeadYaw) {
        this.fixedHeadYaw = fixedHeadYaw;
    }

    public float getFixedPitch() {
        return fixedPitch;
    }

    public void setFixedPitch(float fixedPitch) {
        this.fixedPitch = fixedPitch;
    }

    public Map<String, Attribute> getAttributes() {
        return new HashMap<>(attributes);
    }

    public BehaviourSelector getMovementBehaviourSelector() {
        return behaviourSelector;
    }

    public BehaviourSelector getTargetingBehaviourSelector() {
        return targetSelector;
    }

    public void clearAttributes() {
        Iterator<Map.Entry<String, Attribute>> i = getAttributes().entrySet().iterator();
        while (i.hasNext()) {
            clearAttribute(i.next().getValue().getClass());
        }
    }

    public void addAttribute(Attribute attribute) {
        Attribute attr = attribute.applyTo(this);
        if (attr != null) {
            this.clearAttribute(attr.getClass());
            this.attributes.put(attr.getKey(), attr);
        }
    }

    public void clearAttribute(String key) {
        Iterator<Map.Entry<String, Attribute>> i = this.attributes.entrySet().iterator();
        while (i.hasNext()) {
            Map.Entry<String, Attribute> entry = i.next();
            if (entry.getKey().equals(key)) {
                i.remove();
            }
        }
    }

    public void clearAttribute(Class<? extends Attribute> type) {
        Iterator<Map.Entry<String, Attribute>> i = this.attributes.entrySet().iterator();
        while (i.hasNext()) {
            Map.Entry<String, Attribute> entry = i.next();
            if (type.isAssignableFrom(entry.getValue().getClass())) {
                entry.getValue().removeFrom(null);
                i.remove();
            }
        }
    }

    public <T extends Attribute> T getAttribute(Class<T> type) {
        for (Map.Entry<String, Attribute> entry : this.attributes.entrySet()) {
            if (type.isAssignableFrom(entry.getValue().getClass())) {
                return (T) entry.getValue();
            }
        }
        return null;
    }

    public Attribute getAttribute(String key) {
        for (String k : this.attributes.keySet()) {
            if (k.equals(key)) {
                return this.attributes.get(k);
            }
        }
        return null;
    }

    public boolean hasAttribute(Class<? extends Attribute> type) {
        for (Map.Entry<String, Attribute> entry : this.attributes.entrySet()) {
            if (type.isAssignableFrom(entry.getValue().getClass())) {
                return true;
            }
        }
        return false;
    }

    public boolean hasAttribute(String key) {
        for (String k : this.attributes.keySet()) {
            if (k.equals(key)) {
                return true;
            }
        }
        return false;
    }

    public void tick() {
        if (this.behaviourSelector != null) {
            this.behaviourSelector.updateBehaviours();
        }

        if (this.targetSelector != null) {
            this.targetSelector.updateBehaviours();
        }

        for (Attribute attribute : this.attributes.values()) {
            // Make sure the Mind instance is correct
            if (attribute.getMind().equals(this)) {
                attribute.tick();
            }
        }

        if (this.isStationary()) {
            this.getControllableEntity().setPitch(this.fixedPitch);
            this.getControllableEntity().setYaw(this.fixedYaw);
            this.getControllableEntity().setHeadYaw(this.fixedHeadYaw);
        }
    }
}