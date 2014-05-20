/*
 * Copyright (C) EntityAPI Team
 *
 * This file is part of EntityAPI.
 *
 * EntityAPI is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * EntityAPI is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with EntityAPI.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.entityapi.api.pathfinding.astar;

import com.google.common.collect.Lists;
import org.bukkit.Material;
import org.bukkit.block.Block;

import java.util.Arrays;
import java.util.List;

public class MaterialChecker {

    private static List<Material> transparents = Lists.newArrayList();
    private static List<Integer> transparentsID = Lists.newArrayList();
    private static List<Material> liquids = Lists.newArrayList();
    private static List<Integer> liquidsID = Lists.newArrayList();

    static {
        Material[] materials = new Material[]{
                Material.AIR,
                Material.ACTIVATOR_RAIL,
                Material.BROWN_MUSHROOM,
                Material.CARROT,
                Material.CROPS,
                Material.DETECTOR_RAIL,
                Material.DIODE,
                Material.DIODE_BLOCK_OFF,
                Material.DIODE_BLOCK_ON,
                Material.FENCE_GATE,
                Material.GRASS,
                Material.LADDER,
                Material.LEVER,
                Material.LONG_GRASS,
                Material.MELON_STEM,
                Material.NETHER_WARTS,
                Material.PAINTING,
                Material.PORTAL,
                Material.POTATO,
                Material.PUMPKIN_STEM,
                Material.RAILS,
                Material.REDSTONE,
                Material.RED_ROSE,
                Material.REDSTONE_COMPARATOR,
                Material.REDSTONE_COMPARATOR_OFF,
                Material.REDSTONE_COMPARATOR_ON,
                Material.REDSTONE_WIRE,
                Material.REDSTONE_TORCH_OFF,
                Material.REDSTONE_TORCH_ON,
                Material.SAPLING,
                Material.SIGN_POST,
                Material.SKULL,
                Material.SNOW,
                Material.TORCH,
                Material.TRIPWIRE,
                Material.WALL_SIGN,
                Material.WOOD_BUTTON,
                Material.STONE_BUTTON,
                Material.STONE_PLATE,
                Material.WOOD_PLATE,
                Material.YELLOW_FLOWER,
                Material.WOODEN_DOOR,
                Material.IRON_DOOR,
                Material.IRON_DOOR_BLOCK,
                Material.TRAP_DOOR,
        };

        transparents.addAll(Arrays.asList(materials));

        materials = new Material[]{
                Material.WATER,
                Material.STATIONARY_WATER,
                Material.LAVA,
                Material.STATIONARY_LAVA,
                Material.LADDER
        };

        liquids.addAll(Arrays.asList(materials));
        @Deprecated
        Integer[] materialsID = new Integer[]{
                Material.AIR.getId(),
                Material.ACTIVATOR_RAIL.getId(),
                Material.BROWN_MUSHROOM.getId(),
                Material.CARROT.getId(),
                Material.CROPS.getId(),
                Material.DETECTOR_RAIL.getId(),
                Material.DIODE.getId(),
                Material.DIODE_BLOCK_OFF.getId(),
                Material.DIODE_BLOCK_ON.getId(),
                Material.FENCE_GATE.getId(),
                Material.GRASS.getId(),
                Material.LADDER.getId(),
                Material.LEVER.getId(),
                Material.LONG_GRASS.getId(),
                Material.MELON_STEM.getId(),
                Material.NETHER_WARTS.getId(),
                Material.PAINTING.getId(),
                Material.PORTAL.getId(),
                Material.POTATO.getId(),
                Material.PUMPKIN_STEM.getId(),
                Material.RAILS.getId(),
                Material.REDSTONE.getId(),
                Material.RED_ROSE.getId(),
                Material.REDSTONE_COMPARATOR.getId(),
                Material.REDSTONE_COMPARATOR_OFF.getId(),
                Material.REDSTONE_COMPARATOR_ON.getId(),
                Material.REDSTONE_WIRE.getId(),
                Material.REDSTONE_TORCH_OFF.getId(),
                Material.REDSTONE_TORCH_ON.getId(),
                Material.SAPLING.getId(),
                Material.SIGN_POST.getId(),
                Material.SKULL.getId(),
                Material.SNOW.getId(),
                Material.TORCH.getId(),
                Material.TRIPWIRE.getId(),
                Material.WALL_SIGN.getId(),
                Material.WOOD_BUTTON.getId(),
                Material.STONE_BUTTON.getId(),
                Material.STONE_PLATE.getId(),
                Material.WOOD_PLATE.getId(),
                Material.YELLOW_FLOWER.getId(),
                Material.WOODEN_DOOR.getId(),
                Material.IRON_DOOR.getId(),
                Material.IRON_DOOR_BLOCK.getId(),
                Material.TRAP_DOOR.getId()
        };

        transparentsID.addAll(Arrays.asList(materialsID));
        materialsID = new Integer[]{
                Material.WATER.getId(),
                Material.STATIONARY_WATER.getId(),
                Material.LAVA.getId(),
                Material.STATIONARY_LAVA.getId(),
                Material.LADDER.getId()
        };

        liquidsID.addAll(Arrays.asList(materialsID));
    }

    public static boolean isTransparent(Material material) {
        return transparents.contains(material);
    }

    public static boolean isTransparent(Block block) {
        return isTransparent(block.getType());
    }

    @Deprecated
    public static boolean isTransParentID(Material material) {
        return transparentsID.contains(material.getId());
    }

    @Deprecated
    public static boolean isTransParentID(Block block) {
        return transparentsID.contains(block.getTypeId());
    }

    public static boolean isLiquid(Material material) {
        return liquids.contains(material);
    }

    public static boolean isLiquid(Block block) {
        return isLiquid(block.getType());
    }

    @Deprecated
    public static boolean isLiquidID(Material material) {
        return liquidsID.contains(material.getId());
    }

    @Deprecated
    public static boolean isLiquidID(Block block) {
        return liquidsID.contains(block.getTypeId());
    }
}
