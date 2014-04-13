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

package org.entityapi.nms.v1_7_R1.entity.mind.behaviour.goals;

import net.minecraft.server.v1_7_R1.*;
import org.bukkit.craftbukkit.v1_7_R1.entity.CraftPlayer;
import org.bukkit.entity.Animals;
import org.bukkit.entity.LivingEntity;
import org.entityapi.api.entity.ControllableEntity;
import org.entityapi.api.events.ControllableEntityBreedEvent;
import org.entityapi.api.events.ControllableEntityPreBreedEvent;
import org.entityapi.api.entity.mind.behaviour.BehaviourType;
import org.entityapi.api.plugin.EntityAPI;
import org.entityapi.nms.v1_7_R1.BasicEntityUtil;
import org.entityapi.nms.v1_7_R1.NMSEntityUtil;
import org.entityapi.nms.v1_7_R1.entity.mind.behaviour.BehaviourBase;

import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class BehaviourBreed extends BehaviourBase {

    private EntityAnimal mate;
    private int matingTicks;
    private double navigationSpeed;

    public BehaviourBreed(ControllableEntity<? extends Animals> controllableEntity) {
        this(controllableEntity, -1);
    }

    public BehaviourBreed(ControllableEntity<? extends Animals> controllableEntity, double navigationSpeed) {
        super(controllableEntity);
        this.navigationSpeed = navigationSpeed;
    }

    @Override
    public ControllableEntity<? extends Animals> getControllableEntity() {
        return super.getControllableEntity();
    }

    @Override
    public EntityAnimal getHandle() {
        return (EntityAnimal) BasicEntityUtil.getInstance().getHandle(this.getControllableEntity());
    }

    @Override
    public BehaviourType getType() {
        return BehaviourType.ACTION;
    }

    @Override
    public String getDefaultKey() {
        return "Breed";
    }

    @Override
    public boolean shouldStart() {
        if (!this.getHandle().cc()) {
            return false;
        } else {
            this.mate = this.getPossibleMate();
            return this.mate != null;
        }
    }

    @Override
    public boolean shouldContinue() {
        return this.mate.isAlive() && this.mate.cc() && this.matingTicks < 60;
    }

    @Override
    public void finish() {
        this.mate = null;
        this.matingTicks = 0;
    }

    @Override
    public void tick() {
        NMSEntityUtil.getControllerLook(this.getControllableEntity().getBukkitEntity()).a(this.mate, 10.0F, (float) this.getHandle().x());
        this.getControllableEntity().navigateTo((LivingEntity) this.mate.getBukkitEntity(), this.navigationSpeed > 0 ? this.navigationSpeed : this.getControllableEntity().getSpeed());
        ++this.matingTicks;
        if (this.matingTicks >= 60 && this.getHandle().e(this.mate) < 9.0D) {
            this.breed();
        }
    }

    private EntityAnimal getPossibleMate() {
        float f = 8.0F;
        List list = this.getHandle().world.a(this.getHandle().getClass(), this.getHandle().boundingBox.grow((double) f, (double) f, (double) f));
        double range = Double.MAX_VALUE;
        EntityAnimal nearestAnimal = null;
        Iterator iterator = list.iterator();

        while (iterator.hasNext()) {
            EntityAnimal animal = (EntityAnimal) iterator.next();

            if (this.getHandle().mate(animal) && this.getHandle().e(animal) < range) {
                nearestAnimal = animal;
                range = this.getHandle().e(animal);
            }
        }

        return nearestAnimal;
    }

    private void breed() {
        ControllableEntityPreBreedEvent preBreedEvent = new ControllableEntityPreBreedEvent(this.getControllableEntity(), (Animals) this.mate.getBukkitEntity(), (CraftPlayer) this.getHandle().cb().getBukkitEntity());
        EntityAPI.getCore().getServer().getPluginManager().callEvent(preBreedEvent);
        if (!preBreedEvent.isCancelled()) {
            EntityAgeable child = this.getHandle().createChild(this.mate);

            ControllableEntityBreedEvent breedEvent = new ControllableEntityBreedEvent(this.getControllableEntity(), (Animals) this.mate.getBukkitEntity(), (Animals) child.getBukkitEntity(), (CraftPlayer) this.getHandle().cb().getBukkitEntity());
            EntityAPI.getCore().getServer().getPluginManager().callEvent(breedEvent);

            if (child != null) {
                // CraftBukkit start - set persistence for tame animals
                if (child instanceof EntityTameableAnimal && ((EntityTameableAnimal) child).isTamed()) {
                    child.persistent = true;
                }
                // CraftBukkit end

                EntityHuman human = this.getHandle().cb();

                if (human == null && this.mate.cb() != null) {
                    human = this.mate.cb();
                }

                if (human != null) {
                    human.a(StatisticList.x);
                    if (this.getHandle() instanceof EntityCow) {
                        human.a((Statistic) AchievementList.H);
                    }
                }

                this.getHandle().setAge(6000);
                this.mate.setAge(6000);
                this.getHandle().cd();
                this.mate.cd();
                child.setAge(-24000);
                child.setPositionRotation(this.getHandle().locX, this.getHandle().locY, this.getHandle().locZ, 0.0F, 0.0F);
                this.getHandle().world.addEntity(child, org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason.BREEDING); // CraftBukkit - added SpawnReason
                Random random = this.getHandle().aI();

                for (int i = 0; i < 7; ++i) {
                    double d0 = random.nextGaussian() * 0.02D;
                    double d1 = random.nextGaussian() * 0.02D;
                    double d2 = random.nextGaussian() * 0.02D;

                    this.getHandle().world.addParticle("heart", this.getHandle().locX + (double) (random.nextFloat() * this.getHandle().width * 2.0F) - (double) this.getHandle().width, this.getHandle().locY + 0.5D + (double) (random.nextFloat() * this.getHandle().length), this.getHandle().locZ + (double) (random.nextFloat() * this.getHandle().width * 2.0F) - (double) this.getHandle().width, d0, d1, d2);
                }

                if (this.getHandle().world.getGameRules().getBoolean("doMobLoot")) {
                    this.getHandle().world.addEntity(new EntityExperienceOrb(this.getHandle().world, this.getHandle().locX, this.getHandle().locY, this.getHandle().locZ, random.nextInt(7) + 1));
                }
            }
        }
    }
}