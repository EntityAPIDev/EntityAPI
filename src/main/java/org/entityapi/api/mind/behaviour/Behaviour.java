package org.entityapi.api.mind.behaviour;

import org.bukkit.Bukkit;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;

public abstract class Behaviour {

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

    public abstract void tick(); //e

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
