package io.snw.entityapi.api.pathfinding.astar;

import com.google.common.collect.Lists;
import org.bukkit.Material;
import org.bukkit.block.Block;

import java.util.Arrays;
import java.util.List;

public class MaterialChecker {

    private static List<Material> transparents = Lists.newArrayList();
    private static List<Material> liquids = Lists.newArrayList();

    static {
        Material[] materials = new Material[] {
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
                Material.YELLOW_FLOWER
        };

        transparents.addAll(Arrays.asList(materials));

        materials = new Material[] {
                Material.WATER,
                Material.STATIONARY_WATER,
                Material.LAVA,
                Material.STATIONARY_LAVA,
                Material.LADDER
        };

        liquids.addAll(Arrays.asList(materials));
    }

    public static boolean isTransparent(Material material) {
        return transparents.contains(material);
    }

    public static boolean isTransparent(Block block) {
        return isTransparent(block.getType());
    }

    public static boolean isLiquid(Material material) {
        return liquids.contains(material);
    }

    public static boolean isLiquid(Block block) {
        return isLiquid(block.getType());
    }
}
