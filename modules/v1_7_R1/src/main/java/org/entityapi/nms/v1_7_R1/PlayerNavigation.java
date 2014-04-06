package org.entityapi.nms.v1_7_R1;

import net.minecraft.server.v1_7_R1.Navigation;
import org.entityapi.nms.v1_7_R1.entity.ControllablePlayerEntity;

public class PlayerNavigation extends Navigation {

    public PlayerNavigation(ControllablePlayerEntity controllablePlayerEntity) {
        super(null, controllablePlayerEntity.world);
    }
}
