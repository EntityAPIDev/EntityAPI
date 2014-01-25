package io.snw.entityapi.api;

import io.snw.entityapi.api.ControllableEntity;
import org.bukkit.Material;

/**
 * Represents the NMS entity
 */

public interface ControllableEntityHandle {

    public ControllableEntity getControllableEntity();

    public Material getDefaultMaterialLoot();
}