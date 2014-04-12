/*
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

import org.bukkit.Location;
import org.entityapi.api.entity.ControllableEntity;

import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.TreeSet;

public class Pathfinder {

    private final ControllableEntity controllableEntity;
    private boolean isAsync;

    private PriorityQueue<BlockNode> open_list = new PriorityQueue<>();

    private HeuristicType heuristicType = HeuristicType.MANHATTAN;
    private TreeSet<BlockNode> openList = new TreeSet<>();
    private Set<BlockNode> closedList = new HashSet<>();

    public Pathfinder(ControllableEntity controllableEntity) {
        this(controllableEntity, true);
    }

    public Pathfinder(ControllableEntity controllableEntity, boolean async) {
        this.controllableEntity = controllableEntity;
        this.isAsync = async;
    }

    public boolean isAsync() {
        return this.isAsync;
    }

    public void setAsyn(boolean async) {
        this.isAsync = async;
    }

    public Set<BlockNode> find(Location to) {
        return null;
    }

    public HeuristicType getHeuristicType() {
        return this.heuristicType;
    }
}
