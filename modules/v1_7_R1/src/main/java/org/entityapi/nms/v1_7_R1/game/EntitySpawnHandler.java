package org.entityapi.nms.v1_7_R1.game;

import com.captainbern.reflection.Reflection;
import com.captainbern.reflection.SafeConstructor;
import net.minecraft.server.v1_7_R1.World;
import net.minecraft.server.v1_7_R1.WorldServer;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_7_R1.CraftWorld;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.entityapi.api.entity.ControllableEntity;
import org.entityapi.api.entity.ControllableEntityHandle;
import org.entityapi.game.EntityRegistrationEntry;
import org.entityapi.game.GameRegistry;
import org.entityapi.game.IEntityRegistry;
import org.entityapi.game.IEntitySpawnHandler;

public class EntitySpawnHandler extends IEntitySpawnHandler {

    @Override
    public <T extends ControllableEntityHandle<? extends LivingEntity>> T createHandle(ControllableEntity entity, Location location) {
        SafeConstructor<ControllableEntityHandle> entityConstructor = new Reflection().reflect(entity.getEntityType().getHandleClass()).getSafeConstructor(World.class, entity.getEntityType().getControllableInterface());

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

        return handle;
    }
}
