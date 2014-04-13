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

package org.entityapi.nms.v1_7_R1.entity.mind.behaviour.goals;

import net.minecraft.server.v1_7_R1.EntityHuman;
import net.minecraft.server.v1_7_R1.Item;
import net.minecraft.server.v1_7_R1.ItemStack;
import org.bukkit.Material;
import org.entityapi.api.entity.ControllableEntity;
import org.entityapi.api.entity.mind.behaviour.BehaviourType;
import org.entityapi.nms.v1_7_R1.NMSEntityUtil;
import org.entityapi.nms.v1_7_R1.entity.mind.behaviour.BehaviourBase;

public class BehaviourTempt extends BehaviourBase {

    private org.bukkit.Material temptItemMaterial;
    private boolean isScaredByMovement;
    private double navigationSpeed;

    private double playerX;
    private double playerY;
    private double playerZ;
    private double playerPitch;
    private double playerYaw;
    private EntityHuman nearbyPlayer;
    private int delayTicks;
    private boolean tempted;
    private boolean avoidWater;

    public BehaviourTempt(ControllableEntity controllableEntity, org.bukkit.Material temptItemId) {
        this(controllableEntity, temptItemId, false);
    }

    public BehaviourTempt(ControllableEntity controllableEntity, org.bukkit.Material temptItemId, boolean isScaredByMovement) {
        this(controllableEntity, temptItemId, isScaredByMovement, -1);
    }

    public BehaviourTempt(ControllableEntity controllableEntity, org.bukkit.Material temptItemId, boolean isScaredByMovement, double navigationSpeed) {
        super(controllableEntity);
        this.navigationSpeed = navigationSpeed;
        this.isScaredByMovement = isScaredByMovement;
        this.temptItemMaterial = temptItemId;
    }

    @Deprecated
    public BehaviourTempt(ControllableEntity controllableEntity, int temptItemId) {
        this(controllableEntity, temptItemId, false);
    }

    @Deprecated
    public BehaviourTempt(ControllableEntity controllableEntity, int temptItemId, boolean isScaredByMovement) {
        this(controllableEntity, temptItemId, isScaredByMovement, -1);
    }

    @Deprecated
    public BehaviourTempt(ControllableEntity controllableEntity, int temptItemId, boolean isScaredByMovement, double navigationSpeed) {
        super(controllableEntity);
        this.navigationSpeed = navigationSpeed;
        this.isScaredByMovement = isScaredByMovement;
        this.temptItemMaterial = Material.getMaterial(temptItemId);
    }

    @Override
    public BehaviourType getType() {
        return BehaviourType.ACTION;
    }

    @Override
    public String getDefaultKey() {
        return "Tempt";
    }

    @Override
    public boolean shouldStart() {
        if (this.delayTicks > 0) {
            --this.delayTicks;
            return false;
        } else {
            this.nearbyPlayer = this.getHandle().world.findNearbyPlayer(this.getHandle(), 10.0D);
            if (this.nearbyPlayer == null) {
                return false;
            } else {
                ItemStack heldItem = this.nearbyPlayer.bD();

                return heldItem == null ? false : Item.b(heldItem.getItem()) == this.temptItemMaterial.getId();
            }
        }
    }

    @Override
    public boolean shouldContinue() {
        if (this.isScaredByMovement) {
            if (this.getHandle().e(this.nearbyPlayer) < 36.0D) {
                if (this.nearbyPlayer.e(this.playerX, this.playerY, this.playerZ) > 0.010000000000000002D) {
                    return false;
                }

                if (Math.abs((double) this.nearbyPlayer.pitch - this.playerPitch) > 5.0D || Math.abs((double) this.nearbyPlayer.yaw - this.playerYaw) > 5.0D) {
                    return false;
                }
            } else {
                this.playerX = this.nearbyPlayer.locX;
                this.playerY = this.nearbyPlayer.locY;
                this.playerZ = this.nearbyPlayer.locZ;
            }

            this.playerPitch = (double) this.nearbyPlayer.pitch;
            this.playerYaw = (double) this.nearbyPlayer.yaw;
        }

        return this.shouldStart();
    }

    @Override
    public void start() {
        this.playerX = this.nearbyPlayer.locX;
        this.playerY = this.nearbyPlayer.locY;
        this.playerZ = this.nearbyPlayer.locZ;
        this.tempted = true;
        this.avoidWater = NMSEntityUtil.getNavigation(this.getHandle()).a();
        NMSEntityUtil.getNavigation(this.getHandle()).a(false);
    }

    @Override
    public void finish() {
        this.nearbyPlayer = null;
        NMSEntityUtil.getNavigation(this.getHandle()).h();
        this.delayTicks = 100;
        this.tempted = false;
        NMSEntityUtil.getNavigation(this.getHandle()).a(this.avoidWater);
    }

    @Override
    public void tick() {
        NMSEntityUtil.getControllerLook(this.getHandle()).a(this.nearbyPlayer, 30.0F, (float) NMSEntityUtil.getMaxHeadRotation(this.getHandle()));
        if (this.getHandle().e(this.nearbyPlayer) < 6.25D) {
            NMSEntityUtil.getNavigation(this.getHandle()).h();
        } else {
            this.getControllableEntity().navigateTo(this.nearbyPlayer.getBukkitEntity(), this.navigationSpeed > 0 ? this.navigationSpeed : this.getControllableEntity().getSpeed());
        }
    }
    
    public boolean isTempted() {
        return this.tempted;
    }
}