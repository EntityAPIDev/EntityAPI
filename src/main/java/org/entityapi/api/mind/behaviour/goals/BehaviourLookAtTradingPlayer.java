package org.entityapi.api.mind.behaviour.goals;

import net.minecraft.server.v1_7_R1.EntityVillager;
import org.bukkit.entity.HumanEntity;
import org.entityapi.api.ControllableEntity;

public class BehaviourLookAtTradingPlayer extends BehaviourLookAtNearestEntity {

    public BehaviourLookAtTradingPlayer(ControllableEntity controllableEntity) {
        this(controllableEntity, 8.0F);
    }

    public BehaviourLookAtTradingPlayer(ControllableEntity controllableEntity, float minDistance) {
        super(controllableEntity, HumanEntity.class, minDistance);
    }

    @Override
    public String getDefaultKey() {
        return "Look Trading Player";
    }

    @Override
    public boolean shouldStart() {
        if (this.getHandle() instanceof EntityVillager) {
            if (((EntityVillager) this.getHandle()).ca()) {
                this.target = ((EntityVillager) this.getHandle()).b();
                return true;
            } else return false;
        } else {
            // TODO: Trading Attribute
        }
        return false;
    }
}