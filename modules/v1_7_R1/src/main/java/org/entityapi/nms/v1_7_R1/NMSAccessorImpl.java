/*
 * Copyright (C) EntityAPI Team
 *
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

package org.entityapi.nms.v1_7_R1;

import com.captainbern.reflection.accessor.FieldAccessor;
import com.captainbern.reflection.accessor.MethodAccessor;
import net.minecraft.server.v1_7_R1.*;
import org.bukkit.Sound;
import org.bukkit.craftbukkit.v1_7_R1.CraftSound;
import org.bukkit.craftbukkit.v1_7_R1.entity.CraftLivingEntity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.util.Vector;
import org.entityapi.api.NMSAccessor;
import org.entityapi.api.ProjectileType;
import org.entityapi.api.entity.ControllableEntity;
import org.entityapi.api.entity.ControllableEntityHandle;
import org.entityapi.api.entity.impl.ControllableEnderDragonBase;
import org.entityapi.nms.v1_7_R1.entity.selector.EntitySelectorContainer;
import org.entityapi.nms.v1_7_R1.entity.selector.EntitySelectorHorse;
import org.entityapi.nms.v1_7_R1.entity.selector.EntitySelectorLiving;
import org.entityapi.nms.v1_7_R1.entity.selector.EntitySelectorMonster;
import org.entityapi.nms.v1_7_R1.entity.selector.EntitySelectorNotUndead;

import java.util.List;

public class NMSAccessorImpl<T extends LivingEntity, S extends ControllableEntityHandle<T>> implements NMSAccessor<T, S> {

    private ControllableEntity controllableEntity;

    public NMSAccessorImpl(ControllableEntity controllableEntity) {
        this.controllableEntity = controllableEntity;
    }

    private EntityLiving handle() {
        return (EntityLiving) controllableEntity.getHandle();
    }

    @Override
    public IEntitySelector getMonsterSelector() {
        return IMonster.a;
    }

    @Override
    public IEntitySelector getSelector(SelectorType type) {
        switch (type) {
            case CONTAINER:
                return new EntitySelectorContainer();
            case HORSE:
                return new EntitySelectorHorse();
            case LIVING:
                return new EntitySelectorLiving();
            case MONSTER:
                return new EntitySelectorMonster();
            case NOT_UNDEAD:
                return new EntitySelectorNotUndead();
            default:
                return null;
        }
    }

    @Override
    public LivingEntity getBukkitEntity() {
        return (LivingEntity) handle().getBukkitEntity();
    }

    @Override
    public String getSoundName(Sound sound) {
        return CraftSound.getSound(sound);
    }

    @Override
    public float getHeight() {
        return handle().height;
    }

    @Override
    public float getWidth() {
        return handle().width;
    }

    @Override
    public double getSpeed() {
        if (this.controllableEntity.getHandle() == null) {
            return GenericAttributes.d.b();
        }
        return handle().getAttributeInstance(GenericAttributes.d).getValue();
    }

    @Override
    public void setSpeed(double speed) {
        handle().getAttributeInstance(GenericAttributes.d).setValue(speed);
    }

    @Override
    public void setPathfindingRange(double range) {
        handle().getAttributeInstance(GenericAttributes.b).setValue(range);
    }

    @Override
    public double getPathfindingRange() {
        AttributeInstance attribute = handle().getAttributeInstance(GenericAttributes.b);
        return attribute == null ? 16.0D : attribute.getValue();
    }

    @Override
    public boolean navigateTo(LivingEntity livingEntity, double speed) {
        if (livingEntity == null) {
            return false;
        }
        EntityLiving target = ((CraftLivingEntity) livingEntity).getHandle();
        if (target == handle()) {
            return true;
        }
        PathEntity path = handle().world.findPath(handle(), ((CraftLivingEntity) livingEntity).getHandle(), (float) this.getPathfindingRange(), true, false, false, true);
        return this.navigateTo(path, speed);
    }

    @Override
    public boolean navigateTo(Vector to, double speed) {
        if (to == null) {
            return false;
        }
        PathEntity path = handle().world.a(handle(), MathHelper.floor(to.getX()), MathHelper.f(to.getY()), MathHelper.floor(to.getZ()), (float) this.getPathfindingRange(), true, false, false, true);
        return this.navigateTo(path, speed);
    }

    @Override
    public boolean navigateTo(Object path) {
        return this.navigateTo(path, this.getSpeed());
    }

    @Override
    public boolean navigateTo(Object path, double speed) {
        if (!(path instanceof PathEntity)) {
            throw new IllegalArgumentException("Navigation can only directed by a PathEntity.");
        }
        if (!(handle() instanceof EntityInsentient)) {
            return false;
        }

        if (this.controllableEntity instanceof ControllableEnderDragonBase) {
            PathPoint point = ((PathEntity) path).c();
            ((ControllableEnderDragonBase) this.controllableEntity).setTargetPosition(new Vector(point.a, point.b, point.c));
            return true;
        }

        if (this.controllableEntity.isPathfindingSpeedOverriden()) {
            speed = this.getSpeed();
        }
        return ((EntityInsentient) handle()).getNavigation().a((PathEntity) path, speed);
    }

    @Override
    public LivingEntity getTarget() {
        if (handle() == null) {
            return null;
        }

        if (handle() instanceof EntityInsentient) {
            EntityLiving targetHandle = ((EntityInsentient) handle()).getGoalTarget();
            if (targetHandle != null && targetHandle.getBukkitEntity() instanceof LivingEntity) {
                return (LivingEntity) targetHandle.getBukkitEntity();
            }
        }

        return null;
    }

    @Override
    public void setTarget(LivingEntity target) {
        if (handle() == null) {
            return;
        }

        if (handle() instanceof EntityInsentient) {
            ((EntityInsentient) handle()).setGoalTarget(((CraftLivingEntity) target).getHandle());
        }
    }

    @Override
    public void setYaw(float value) {
        handle().yaw = value;
        handle().aO = value;
    }

    @Override
    public void setHeadYaw(float value) {
        handle().aP = value;
        handle().aQ = value;
        handle().aN = value;
    }

    @Override
    public void setPitch(float value) {
        handle().pitch = value;
    }

    @Override
    public float getFixedHeadYaw() {
        return handle().aP;
    }

    @Override
    public boolean isAlive() {
        return handle().isAlive();
    }

    @Override
    public void callBaseTick() {
        handle().C();
    }

    @Override
    public void shootProjectile(ProjectileType projectileType, LivingEntity target, float strength) {
        EntityLiving targetHandle = ((CraftLivingEntity) target).getHandle();
        if (projectileType == ProjectileType.ARROW) {
            EntityArrow entityarrow = new EntityArrow(handle().world, handle(), targetHandle, 1.6F, (float) (14 - handle().world.difficulty.a() * 4));
            if (handle().be() != null) {
                int i = EnchantmentManager.getEnchantmentLevel(Enchantment.ARROW_DAMAGE.id, handle().be());
                int j = EnchantmentManager.getEnchantmentLevel(Enchantment.ARROW_KNOCKBACK.id, handle().be());

                entityarrow.b((double) (strength * 2.0F) + handle().aI().nextGaussian() * 0.25D + (double) ((float) handle().world.difficulty.a() * 0.11F));
                if (i > 0) {
                    entityarrow.b(entityarrow.e() + (double) i * 0.5D + 0.5D);
                }

                if (j > 0) {
                    entityarrow.a(j);
                }

                if (handle() instanceof EntitySkeleton) {
                    if (EnchantmentManager.getEnchantmentLevel(Enchantment.ARROW_FIRE.id, handle().be()) > 0 || ((EntitySkeleton) handle()).getSkeletonType() == 1) {
                        entityarrow.setOnFire(100);
                    }
                }
            }
            handle().makeSound("random.bow", 1.0F, 1.0F / (handle().aI().nextFloat() * 0.4F + 0.8F));
            handle().world.addEntity(entityarrow);
        } else if (projectileType == ProjectileType.SNOWBALL) {
            EntitySnowball entitysnowball = new EntitySnowball(handle().world, handle());
            double vecX = targetHandle.locX - handle().locX;
            double vecY = targetHandle.locY + (double) targetHandle.getHeadHeight() - 1.100000023841858D - entitysnowball.locY;
            double vecZ = targetHandle.locZ - handle().locZ;
            float f1 = MathHelper.sqrt(vecX * vecX + vecZ * vecZ) * 0.2F;

            entitysnowball.shoot(vecX, vecY + (double) f1, vecZ, 1.6F, 12.0F);
            handle().makeSound("random.bow", 1.0F, 1.0F / (handle().aI().nextFloat() * 0.4F + 0.8F));
            handle().world.addEntity(entitysnowball);
        } else if (projectileType == ProjectileType.FIREBALL) {
            handle().world.a(null, 1008, (int) handle().locX, (int) handle().locY, (int) handle().locZ, 0);
            double vecX = targetHandle.locX - handle().locX;
            double vecY = targetHandle.boundingBox.b + (double) (targetHandle.length / 2.0F) - (handle().locY + (double) (handle().length / 2.0F));
            double vecZ = targetHandle.locZ - handle().locZ;
            EntityLargeFireball entitylargefireball = new EntityLargeFireball(handle().world, handle(), vecX, vecY, vecZ);
            double d8 = 4.0D;
            Vec3D vec3d = handle().j(1.0F);
            entitylargefireball.locX = handle().locX + vec3d.c * d8;
            entitylargefireball.locY = handle().locY + (double) (handle().length / 2.0F) + 0.5D;
            entitylargefireball.locZ = handle().locZ + vec3d.e * d8;
            handle().world.addEntity(entitylargefireball);
        } else if (projectileType == ProjectileType.SMALL_FIREBALL) {
            double vecX = targetHandle.locX - handle().locX;
            double vecY = targetHandle.boundingBox.b + (double) (targetHandle.length / 2.0F) - (handle().locY + (double) (handle().length / 2.0F));
            double vecZ = targetHandle.locZ - handle().locZ;
            float f1 = MathHelper.c(strength) * 0.5F;
            handle().world.a(null, 1009, (int) handle().locX, (int) handle().locY, (int) handle().locZ, 0);
            EntitySmallFireball entitysmallfireball = new EntitySmallFireball(handle().world, handle(), vecX + handle().aI().nextGaussian() * (double) f1, vecY, vecZ + handle().aI().nextGaussian() * (double) f1);
            entitysmallfireball.locY = handle().locY + (double) (handle().length / 2.0F) + 0.5D;
            handle().world.addEntity(entitysmallfireball);
        } else if (projectileType == ProjectileType.THROWN_POTION) {
            EntityPotion entitypotion = new EntityPotion(handle().world, handle(), 32732);

            entitypotion.pitch -= -20.0F;
            double d0 = targetHandle.locX + targetHandle.motX - handle().locX;
            double d1 = targetHandle.locY + (double) targetHandle.getHeadHeight() - 1.100000023841858D - handle().locY;
            double d2 = targetHandle.locZ + targetHandle.motZ - handle().locZ;
            float f1 = MathHelper.sqrt(d0 * d0 + d2 * d2);

            if (f1 >= 8.0F && !targetHandle.hasEffect(MobEffectList.SLOWER_MOVEMENT)) {
                entitypotion.setPotionValue(32698);
            } else if (targetHandle.getHealth() >= 8.0F && !targetHandle.hasEffect(MobEffectList.POISON)) {
                entitypotion.setPotionValue(32660);
            } else if (f1 <= 3.0F && !targetHandle.hasEffect(MobEffectList.WEAKNESS) && handle().aI().nextFloat() < 0.25F) {
                entitypotion.setPotionValue(32696);
            }

            entitypotion.shoot(d0, d1 + (double) (f1 * 0.2F), d2, 0.75F, 8.0F);
            handle().world.addEntity(entitypotion);
        } else if (projectileType == ProjectileType.DEFAULT) {
            if (handle() instanceof IRangedEntity) {
                ((IRangedEntity) handle()).a(targetHandle, strength);
            }
        }
    }
}