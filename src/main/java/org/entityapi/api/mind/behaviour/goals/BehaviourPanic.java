package org.entityapi.api.mind.behaviour.goals;

import net.minecraft.server.v1_7_R1.EntityLiving;
import net.minecraft.server.v1_7_R1.Vec3D;
import org.entityapi.api.ControllableEntity;
import org.entityapi.api.mind.behaviour.Behaviour;
import org.entityapi.api.mind.behaviour.BehaviourType;
import org.entityapi.nms.NMSEntityUtil;
import org.entityapi.nms.RandomPositionGenerator;

public class BehaviourPanic extends Behaviour {

    private double panicX;
    private double panicY;
    private double panicZ;

    public BehaviourPanic(ControllableEntity controllableEntity) {
        super(controllableEntity);
    }

    @Override
    public BehaviourType getType() {
        return BehaviourType.ONE;
    }

    @Override
    public String getDefaultKey() {
        return "Panic";
    }

    @Override
    public boolean shouldStart() {
        if (this.getHandle().getLastDamager() == null && !this.getHandle().isBurning()) {
            return false;
        } else {
            Vec3D vec3d = RandomPositionGenerator.a(this.getHandle(), 5, 4);

            if (vec3d == null) {
                return false;
            } else {
                this.panicX = vec3d.c;
                this.panicY = vec3d.d;
                this.panicZ = vec3d.e;
                return true;
            }
        }
    }

    @Override
    public boolean shouldContinue() {
        // CraftBukkit start - introduce a temporary timeout hack until this is fixed properly
        if ((this.getHandle().ticksLived - this.getHandle().aK()) > 100) {
            this.getHandle().b((EntityLiving) null);
            return false;
        }
        // CraftBukkit end
        return !NMSEntityUtil.getNavigation(this.getHandle()).g();
    }

    @Override
    public void start() {
        NMSEntityUtil.getNavigation(this.getHandle()).a(this.panicX, this.panicY, this.panicZ, this.getControllableEntity().getSpeed());
    }

    @Override
    public void tick() {

    }
}