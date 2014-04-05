package org.entityapi.api;

import org.bukkit.entity.LivingEntity;
import org.entityapi.EntityAPICore;

public enum ProjectileType {

    DEFAULT,
    ARROW,
    SNOWBALL,
    FIREBALL,
    SMALL_FIREBALL,
    THROWN_POTION;

    public void shootProjectile(ControllableEntity controllableEntity, LivingEntity target, float strength) {
        EntityAPICore.getBasicEntityUtil().shootProjectile(this, controllableEntity, target, strength);
    }
}