package io.snw.entityapi.api.pathfinding.astar.checkers;

import org.bukkit.Material;
import org.bukkit.block.Block;

public abstract class BlockChecker {

    private final Material material;

    public BlockChecker(Material material) {
        this.material = material;
    }

    public Material getMaterial() {
        return this.material;
    }

    public abstract boolean canPass(Block block);

    public abstract boolean check(Block block);
}
