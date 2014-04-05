package org.entityapi.nms.v1_7_R1.entity.mind.behaviour.goals;

import net.minecraft.server.v1_7_R1.Block;
import net.minecraft.server.v1_7_R1.Blocks;
import net.minecraft.server.v1_7_R1.EntityInsentient;
import net.minecraft.server.v1_7_R1.MathHelper;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_7_R1.event.CraftEventFactory;
import org.entityapi.api.ControllableEntity;
import org.entityapi.api.mind.BehaviourType;
import org.entityapi.nms.v1_7_R1.NMSEntityUtil;
import org.entityapi.nms.v1_7_R1.entity.mind.behaviour.BehaviourBase;

public class BehaviourEatGrass extends BehaviourBase {

    private int ticks;

    public BehaviourEatGrass(ControllableEntity controllableEntity) {
        super(controllableEntity);
    }

    @Override
    public BehaviourType getType() {
        return BehaviourType.SEVEN;
    }

    @Override
    public String getDefaultKey() {
        return "Eat Grass";
    }

    @Override
    public boolean shouldStart() {
        if (this.getHandle().aI().nextInt(this.getHandle().isBaby() ? 50 : 1000) != 0) {
            return false;
        } else {
            int i = MathHelper.floor(this.getHandle().locX);
            int j = MathHelper.floor(this.getHandle().locY);
            int k = MathHelper.floor(this.getHandle().locZ);

            return this.getHandle().world.getType(i, j, k) == Blocks.LONG_GRASS && this.getHandle().world.getData(i, j, k) == 1 ? true : this.getHandle().world.getType(i, j - 1, k) == Blocks.GRASS;
        }
    }

    @Override
    public boolean shouldContinue() {
        return this.ticks > 0;
    }

    @Override
    public void start() {
        this.ticks = 40;
        this.getHandle().world.broadcastEntityEffect(this.getHandle(), (byte) 10);
        NMSEntityUtil.getNavigation(this.getHandle()).h();
    }

    @Override
    public void finish() {
        this.ticks = 0;
    }

    @Override
    public void tick() {
        this.ticks = Math.max(0, this.ticks - 1);
        if (this.ticks == 4) {
            int i = MathHelper.floor(this.getHandle().locX);
            int j = MathHelper.floor(this.getHandle().locY);
            int k = MathHelper.floor(this.getHandle().locZ);

            if (this.getHandle().world.getType(i, j, k) == Blocks.LONG_GRASS) {
                // CraftBukkit
                if (!CraftEventFactory.callEntityChangeBlockEvent(this.getHandle(), this.getHandle().world.getWorld().getBlockAt(i, j, k), Material.AIR, !this.getHandle().world.getGameRules().getBoolean("mobGriefing")).isCancelled()) {
                    this.getHandle().world.setAir(i, j, k, false);
                }

                if (this.getHandle() instanceof EntityInsentient) {
                    ((EntityInsentient) this.getHandle()).p();
                }
            } else if (this.getHandle().world.getType(i, j - 1, k) == Blocks.GRASS) {
                // CraftBukkit
                if (!CraftEventFactory.callEntityChangeBlockEvent(this.getHandle(), this.getHandle().world.getWorld().getBlockAt(i, j - 1, k), Material.DIRT, !this.getHandle().world.getGameRules().getBoolean("mobGriefing")).isCancelled()) {
                    this.getHandle().world.triggerEffect(2001, i, j - 1, k, Block.b(Blocks.GRASS));
                    this.getHandle().world.setTypeAndData(i, j - 1, k, Blocks.DIRT, 0, 2);
                }

                if (this.getHandle() instanceof EntityInsentient) {
                    ((EntityInsentient) this.getHandle()).p();
                }
            }
        }
    }
}