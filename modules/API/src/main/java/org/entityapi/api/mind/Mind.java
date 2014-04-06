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

package org.entityapi.api.mind;

import org.entityapi.api.ControllableEntity;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Mind {

    protected ControllableEntity controllableEntity;
    protected HashMap<String, Attribute> attributes = new HashMap<>();

    protected BehaviourSelector behaviourSelector;

    public Mind() {
    }

    public void setControllableEntity(ControllableEntity controllableEntity) {
        this.controllableEntity = controllableEntity;
        this.behaviourSelector = controllableEntity == null ? null : new BehaviourSelector(this.controllableEntity);
    }

    public ControllableEntity getControllableEntity() {
        return controllableEntity;
    }

    public HashMap<String, Attribute> getAttributes() {
        return attributes;
    }

    public BehaviourSelector getBehaviourSelector() {
        return behaviourSelector;
    }

    public void addAttribute(Attribute attribute) {
        this.clearAttribute(attribute);
        this.attributes.put(attribute.getKey(), attribute);
    }

    public void clearAttribute(Attribute attribute) {
        Iterator<Map.Entry<String, Attribute>> i = this.attributes.entrySet().iterator();
        while (i.hasNext()) {
            if (i.next().getKey().equals(attribute.getKey())) {
                i.remove();
            }
        }
    }

    public Attribute getAttribute(String key) {
        for (String k : this.attributes.keySet()) {
            if (k.equals(key)) {
                return this.attributes.get(k);
            }
        }
        return null;
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
        for (Attribute b : this.attributes.values()) {
            b.tick();
        }
    }
}