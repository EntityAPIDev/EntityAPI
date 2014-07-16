package org.entityapi.api.pathfinding.navmesh;

public class Link {

    private float x;

    private float z;

    private Space target;

    public Link(float x, float z, Space target) {
        this.x = x;
        this.z = z;
        this.target = target;
    }

    public float getX() {
        return this.x;
    }

    public float getZ() {
        return this.z;
    }

    public Space getTarget() {
        return this.target;
    }

    public float distance2(float x1, float z2) {
        float fx = this.x - x1;
        float yx = this.z - z2;

        return ((fx * fx) + (yx * yx));
    }
}
