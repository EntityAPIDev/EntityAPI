package org.entityapi.api.mind.behaviour;

public enum BehaviourType {

    ZERO,
    ONE,
    TWO,
    THREE,
    FOUR,
    FIVE,
    SIX,
    SEVEN;

    // NMS Goals have stored integers to check compatibility -> goal.j()
    // This enum is used to compare these goals easily and more friendly

    ;

    public boolean isCompatibleWith(BehaviourType type) {
        return (this.ordinal() & type.ordinal()) == 0;
    }
}