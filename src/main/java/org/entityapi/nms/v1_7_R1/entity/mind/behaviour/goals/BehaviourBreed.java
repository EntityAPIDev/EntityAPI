package org.entityapi.nms.v1_7_R1.entity.mind.behaviour.goals;

import net.minecraft.server.v1_7_R1.*;
import org.bukkit.craftbukkit.v1_7_R1.entity.CraftPlayer;
import org.bukkit.entity.Animals;
import org.entityapi.EntityAPICore;
import org.entityapi.api.ControllableEntity;
import org.entityapi.api.events.ControllableEntityBreedEvent;
import org.entityapi.api.events.ControllableEntityPreBreedEvent;
import org.entityapi.api.mind.BehaviourType;
import org.entityapi.nms.v1_7_R1.BasicEntityUtil;
import org.entityapi.nms.v1_7_R1.NMSEntityUtil;
import org.entityapi.nms.v1_7_R1.entity.mind.behaviour.BehaviourBase;

import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class BehaviourBreed extends BehaviourBase {

    private EntityAnimal handle;
    private EntityAnimal mate;
    int matingTicks;

    public BehaviourBreed(ControllableEntity<Animals> controllableEntity) {
        super(controllableEntity);
        this.handle = (EntityAnimal) ((BasicEntityUtil) EntityAPICore.getBasicEntityUtil()).getHandle(this.getControllableEntity());
    }

    @Override
    public BehaviourType getType() {
        return BehaviourType.THREE;
    }

    @Override
    public String getDefaultKey() {
        return "Breed";
    }

    @Override
    public boolean shouldStart() {
        if (!this.handle.cc()) {
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
        NMSEntityUtil.getControllerLook(this.getControllableEntity().getBukkitEntity()).a(this.mate, 10.0F, (float) this.handle.x());
        NMSEntityUtil.getNavigation(this.getControllableEntity().getBukkitEntity()).a(this.mate);
        ++this.matingTicks;
        if (this.matingTicks >= 60 && this.handle.e(this.mate) < 9.0D) {
            this.breed();
        }
    }

    private EntityAnimal getPossibleMate() {
        float f = 8.0F;
        List list = this.handle.world.a(this.handle.getClass(), this.handle.boundingBox.grow((double) f, (double) f, (double) f));
        double range = Double.MAX_VALUE;
        EntityAnimal nearestAnimal = null;
        Iterator iterator = list.iterator();

        while (iterator.hasNext()) {
            EntityAnimal animal = (EntityAnimal) iterator.next();

            if (this.handle.mate(animal) && this.handle.e(animal) < range) {
                nearestAnimal = animal;
                range = this.handle.e(animal);
            }
        }

        return nearestAnimal;
    }

    private void breed() {
        ControllableEntityPreBreedEvent preBreedEvent = new ControllableEntityPreBreedEvent(this.getControllableEntity(), (Animals) this.mate.getBukkitEntity(), (CraftPlayer) this.handle.cb().getBukkitEntity());
        EntityAPICore.getCore().getServer().getPluginManager().callEvent(preBreedEvent);
        if (!preBreedEvent.isCancelled()) {
            EntityAgeable child = this.handle.createChild(this.mate);

            ControllableEntityBreedEvent breedEvent = new ControllableEntityBreedEvent(this.getControllableEntity(), (Animals) this.mate.getBukkitEntity(), (Animals) child.getBukkitEntity(), (CraftPlayer) this.handle.cb().getBukkitEntity());
            EntityAPICore.getCore().getServer().getPluginManager().callEvent(breedEvent);

            if (child != null) {
                // CraftBukkit start - set persistence for tame animals
                if (child instanceof EntityTameableAnimal && ((EntityTameableAnimal) child).isTamed()) {
                    child.persistent = true;
                }
                // CraftBukkit end

                EntityHuman human = this.handle.cb();

                if (human == null && this.mate.cb() != null) {
                    human = this.mate.cb();
                }

                if (human != null) {
                    human.a(StatisticList.x);
                    if (this.handle instanceof EntityCow) {
                        human.a((Statistic) AchievementList.H);
                    }
                }

                this.handle.setAge(6000);
                this.mate.setAge(6000);
                this.handle.cd();
                this.mate.cd();
                child.setAge(-24000);
                child.setPositionRotation(this.handle.locX, this.handle.locY, this.handle.locZ, 0.0F, 0.0F);
                this.handle.world.addEntity(child, org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason.BREEDING); // CraftBukkit - added SpawnReason
                Random random = this.handle.aI();

                for (int i = 0; i < 7; ++i) {
                    double d0 = random.nextGaussian() * 0.02D;
                    double d1 = random.nextGaussian() * 0.02D;
                    double d2 = random.nextGaussian() * 0.02D;

                    this.handle.world.addParticle("heart", this.handle.locX + (double) (random.nextFloat() * this.handle.width * 2.0F) - (double) this.handle.width, this.handle.locY + 0.5D + (double) (random.nextFloat() * this.handle.length), this.handle.locZ + (double) (random.nextFloat() * this.handle.width * 2.0F) - (double) this.handle.width, d0, d1, d2);
                }

                if (this.handle.world.getGameRules().getBoolean("doMobLoot")) {
                    this.handle.world.addEntity(new EntityExperienceOrb(this.handle.world, this.handle.locX, this.handle.locY, this.handle.locZ, random.nextInt(7) + 1));
                }
            }
        }
    }
}