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

import net.minecraft.server.v1_7_R1.*;
import org.bukkit.craftbukkit.v1_7_R1.entity.CraftLivingEntity;
import org.bukkit.entity.LivingEntity;
import org.entityapi.api.IBasicEntityUtil;
import org.entityapi.api.ProjectileType;
import org.entityapi.api.entity.ControllableEntity;
import org.entityapi.api.plugin.EntityAPI;

public class BasicEntityUtil implements IBasicEntityUtil {

    public static BasicEntityUtil getInstance() {
        return (BasicEntityUtil) EntityAPI.getBasicEntityUtil();
    }

    @Override
    public EntityLiving getHandle(ControllableEntity controllableEntity) {
        return ((CraftLivingEntity) controllableEntity.getBukkitEntity()).getHandle();
    }

    @Override
    public boolean isAlive(ControllableEntity controllableEntity) {
        return getHandle(controllableEntity).isAlive();
    }

    @Override
    public void callBaseTick(ControllableEntity controllableEntity) {
        getHandle(controllableEntity).C();
    }

    @Override
    public void shootProjectile(ProjectileType projectileType, ControllableEntity controllableEntity, LivingEntity target, float strength) {
        EntityLiving handle = getHandle(controllableEntity);
        EntityLiving entityliving = ((CraftLivingEntity) target).getHandle();
        if (projectileType == ProjectileType.ARROW) {
            EntityArrow entityarrow = new EntityArrow(handle.world, handle, entityliving, 1.6F, (float) (14 - handle.world.difficulty.a() * 4));
            if (handle.be() != null) {
                int i = EnchantmentManager.getEnchantmentLevel(Enchantment.ARROW_DAMAGE.id, handle.be());
                int j = EnchantmentManager.getEnchantmentLevel(Enchantment.ARROW_KNOCKBACK.id, handle.be());

                entityarrow.b((double) (strength * 2.0F) + handle.aI().nextGaussian() * 0.25D + (double) ((float) handle.world.difficulty.a() * 0.11F));
                if (i > 0) {
                    entityarrow.b(entityarrow.e() + (double) i * 0.5D + 0.5D);
                }

                if (j > 0) {
                    entityarrow.a(j);
                }

                if (handle instanceof EntitySkeleton) {
                    if (EnchantmentManager.getEnchantmentLevel(Enchantment.ARROW_FIRE.id, handle.be()) > 0 || ((EntitySkeleton) handle).getSkeletonType() == 1) {
                        entityarrow.setOnFire(100);
                    }
                }
            }
            handle.makeSound("random.bow", 1.0F, 1.0F / (handle.aI().nextFloat() * 0.4F + 0.8F));
            handle.world.addEntity(entityarrow);
        } else if (projectileType == ProjectileType.SNOWBALL) {
            EntitySnowball entitysnowball = new EntitySnowball(handle.world, handle);
            double vecX = entityliving.locX - handle.locX;
            double vecY = entityliving.locY + (double) entityliving.getHeadHeight() - 1.100000023841858D - entitysnowball.locY;
            double vecZ = entityliving.locZ - handle.locZ;
            float f1 = MathHelper.sqrt(vecX * vecX + vecZ * vecZ) * 0.2F;

            entitysnowball.shoot(vecX, vecY + (double) f1, vecZ, 1.6F, 12.0F);
            handle.makeSound("random.bow", 1.0F, 1.0F / (handle.aI().nextFloat() * 0.4F + 0.8F));
            handle.world.addEntity(entitysnowball);
        } else if (projectileType == ProjectileType.FIREBALL) {
            handle.world.a(null, 1008, (int) handle.locX, (int) handle.locY, (int) handle.locZ, 0);
            double vecX = entityliving.locX - handle.locX;
            double vecY = entityliving.boundingBox.b + (double) (entityliving.length / 2.0F) - (handle.locY + (double) (handle.length / 2.0F));
            double vecZ = entityliving.locZ - handle.locZ;
            EntityLargeFireball entitylargefireball = new EntityLargeFireball(handle.world, handle, vecX, vecY, vecZ);
            double d8 = 4.0D;
            Vec3D vec3d = handle.j(1.0F);
            entitylargefireball.locX = handle.locX + vec3d.c * d8;
            entitylargefireball.locY = handle.locY + (double) (handle.length / 2.0F) + 0.5D;
            entitylargefireball.locZ = handle.locZ + vec3d.e * d8;
            handle.world.addEntity(entitylargefireball);
        } else if (projectileType == ProjectileType.SMALL_FIREBALL) {
            double vecX = entityliving.locX - handle.locX;
            double vecY = entityliving.boundingBox.b + (double) (entityliving.length / 2.0F) - (handle.locY + (double) (handle.length / 2.0F));
            double vecZ = entityliving.locZ - handle.locZ;
            float f1 = MathHelper.c(strength) * 0.5F;
            handle.world.a(null, 1009, (int) handle.locX, (int) handle.locY, (int) handle.locZ, 0);
            EntitySmallFireball entitysmallfireball = new EntitySmallFireball(handle.world, handle, vecX + handle.aI().nextGaussian() * (double) f1, vecY, vecZ + handle.aI().nextGaussian() * (double) f1);
            entitysmallfireball.locY = handle.locY + (double) (handle.length / 2.0F) + 0.5D;
            handle.world.addEntity(entitysmallfireball);
        } else if (projectileType == ProjectileType.THROWN_POTION) {
            EntityPotion entitypotion = new EntityPotion(handle.world, handle, 32732);

            entitypotion.pitch -= -20.0F;
            double d0 = entityliving.locX + entityliving.motX - handle.locX;
            double d1 = entityliving.locY + (double) entityliving.getHeadHeight() - 1.100000023841858D - handle.locY;
            double d2 = entityliving.locZ + entityliving.motZ - handle.locZ;
            float f1 = MathHelper.sqrt(d0 * d0 + d2 * d2);

            if (f1 >= 8.0F && !entityliving.hasEffect(MobEffectList.SLOWER_MOVEMENT)) {
                entitypotion.setPotionValue(32698);
            } else if (entityliving.getHealth() >= 8.0F && !entityliving.hasEffect(MobEffectList.POISON)) {
                entitypotion.setPotionValue(32660);
            } else if (f1 <= 3.0F && !entityliving.hasEffect(MobEffectList.WEAKNESS) && handle.aI().nextFloat() < 0.25F) {
                entitypotion.setPotionValue(32696);
            }

            entitypotion.shoot(d0, d1 + (double) (f1 * 0.2F), d2, 0.75F, 8.0F);
            handle.world.addEntity(entitypotion);
        } else if (projectileType == ProjectileType.DEFAULT) {
            if (handle instanceof IRangedEntity) {
                ((IRangedEntity) handle).a(entityliving, strength);
            }
        }
    }
}