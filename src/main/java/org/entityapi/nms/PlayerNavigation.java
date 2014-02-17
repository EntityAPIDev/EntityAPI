package org.entityapi.nms;

import net.minecraft.server.v1_7_R1.Navigation;
import org.entityapi.entity.ControllablePlayerEntity;

public class PlayerNavigation extends Navigation {

    public PlayerNavigation(ControllablePlayerEntity controllablePlayerEntity) {
        super(null, controllablePlayerEntity.world);
    }
}
