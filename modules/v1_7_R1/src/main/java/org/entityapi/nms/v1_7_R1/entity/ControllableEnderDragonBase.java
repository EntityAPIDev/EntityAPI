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

package org.entityapi.nms.v1_7_R1.entity;

import net.minecraft.server.v1_7_R1.PathEntity;
import net.minecraft.server.v1_7_R1.PathPoint;
import org.bukkit.Bukkit;
import org.bukkit.entity.ComplexEntityPart;
import org.bukkit.entity.EnderDragon;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.util.Vector;
import org.entityapi.api.EntityManager;
import org.entityapi.api.entity.ControllableEntityType;
import org.entityapi.api.entity.EntitySound;
import org.entityapi.api.entity.type.ControllableEnderDragon;
import org.entityapi.api.plugin.EntityAPI;

public class ControllableEnderDragonBase extends ControllableBaseEntity<EnderDragon, ControllableEnderDragonEntity> implements ControllableEnderDragon {

    private boolean useAppliedTargetPosition;
    private Vector targetPosition;
    private boolean destroyBlocks;

    public ControllableEnderDragonBase(int id, EntityManager manager) {
        super(id, ControllableEntityType.ENDERDRAGON, manager);
        Bukkit.getPluginManager().registerEvents(new Listener() {

            @EventHandler
            public void onEntityExplode(EntityExplodeEvent event) {
                if (!shouldDestroyBlocks()) {
                    Entity entity = event.getEntity();
                    if (entity instanceof EnderDragon && entity.equals(getBukkitEntity())) {
                        event.setCancelled(true);
                    } else if (entity instanceof ComplexEntityPart && ((ComplexEntityPart) entity).getParent().equals(getBukkitEntity())) {
                        event.setCancelled(true);
                    }
                }
            }

        }, EntityAPI.getCore());
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

    public void setDestroyBlocks(boolean destroyBlocks) {
        this.destroyBlocks = destroyBlocks;
    }

    public boolean shouldDestroyBlocks() {
        return destroyBlocks;
    }

    @Override
    public boolean navigateTo(PathEntity path, double speed) {
        PathPoint point = path.c();
        this.setTargetPosition(new Vector(point.a, point.b, point.c));
        return true;
    }

    @Override
    public void initSounds() {
        this.setSound(EntitySound.IDLE, "mob.enderdragon.growl");
        this.setSound(EntitySound.HIT, "mob.enderdragon.hit");
        this.setSound(EntitySound.DEATH, "mob.enderdragon.end");
        this.setSound(EntitySound.WINGS, "mob.enderdragon.wings");
    }
}