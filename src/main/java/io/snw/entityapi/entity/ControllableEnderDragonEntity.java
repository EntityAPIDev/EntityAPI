package io.snw.entityapi.entity;

import io.snw.entityapi.api.ControllableEntity;
import io.snw.entityapi.api.ControllableEntityHandle;
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
}