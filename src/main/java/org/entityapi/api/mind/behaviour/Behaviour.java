package org.entityapi.api.mind.behaviour;

import net.minecraft.server.v1_7_R1.EntityLiving;
import org.bukkit.Bukkit;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.entityapi.api.ControllableEntity;

public abstract class Behaviour {

    private ControllableEntity controllableEntity;

    public Behaviour(ControllableEntity controllableEntity) {
        this.controllableEntity = controllableEntity;
    }

    public ControllableEntity getControllableEntity() {
        return controllableEntity;
    }

    public EntityLiving getHandle() {
        return this.controllableEntity.getHandle();
    }

    public abstract BehaviourType getType();

    public abstract String getDefaultKey();

    public abstract boolean shouldStart(); //a

    public boolean shouldContinue() { //b
        return shouldStart();
    }

    public void start() { //c
    }

    public void finish() { //d
    }

    public boolean isContinuous() {
        return true;
    }

    public void tick() {} //e

    protected boolean callEvent(Event e) {
        if (e != null) {
            Bukkit.getServer().getPluginManager().callEvent(e);
            if (e instanceof Cancellable) {
                return !((Cancellable) e).isCancelled();
            }
        }
        return true;
    }
}
