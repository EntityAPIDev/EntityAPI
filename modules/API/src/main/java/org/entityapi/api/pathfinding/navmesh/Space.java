package org.entityapi.api.pathfinding.navmesh;

public class Space {

    private float x;

    private float z;

    private float width;

    private float height;

    private float cost;

    public Space(float x, float z, float width, float height) {
        this.x = x;
        this.z = z;
        this.width = width;
        this.height = height;
    }

    public float getX() {
        return this.x;
    }

    public float getZ() {
        return this.z;
    }

    public float getWidth() {
        return this.width;
    }

    public float getHeight() {
        return this.height;
    }

    public float getCost() {
        return this.cost;
    }
}
