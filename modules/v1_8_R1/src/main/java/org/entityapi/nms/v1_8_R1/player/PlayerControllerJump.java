package org.entityapi.nms.v1_8_R1.player;

import net.minecraft.server.v1_8_R1.ControllerJump;
import net.minecraft.server.v1_8_R1.EntityInsentient;
import net.minecraft.server.v1_8_R1.EntityLiving;

public class PlayerControllerJump extends ControllerJump {

    private EntityLiving a;
    private boolean b;

    public PlayerControllerJump(EntityLiving entity) {
        super(new EntityInsentient(null) {
        });
        this.a = entity;
    }

    public void a() {
        this.b = true;
    }

    public void b() {
        this.a.f(this.b);
        this.b = false;
    }
}
