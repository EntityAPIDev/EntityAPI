package io.snw.entityapi.entity;

import io.snw.entityapi.api.ControllableEntity;
import io.snw.entityapi.api.ControllableEntityHandle;
import io.snw.entityapi.api.EntitySound;
import net.minecraft.server.v1_7_R1.EntityEnderDragon;
import net.minecraft.server.v1_7_R1.Item;
import net.minecraft.server.v1_7_R1.PathfinderGoalSelector;
import net.minecraft.server.v1_7_R1.World;
import org.bukkit.craftbukkit.v1_7_R1.util.CraftMagicNumbers;

//TODO: finish this
public class ControllableEnderDragonEntity extends EntityEnderDragon implements ControllableEntityHandle {

    private final ControllableEntity controllableEntity;

    public ControllableEnderDragonEntity(World world, ControllableEntity controllableEntity) {
        super(world);
        this.controllableEntity = controllableEntity;
        if (this.controllableEntity instanceof ControllableBaseEntity) {
            ((ControllableBaseEntity) this.controllableEntity).clearNMSGoals(new PathfinderGoalSelector[]{this.goalSelector, this.targetSelector});
        }
    }

    @Override
    public ControllableEntity getControllableEntity() {
        return this.controllableEntity;
    }

    @Override
    public org.bukkit.Material getDefaultMaterialLoot() {
        return CraftMagicNumbers.getMaterial(this.getLoot());
    }

    @Override
    protected Item getLoot() {
        org.bukkit.Material lootMaterial = this.controllableEntity.getLoot();
        return this.controllableEntity == null ? super.getLoot() : lootMaterial == null ? super.getLoot() : CraftMagicNumbers.getItem(lootMaterial);
    }

    @Override
    protected String t() {
        return this.controllableEntity == null ? "mob.enderdragon.growl" : this.controllableEntity.getSound(EntitySound.IDLE);
    }

    @Override
    protected String aT() {
        return this.controllableEntity == null ? "mob.enderdragon.hit" : this.controllableEntity.getSound(EntitySound.HIT);
    }

    @Override
    public void makeSound(String s, float f, float f1) {
        if (s.equals("mob.enderdragon.wings")) {
            if (this.controllableEntity != null) {
                s = this.controllableEntity.getSound(EntitySound.WINGS);
            }
        } else if (s.equals("mob.enderdragon.end")) {
            if (this.controllableEntity != null) {
                s = this.controllableEntity.getSound(EntitySound.DEATH);
            }
        }
        super.makeSound(s, f, f1);
    }
}