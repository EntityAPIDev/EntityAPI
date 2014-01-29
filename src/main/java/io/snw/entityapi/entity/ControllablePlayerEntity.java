package io.snw.entityapi.entity;

import io.snw.entityapi.api.ControllableEntity;
import io.snw.entityapi.api.ControllableEntityHandle;
import io.snw.entityapi.nms.FixedNetworkManager;
import io.snw.entityapi.nms.NullPlayerConnection;
import net.minecraft.server.v1_7_R1.*;
import net.minecraft.util.com.mojang.authlib.GameProfile;

public class ControllablePlayerEntity extends EntityPlayer implements ControllableEntityHandle {

    public ControllablePlayerEntity(MinecraftServer minecraftserver, WorldServer worldserver, GameProfile gameprofile, PlayerInteractManager playerinteractmanager) {
        super(minecraftserver, worldserver, gameprofile, playerinteractmanager);

        NetworkManager manager = new FixedNetworkManager();
        playerConnection = new NullPlayerConnection(server, manager, this);
        manager.a(playerConnection);

        noDamageTicks = 1;
    }

    @Override
    public ControllableEntity getControllableEntity() {
        return null;
    }

    @Override
    public org.bukkit.Material getDefaultMaterialLoot() {
        return null;
    }
}
