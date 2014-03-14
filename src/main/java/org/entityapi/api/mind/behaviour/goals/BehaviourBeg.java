package org.entityapi.api.mind.behaviour.goals;

import net.minecraft.server.v1_7_R1.EntityHuman;
import net.minecraft.server.v1_7_R1.EntityLiving;
import net.minecraft.server.v1_7_R1.EntityWolf;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.entityapi.api.ControllableEntity;
import org.entityapi.api.mind.behaviour.Behaviour;
import org.entityapi.api.mind.behaviour.BehaviourType;
import org.entityapi.nms.NMSEntityUtil;

public class BehaviourBeg extends Behaviour {

    private ControllableEntity controllableEntity;
    private EntityLiving handle;
    private Material[] materialsToBegFor;
    private EntityHuman player;
    private float range;
    private int ticks;

    public BehaviourBeg(ControllableEntity controllableEntity, Material[] materialsToBegFor) {
        this(controllableEntity, 8.0F, materialsToBegFor);
    }

    public BehaviourBeg(ControllableEntity controllableEntity, float range, Material[] materialsToBegFor) {
        this.controllableEntity = controllableEntity;
        this.handle = controllableEntity.getHandle();
        this.materialsToBegFor = materialsToBegFor;
        this.range = range;
    }

    @Override
    public BehaviourType getType() {
        return BehaviourType.TWO;
    }

    @Override
    public String getDefaultKey() {
        return "Beg";
    }

    @Override
    public boolean shouldStart() {
        this.player = this.handle.world.findNearbyPlayer(this.handle, (double) this.range);
        return this.player == null ? false : this.isHoldingItem(this.player);
    }

    @Override
    public boolean shouldContinue() {
        if (!player.isAlive()) {
            return false;
        } else if (this.handle.e(this.player) > (double) (this.range * this.range)) {
            return false;
        }
        return this.ticks > 0 && this.isHoldingItem(this.player);
    }

    @Override
    public void start() {
        if (this.handle instanceof EntityWolf) {
            ((EntityWolf) this.handle).m(true);
        }
        this.ticks = 40 + this.handle.aI().nextInt(40);
    }

    @Override
    public void finish() {
        if (this.handle instanceof EntityWolf) {
            ((EntityWolf) this.handle).m(false);
        }
        this.player = null;
    }

    @Override
    public void tick() {
        NMSEntityUtil.getControllerLook(this.handle).a(this.player.locX, this.player.locY + (double) this.player.getHeadHeight(), this.player.locZ, 10.0F, (float) NMSEntityUtil.getMaxHeadRotation(this.handle));
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