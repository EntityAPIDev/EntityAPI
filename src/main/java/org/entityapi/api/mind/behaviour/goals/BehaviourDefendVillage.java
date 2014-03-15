package org.entityapi.api.mind.behaviour.goals;

import net.minecraft.server.v1_7_R1.EntityIronGolem;
import net.minecraft.server.v1_7_R1.EntityLiving;
import net.minecraft.server.v1_7_R1.MathHelper;
import net.minecraft.server.v1_7_R1.Village;
import org.bukkit.entity.LivingEntity;
import org.entityapi.api.ControllableEntity;
import org.entityapi.api.mind.behaviour.BehaviourType;

public class BehaviourDefendVillage extends BehaviourTarget {

    private EntityLiving target;

    public BehaviourDefendVillage(ControllableEntity controllableEntity) {
        this(controllableEntity, false, true);
    }

    public BehaviourDefendVillage(ControllableEntity controllableEntity, boolean checkSenses, boolean useMelee) {
        super(controllableEntity, checkSenses, useMelee);
    }

    @Override
    public BehaviourType getType() {
        return BehaviourType.ONE;
    }

    @Override
    public String getDefaultKey() {
        return "Defend Village";
    }

    @Override
    public boolean shouldStart() {
        Village village;
        if (this.handle instanceof EntityIronGolem) {
            village = ((EntityIronGolem) this.handle).bX();
        } else {
            village = this.handle.world.villages.getClosestVillage(MathHelper.floor(this.handle.locX), MathHelper.floor(this.handle.locY), MathHelper.floor(this.handle.locZ), 32);
        }

        if (village == null) {
            return false;
        } else {
            this.target = village.b(this.handle);
            if (!this.isSuitableTarget(this.target, false)) {
                if (this.handle.aI().nextInt(20) == 0) {
                    this.target = village.c(this.handle);
                    return this.isSuitableTarget(this.target, false);
                } else {
                    return false;
                }
            } else {
                return true;
            }
        }
    }

    @Override
    public void tick() {
        this.controllableEntity.setTarget((LivingEntity) this.handle.getBukkitEntity());
        super.tick();
    }
}