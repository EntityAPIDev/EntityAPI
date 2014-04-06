package org.entityapi.api;

import org.bukkit.entity.LivingEntity;

public interface Attacking {

    public LivingEntity getTarget();

    public void setTarget(LivingEntity target);
}