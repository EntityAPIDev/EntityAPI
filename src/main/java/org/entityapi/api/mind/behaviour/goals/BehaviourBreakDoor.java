package org.entityapi.api.mind.behaviour.goals;

import net.minecraft.server.v1_7_R1.Block;
import net.minecraft.server.v1_7_R1.EnumDifficulty;
import net.minecraft.server.v1_7_R1.IBlockAccess;
import org.entityapi.api.ControllableEntity;

public class BehaviourBreakDoor extends BehaviourDoorInteract {

    private int breakTicks;
    private int lastBreakTicks = -1;

    public BehaviourBreakDoor(ControllableEntity controllableEntity) {
        super(controllableEntity);
    }

    public BehaviourBreakDoor(ControllableEntity controllableEntity, boolean searchForIronDoor) {
        super(controllableEntity, searchForIronDoor);
    }

    @Override
    public String getDefaultKey() {
        return "Break Door";
    }

    @Override
    public boolean shouldStart() {
        return !super.shouldStart() ? false : (!this.handle.world.getGameRules().getBoolean("mobGriefing") ? false : !this.door.f((IBlockAccess) this.handle.world, this.pointX, this.pointY, this.pointZ));
    }

    @Override
    public boolean shouldContinue() {
        double dist = this.handle.e((double) this.pointX, (double) this.pointY, (double) this.pointZ);

        return this.breakTicks <= 240 && !this.door.f((IBlockAccess) this.handle.world, this.pointX, this.pointY, this.pointZ) && dist < 4.0D;
    }

    @Override
    public void start() {
        super.start();
        this.breakTicks = 0;
    }

    @Override
    public void finish() {
        super.finish();
        this.handle.world.d(this.handle.getId(), this.pointX, this.pointY, this.pointZ, -1);
    }

    @Override
    public void tick() {
        super.tick();
        if (this.handle.aI().nextInt(20) == 0) {
            this.handle.world.triggerEffect(1010, this.pointX, this.pointY, this.pointZ, 0);
        }

        ++this.breakTicks;
        int i = (int) ((float) this.breakTicks / 240.0F * 10.0F);

        if (i != this.lastBreakTicks) {
            this.handle.world.d(this.handle.getId(), this.pointX, this.pointY, this.pointZ, i);
            this.lastBreakTicks = i;
        }

        if (this.breakTicks == 240 && this.handle.world.difficulty == EnumDifficulty.HARD) {
            // CraftBukkit start
            if (org.bukkit.craftbukkit.v1_7_R1.event.CraftEventFactory.callEntityBreakDoorEvent(this.handle, this.pointX, this.pointY, this.pointZ).isCancelled()) {
                this.start();
                return;
            }
            // CraftBukkit end

            this.handle.world.setAir(this.pointX, this.pointY, this.pointZ);
            this.handle.world.triggerEffect(1012, this.pointX, this.pointY, this.pointZ, 0);
            this.handle.world.triggerEffect(2001, this.pointX, this.pointY, this.pointZ, Block.b(this.door));
        }
    }
}