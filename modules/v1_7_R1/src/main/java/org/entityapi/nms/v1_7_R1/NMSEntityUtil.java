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
import org.entityapi.nms.v1_7_R1.entity.ControllablePlayerEntity;

public class NMSEntityUtil {

    public static Navigation getNavigation(LivingEntity livingEntity) {
        if (livingEntity instanceof CraftLivingEntity) {
            return getNavigation(((CraftLivingEntity) livingEntity).getHandle());
        }
        return null;
    }

    public static Navigation getNavigation(EntityLiving entityLiving) {
        if (entityLiving instanceof EntityInsentient) {
            return ((EntityInsentient) entityLiving).getNavigation();
        } else if (entityLiving instanceof ControllablePlayerEntity) {
            return ((ControllablePlayerEntity) entityLiving).getNavigation();
        }
        return null;
    }

    public static EntitySenses getEntitySenses(LivingEntity livingEntity) {
        if (livingEntity instanceof CraftLivingEntity) {
            return getEntitySenses(((CraftLivingEntity) livingEntity).getHandle());
        }
        return null;
    }

    public static EntitySenses getEntitySenses(EntityLiving entityLiving) {
        if (entityLiving instanceof EntityInsentient) {
            return ((EntityInsentient) entityLiving).getEntitySenses();
        }
        return null;
    }

    public static ControllerJump getControllerJump(LivingEntity livingEntity) {
        if (livingEntity instanceof CraftLivingEntity) {
            return getControllerJump(((CraftLivingEntity) livingEntity).getHandle());
        }
        return null;
    }

    public static ControllerJump getControllerJump(EntityLiving entityLiving) {
        if (entityLiving instanceof EntityInsentient) {
            return ((EntityInsentient) entityLiving).getControllerJump();
        }
        return null;
    }

    public static ControllerMove getControllerMove(LivingEntity livingEntity) {
        if (livingEntity instanceof CraftLivingEntity) {
            return getControllerMove(((CraftLivingEntity) livingEntity).getHandle());
        }
        return null;
    }

    public static ControllerMove getControllerMove(EntityLiving entityLiving) {
        if (entityLiving instanceof EntityInsentient) {
            return ((EntityInsentient) entityLiving).getControllerMove();
        }
        return null;
    }

    public static ControllerLook getControllerLook(LivingEntity livingEntity) {
        if (livingEntity instanceof CraftLivingEntity) {
            return getControllerLook(((CraftLivingEntity) livingEntity).getHandle());
        }
        return null;
    }

    public static ControllerLook getControllerLook(EntityLiving entityLiving) {
        if (entityLiving instanceof EntityInsentient) {
            return ((EntityInsentient) entityLiving).getControllerLook();
        }
        return null;
    }

    public static boolean hasGuardedArea(EntityLiving entityLiving) {
        if (entityLiving instanceof EntityCreature) {
            return ((EntityCreature) entityLiving).bW();
        }
        return false;
    }

    public static boolean isInHomeArea(EntityLiving entityLiving) {
        if (entityLiving instanceof EntityCreature) {
            return ((EntityCreature) entityLiving).bS();
        }
        return false;
    }

    public static boolean isInHomeArea(EntityLiving entityLiving, int x, int y, int z) {
        if (entityLiving instanceof EntityCreature) {
            return ((EntityCreature) entityLiving).b(x, y, z);
        }
        return false;
    }

    public static float getHomeAreaRange(EntityLiving entityLiving) {
        if (entityLiving instanceof EntityCreature) {
            return ((EntityCreature) entityLiving).bU();
        }
        return 1.0F;
    }

    public static ChunkCoordinates getChunkCoordinates(EntityLiving entityLiving) {
        if (entityLiving instanceof EntityCreature) {
            return ((EntityCreature) entityLiving).bT();
        } else if (entityLiving instanceof EntityPlayer) {
            return ((EntityPlayer) entityLiving).getChunkCoordinates();
        }
        return new ChunkCoordinates(MathHelper.floor(entityLiving.locX), MathHelper.floor(entityLiving.locY), MathHelper.floor(entityLiving.locZ));
    }

    public static int getMaxHeadRotation(EntityLiving entityLiving) {
        if (entityLiving instanceof EntityInsentient) {
            return ((EntityInsentient) entityLiving).x();
        }
        return 40;
    }

    public static boolean canBeSteered(LivingEntity livingEntity) {
        if (livingEntity instanceof CraftLivingEntity) {
            return canBeSteered(((CraftLivingEntity) livingEntity).getHandle());
        }
        return false;
    }

    public static boolean canBeSteered(EntityLiving entityLiving) {
        if (entityLiving instanceof EntityInsentient) {
            return ((EntityInsentient) entityLiving).bC();
        }
        return false;
    }
}
