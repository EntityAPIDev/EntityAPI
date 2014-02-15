package org.entityapi.nms;

import org.entityapi.entity.ControllablePlayerEntity;
import net.minecraft.server.v1_7_R1.Navigation;

public class PlayerNavigation extends Navigation {

    public PlayerNavigation(ControllablePlayerEntity controllablePlayerEntity) {
        super(null, controllablePlayerEntity.world);
    }
}
