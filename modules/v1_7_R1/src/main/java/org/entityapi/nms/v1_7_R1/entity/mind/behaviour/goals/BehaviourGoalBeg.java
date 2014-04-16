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
import net.minecraft.server.v1_7_R1.EntityWolf;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.entityapi.api.entity.ControllableEntity;
import org.entityapi.api.entity.mind.behaviour.BehaviourType;
import org.entityapi.nms.v1_7_R1.NMSEntityUtil;
import org.entityapi.nms.v1_7_R1.entity.mind.behaviour.BehaviourGoalBase;

public class BehaviourGoalBeg extends BehaviourGoalBase {

    private Material[] materialsToBegFor;
    private EntityHuman player;
    private float range;
    private int ticks;

    public BehaviourGoalBeg(ControllableEntity controllableEntity, Material[] materialsToBegFor, float range) {
        super(controllableEntity);
        this.materialsToBegFor = materialsToBegFor;
        this.range = range;
    }

    @Override
    public BehaviourType getType() {
        return BehaviourType.ATTENTION;
    }

    @Override
    public String getDefaultKey() {
        return "Beg";
    }

    @Override
    public boolean shouldStart() {
        this.player = this.getHandle().world.findNearbyPlayer(this.getHandle(), (double) this.range);
        return this.player == null ? false : this.isHoldingItem(this.player);
    }

    @Override
    public boolean shouldContinue() {
        if (!player.isAlive()) {
            return false;
        } else if (this.getHandle().e(this.player) > (double) (this.range * this.range)) {
            return false;
        }
        return this.ticks > 0 && this.isHoldingItem(this.player);
    }

    @Override
    public void start() {
        if (this.getHandle() instanceof EntityWolf) {
            ((EntityWolf) this.getHandle()).m(true);
        }
        this.ticks = 40 + this.getHandle().aI().nextInt(40);
    }

    @Override
    public void finish() {
        if (this.getHandle() instanceof EntityWolf) {
            ((EntityWolf) this.getHandle()).m(false);
        }
        this.player = null;
    }

    @Override
    public void tick() {
        NMSEntityUtil.getControllerLook(this.getHandle()).a(this.player.locX, this.player.locY + (double) this.player.getHeadHeight(), this.player.locZ, 10.0F, (float) NMSEntityUtil.getMaxHeadRotation(this.getHandle()));
        --this.ticks;
    }

    private boolean isHoldingItem(EntityHuman human) {
        ItemStack held = human.getBukkitEntity().getItemInHand();
        if (held == null) {
            return false;
        }

        for (Material m : this.materialsToBegFor) {
            if (held.getType() == m) {
                return true;
            }
        }
        return false;
    }
}