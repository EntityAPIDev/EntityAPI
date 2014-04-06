package org.entityapi.api.pathfinding.astar.checkers;

import org.bukkit.Material;
import org.bukkit.block.Block;

public class LadderChecker extends BlockChecker {

    public LadderChecker() {
        super(Material.LADDER);
    }

    @Override
    public boolean canPass(Block block) {
        return true;
    }

    @Override
    public boolean check(Block block) {
        return true;
    }
}
