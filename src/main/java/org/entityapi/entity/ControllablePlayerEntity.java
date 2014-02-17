package org.entityapi.entity;

import net.minecraft.server.v1_7_R1.*;
import net.minecraft.util.com.mojang.authlib.GameProfile;
import org.entityapi.api.ControllableEntity;
import org.entityapi.api.ControllableEntityHandle;
import org.entityapi.nms.PlayerNavigation;
import org.entityapi.nms.network.FixedNetworkManager;
import org.entityapi.nms.network.NullPlayerConnection;

public class ControllablePlayerEntity extends EntityPlayer implements ControllableEntityHandle {

    private Navigation navigation;

    public ControllablePlayerEntity(MinecraftServer minecraftserver, WorldServer worldserver, GameProfile gameprofile, PlayerInteractManager playerinteractmanager) {
        super(minecraftserver, worldserver, gameprofile, playerinteractmanager);

        NetworkManager manager = new FixedNetworkManager();
        playerConnection = new NullPlayerConnection(server, manager, this);
        manager.a(playerConnection);

        noDamageTicks = 1;

        this.navigation = new PlayerNavigation(this);
    }

    @Override
    public ControllableEntity getControllableEntity() {
        return null;
    }

    @Override
    public org.bukkit.Material getDefaultMaterialLoot() {
        return null;
    }

    public Navigation getNavigation() {
        return this.navigation;
    }
}
