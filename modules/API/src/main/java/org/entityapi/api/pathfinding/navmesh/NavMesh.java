package org.entityapi.api.pathfinding.navmesh;

import java.util.ArrayList;
import java.util.List;

public class NavMesh {

    private List<Space> spaces = new ArrayList<>();

    public NavMesh() {
    }

    public NavMesh(List<Space> spaces) {
        this.spaces.addAll(spaces);
    }

    public int getSpacesCount() {
        return this.spaces.size();
    }

    public List<Space> getSpaces() {
        return this.spaces;
    }

    public Space getSpace(int index) {
        return this.spaces.get(index);
    }

    public void addSpace(Space space) {
        this.spaces.add(space);
    }

    public Space getSpaceAt(float x, float z) {
        return null; // TODO: implement properly
    }
}
