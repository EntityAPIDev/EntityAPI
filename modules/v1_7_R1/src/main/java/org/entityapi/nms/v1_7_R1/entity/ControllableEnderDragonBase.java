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

package org.entityapi.nms.v1_7_R1.entity;

import org.bukkit.entity.EnderDragon;
import org.bukkit.util.Vector;
import org.entityapi.api.entity.ControllableEntityType;
import org.entityapi.api.EntityManager;
import org.entityapi.api.entity.EntitySound;
import org.entityapi.api.entity.type.ControllableEnderDragon;

public class ControllableEnderDragonBase extends ControllableBaseEntity<EnderDragon, ControllableEnderDragonEntity> implements ControllableEnderDragon {

    private boolean useAppliedTargetPosition;
    private Vector targetPosition;

    public ControllableEnderDragonBase(int id, EntityManager manager) {
        super(id, ControllableEntityType.ENDERDRAGON, manager);
    }

    public ControllableEnderDragonBase(int id, ControllableEnderDragonEntity entityHandle, EntityManager manager) {
        this(id, manager);
        this.handle = entityHandle;
        this.loot = entityHandle.getDefaultMaterialLoot();
    }

    @Override
    public boolean isUsingAppliedTargetPosition() {
        return useAppliedTargetPosition;
    }

    @Override
    public void setUsingAppliedTargetPosition(boolean flag) {
        this.useAppliedTargetPosition = flag;
    }

    @Override
    public Vector getTargetPosition() {
        return targetPosition;
    }

    @Override
    public void setTargetPosition(Vector position) {
        this.targetPosition = position;
    }

    @Override
    public void initSounds() {
        this.setSound(EntitySound.IDLE, "mob.enderdragon.growl");
        this.setSound(EntitySound.HIT, "mob.enderdragon.hit");
        this.setSound(EntitySound.DEATH, "mob.enderdragon.end");
        this.setSound(EntitySound.WINGS, "mob.enderdragon.wings");
    }
}