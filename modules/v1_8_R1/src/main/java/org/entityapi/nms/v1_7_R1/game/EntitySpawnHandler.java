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

package org.entityapi.nms.v1_7_R1.game;

import com.captainbern.reflection.Reflection;
import com.captainbern.reflection.SafeConstructor;
import com.google.common.collect.Maps;
import net.minecraft.server.v1_7_R1.Entity;
import net.minecraft.server.v1_7_R1.PlayerInteractManager;
import net.minecraft.server.v1_7_R1.World;
import net.minecraft.server.v1_7_R1.WorldServer;
import net.minecraft.util.com.mojang.authlib.GameProfile;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.craftbukkit.v1_7_R1.CraftWorld;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.entityapi.api.entity.ControllableEntity;
import org.entityapi.api.entity.ControllableEntityHandle;
import org.entityapi.api.entity.ControllableEntityType;
import org.entityapi.api.entity.type.ControllablePlayer;
import org.entityapi.api.entity.type.nms.ControllablePlayerHandle;
import org.entityapi.game.EntityRegistrationEntry;
import org.entityapi.game.GameRegistry;
import org.entityapi.game.IEntityRegistry;
import org.entityapi.game.IEntitySpawnHandler;
import org.entityapi.nms.v1_7_R1.entity.ControllablePlayerEntity;

import java.util.Map;
import java.util.UUID;

public class EntitySpawnHandler implements IEntitySpawnHandler {

    private static final Map<ControllableEntityType, SafeConstructor<ControllableEntityHandle>> typeToConstructor = Maps.newHashMap();

    @Override
    public <T extends ControllableEntityHandle<? extends LivingEntity>> T createEntityHandle(ControllableEntity entity, Location location) {
        SafeConstructor<ControllableEntityHandle> entityConstructor = getConstructorFor(entity.getEntityType());

        EntityRegistrationEntry oldEntry = GameRegistry.get(IEntityRegistry.class).getDefaultEntryFor(entity.getEntityType());
        GameRegistry.get(IEntityRegistry.class).register(new EntityRegistrationEntry(
                entity.getEntityType().getName(),
                entity.getEntityType().getId(),
                entity.getEntityType().getHandleClass()
        ));

        WorldServer worldServer = ((CraftWorld) location.getWorld()).getHandle();
        T handle = (T) entityConstructor.getAccessor().invoke(worldServer, entity);

        handle.setPositionRotation(location.getX(), location.getY(), location.getZ(), location.getYaw(), location.getPitch());
        worldServer.addEntity((net.minecraft.server.v1_7_R1.Entity) handle, CreatureSpawnEvent.SpawnReason.CUSTOM);

        GameRegistry.get(IEntityRegistry.class).register(oldEntry);

        Material beneath = location.getBlock().getRelative(BlockFace.DOWN).getType();
        if (beneath.isBlock()) { // What lies beneath
            ((Entity) handle).onGround = true;
        }

        return handle;
    }

    private SafeConstructor<ControllableEntityHandle> getConstructorFor(ControllableEntityType type) {
        SafeConstructor<ControllableEntityHandle> entityTypeSafeConstructor = typeToConstructor.get(type);

        if (entityTypeSafeConstructor == null) {
            entityTypeSafeConstructor = new Reflection().reflect(type.getHandleClass()).getSafeConstructor(World.class, type.getControllableInterface());
            typeToConstructor.put(type, entityTypeSafeConstructor);
        }

        return entityTypeSafeConstructor;
    }

    @Override
    public ControllablePlayerHandle createPlayerHandle(ControllablePlayer player, Location location, String name, UUID uuid) {
        WorldServer worldServer = ((CraftWorld) location.getWorld()).getHandle();

        if (name.length() > 16)
            name = name.substring(0, 16);

        GameProfile profile = new GameProfile(uuid.toString().replaceAll("-", ""), name);

        ControllablePlayerEntity handle = new ControllablePlayerEntity(worldServer.getMinecraftServer(), worldServer, profile, new PlayerInteractManager(worldServer), player);
        handle.setPositionRotation(location.getX(), location.getY(), location.getZ(), location.getYaw(), location.getPitch());
        worldServer.addEntity(handle);
        handle.world.players.remove(handle);

        if (location.getBlock().getRelative(BlockFace.DOWN).getType().isBlock())
            handle.onGround = true;

        return handle;
    }
}
