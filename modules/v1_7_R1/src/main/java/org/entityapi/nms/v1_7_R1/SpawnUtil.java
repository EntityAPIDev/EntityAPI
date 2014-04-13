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

package org.entityapi.nms.v1_7_R1;

import net.minecraft.server.v1_7_R1.EntityLiving;
import net.minecraft.server.v1_7_R1.World;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_7_R1.CraftWorld;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.entityapi.api.ISpawnUtil;
import org.entityapi.api.entity.ControllableEntity;
import org.entityapi.api.entity.ControllableEntityHandle;
import org.entityapi.api.reflection.SafeConstructor;
import org.entityapi.api.utils.WorldUtil;

public class SpawnUtil implements ISpawnUtil {

    @Override
    public boolean spawnEntity(ControllableEntity controllableEntity, Location spawnLocation) {
        World mcWorld = ((CraftWorld) spawnLocation.getWorld()).getHandle();

        SafeConstructor<ControllableEntityHandle> entityConstructor = new SafeConstructor<>(controllableEntity.getEntityType().getHandleClass(), World.class, ControllableEntity.class);
        ControllableEntityHandle controllableEntityHandle = entityConstructor.newInstance(WorldUtil.toNMSWorld(spawnLocation.getWorld()), this);
        EntityLiving handle = (EntityLiving) controllableEntityHandle;
        handle.setPositionRotation(spawnLocation.getX(), spawnLocation.getY(), spawnLocation.getZ(), spawnLocation.getYaw(), spawnLocation.getPitch());
        if (!spawnLocation.getChunk().isLoaded()) {
            spawnLocation.getChunk().load();
        }
        return mcWorld.addEntity(handle, CreatureSpawnEvent.SpawnReason.CUSTOM);
    }
}