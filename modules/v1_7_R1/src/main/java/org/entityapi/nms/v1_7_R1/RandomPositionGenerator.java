/*
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

import net.minecraft.server.v1_7_R1.EntityCreature;
import net.minecraft.server.v1_7_R1.EntityLiving;
import net.minecraft.server.v1_7_R1.MathHelper;
import net.minecraft.server.v1_7_R1.Vec3D;

import java.util.Random;

public class RandomPositionGenerator {

    private static Vec3D a = Vec3D.a(0.0D, 0.0D, 0.0D);

    public static Vec3D a(EntityLiving entitycreature, int i, int j) {
        return c(entitycreature, i, j, null);
    }

    public static Vec3D a(EntityLiving entity, int i, int j, Vec3D vec3d) {
        a.c = vec3d.c - entity.locX;
        a.d = vec3d.d - entity.locY;
        a.e = vec3d.e - entity.locZ;
        return c(entity, i, j, a);
    }

    public static Vec3D b(EntityLiving entity, int i, int j, Vec3D vec3d) {
        a.c = entity.locX - vec3d.c;
        a.d = entity.locY - vec3d.d;
        a.e = entity.locZ - vec3d.e;
        return c(entity, i, j, a);
    }

    private static Vec3D c(EntityLiving entity, int i, int j, Vec3D vec3d) {
        Random random = entity.aI();
        boolean flag = false;
        int k = 0;
        int l = 0;
        int i1 = 0;
        float f = -99999.0F;
        boolean flag1;

        if (NMSEntityUtil.hasGuardedArea(entity)) {
            double d0 = (double) (NMSEntityUtil.getChunkCoordinates(entity).e(MathHelper.floor(entity.locX), MathHelper.floor(entity.locY), MathHelper.floor(entity.locZ)) + 4.0F);
            double d1 = (double) (NMSEntityUtil.getHomeAreaRange(entity) + (float) i);

            flag1 = d0 < d1 * d1;
        } else {
            flag1 = false;
        }

        for (int j1 = 0; j1 < 10; ++j1) {
            int k1 = random.nextInt(2 * i) - i;
            int l1 = random.nextInt(2 * j) - j;
            int i2 = random.nextInt(2 * i) - i;

            if (vec3d == null || (double) k1 * vec3d.c + (double) i2 * vec3d.e >= 0.0D) {
                k1 += MathHelper.floor(entity.locX);
                l1 += MathHelper.floor(entity.locY);
                i2 += MathHelper.floor(entity.locZ);
                if (!flag1 || NMSEntityUtil.isInHomeArea(entity, k1, l1, i2)) {
                    float f1;
                    if (entity instanceof EntityCreature) {
                        f1 = ((EntityCreature) entity).a(k1, l1, i2);
                    } else {
                        f1 = 0.5F - entity.world.n(k1, l1, i2);
                    }

                    if (f1 > f) {
                        f = f1;
                        k = k1;
                        l = l1;
                        i1 = i2;
                        flag = true;
                    }
                }
            }
        }

        if (flag) {
            return entity.world.getVec3DPool().create((double) k, (double) l, (double) i1);
        } else {
            return null;
        }
    }
}