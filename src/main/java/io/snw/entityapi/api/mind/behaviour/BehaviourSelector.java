package io.snw.entityapi.api.mind.behaviour;

import io.snw.entityapi.api.ControllableEntity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class BehaviourSelector implements IBehaviourSelector {

    private Map<String, BehaviourItem> behaviourMap = new HashMap<>();
    private ArrayList<BehaviourItem> behaviours = new ArrayList<>();
    private ArrayList<BehaviourItem> activeBehaviours = new ArrayList<>();
    private ControllableEntity controllableEntity;

    public BehaviourSelector(ControllableEntity entity) {
        this.controllableEntity = entity;
    }

    // TODO: Implement this so that we have a 'mind' for our entity

    @Override
    public void addBehaviour(Behaviour behaviour, int priority) {

    }

    @Override
    public void removeBehaviour(Behaviour behaviour) {

    }

    @Override
    public Behaviour removeBehaviour(String key) {
        return null;
    }

    @Override
    public Behaviour clearBehaviours(String key) {
        return null;
    }

    @Override
    public Behaviour getBehaviour(String key) {
        return null;
    }

    @Override
    public void updateBehaviours() {

    }
}