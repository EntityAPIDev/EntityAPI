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

package org.entityapi.nms.v1_8_R1.entity;

import net.minecraft.server.v1_8_R1.*;
import net.minecraft.util.com.mojang.authlib.GameProfile;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;
import org.entityapi.api.entity.DespawnReason;
import org.entityapi.api.entity.mind.attribute.*;
import org.entityapi.api.entity.type.ControllablePlayer;
import org.entityapi.api.entity.type.nms.ControllablePlayerHandle;
import org.entityapi.api.events.Action;
import org.entityapi.nms.v1_8_R1.network.FixedNetworkManager;
import org.entityapi.nms.v1_8_R1.network.NullPlayerConnection;
import org.entityapi.nms.v1_8_R1.player.PlayerControllerJump;
import org.entityapi.nms.v1_8_R1.player.PlayerControllerLook;
import org.entityapi.nms.v1_8_R1.player.PlayerControllerMove;
import org.entityapi.nms.v1_8_R1.player.PlayerNavigation;

public class ControllablePlayerEntity extends EntityPlayer implements ControllablePlayerHandle {

    protected ControllablePlayer controllableEntity;

    protected Navigation navigation;
    protected ControllerMove moveController;
    protected ControllerLook lookController;
    protected ControllerJump jumpController;

    public ControllablePlayerEntity(MinecraftServer minecraftServer, WorldServer worldServer, GameProfile gameProfile, PlayerInteractManager playerInteractManager, ControllablePlayer controllablePlayer) {
        this(minecraftServer, worldServer, gameProfile, playerInteractManager);
        this.controllableEntity = controllablePlayer;

        if (this.controllableEntity != null)
            initialise(minecraftServer);
    }

    public ControllablePlayerEntity(MinecraftServer minecraftserver, WorldServer worldserver, GameProfile gameprofile, PlayerInteractManager playerinteractmanager) {
        super(minecraftserver, worldserver, gameprofile, playerinteractmanager);

        initialise(minecraftserver);

        playerinteractmanager.b(EnumGamemode.SURVIVAL);

        this.noDamageTicks = 1;
        this.X = 1;
        this.fauxSleeping = true;
    }

    private void initialise(MinecraftServer minecraftServer) {
        FixedNetworkManager networkManager = new FixedNetworkManager();
        this.playerConnection = new NullPlayerConnection(minecraftServer, networkManager, this);
        networkManager.a(this.playerConnection);

        this.navigation = new PlayerNavigation(this, this.world);
        this.moveController = new PlayerControllerMove(this);
        this.lookController = new PlayerControllerLook(this);
        this.jumpController = new PlayerControllerJump(this);
    }

    public void updateControllers() {
        getControllerLook().a();
        getControllerJump().b();
        getControllerMove().c();
        getNavigation().f();
    }

    public Navigation getNavigation() {
        return this.navigation;
    }

    public ControllerMove getControllerMove() {
        return this.moveController;
    }

    public ControllerLook getControllerLook() {
        return this.lookController;
    }

    public ControllerJump getControllerJump() {
        return this.jumpController;
    }

    @Override
    public ControllablePlayer getControllableEntity() {
        return this.controllableEntity;
    }

    @Override
    public org.bukkit.Material getDefaultLoot() {
        return null;
    }


    @Override
    public void move(double d0, double d1, double d2) {
        if (this.controllableEntity != null && this.controllableEntity.isStationary()) {
            return;
        }
        super.move(d0, d1, d2);
    }

    @Override
    public void h() {
        this.i();

        if(this.noDamageTicks > 0)
            this.noDamageTicks--;

        //Taken from Citizens2#EntityHumanNPC.java#129 - #138 - slightly modified.
        if(Math.abs(motX) < 0.001F && Math.abs(motY) < 0.001F && Math.abs(motZ) < 0.001F)
            motX = motY = motZ = 0;

        this.updateControllers();
        //End Citizens

        if (this.controllableEntity != null) {
            this.controllableEntity.getMind().getAttribute(TickAttribute.class).call(this.controllableEntity);
            if (this.controllableEntity.shouldUpdateAttributes()) {
                this.controllableEntity.getMind().tick();
            }
        }
    }

    @Override
    public void collide(Entity entity) {
        if (this.controllableEntity == null) {
            super.collide(entity);
            return;
        }

        if (!this.controllableEntity.getMind().getAttribute(CollideAttribute.class).call(this.controllableEntity, entity.getBukkitEntity()).isCancelled()) {
            super.collide(entity);
        }
    }

    @Override
    public boolean a(EntityHuman entity) {
        if (this.controllableEntity == null || !(entity.getBukkitEntity() instanceof Player)) {
            return super.c(entity);
        }

        return !this.controllableEntity.getMind().getAttribute(InteractAttribute.class).call(this.controllableEntity, entity.getBukkitEntity(), Action.RIGHT_CLICK).isCancelled();
    }

    @Override
    public boolean damageEntity(DamageSource damageSource, float v) {
        if (this.controllableEntity != null && damageSource.getEntity() != null && damageSource.getEntity().getBukkitEntity() instanceof Player) {
            return !this.controllableEntity.getMind().getAttribute(InteractAttribute.class).call(this.controllableEntity, damageSource.getEntity().getBukkitEntity(), Action.LEFT_CLICK).isCancelled();
        }
        return super.damageEntity(damageSource, v);
    }

    @Override
    public void e(float xMotion, float zMotion) {
        float[] motion = new float[]{xMotion, (float) this.motY, zMotion};
        if (this.controllableEntity != null) {
            ControlledRidingAttribute controlledRidingAttribute = this.controllableEntity.getMind().getAttribute(ControlledRidingAttribute.class);
            if (controlledRidingAttribute != null) {
                controlledRidingAttribute.onRide(motion);
            }
        }
        this.motY = motion[1];
        super.e(motion[0], motion[2]);
    }

    @Override
    public void g(double x, double y, double z) {
        if (this.controllableEntity != null) {
            Vector velocity = this.controllableEntity.getMind().getAttribute(PushAttribute.class).call(this.controllableEntity, new Vector(x, y, z)).getPushVelocity();
            x = velocity.getX();
            y = velocity.getY();
            z = velocity.getZ();
        }
        super.g(x, y, z);
    }

    @Override
    public void die(DamageSource damagesource) {
        if (this.controllableEntity != null) {
            this.controllableEntity.getEntityManager().despawn(this.controllableEntity, DespawnReason.DEATH);
        }
        super.die(damagesource);
    }

    @Override
    public void updateSpawn() {
        PacketPlayOutNamedEntitySpawn packet = new PacketPlayOutNamedEntitySpawn(this);
    }
}
