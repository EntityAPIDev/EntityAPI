package io.snw.entityapi.entity;

import io.snw.entityapi.api.ControllableEntityType;
import io.snw.entityapi.api.EntitySound;
import org.bukkit.entity.Slime;

public class ControllableSlime extends ControllableAttackingBaseEntity<Slime> {

    public ControllableSlime(ControllableSlimeEntity entityHandle) {
        super(ControllableEntityType.SLIME);
        this.handle = entityHandle;
        this.loot = entityHandle.getDefaultMaterialLoot();
    }

    public void initSounds() {
        for (EntitySound type : new EntitySound[] {EntitySound.IDLE, EntitySound.HURT, EntitySound.DEATH}) {
            this.setSound(type, "mob.slime.big", "big");
            this.setSound(type, "mob.slime.small", "small");
        }
        this.setSound(EntitySound.ATTACK, "mob.attack");
    }
}