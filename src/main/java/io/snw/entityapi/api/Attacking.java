package io.snw.entityapi.api;

import org.bukkit.entity.LivingEntity;

public interface Attacking {

    public LivingEntity getTarget();
    
    public LivingEntity getAttackingEntity();

    public void setTarget(LivingEntity target);
}