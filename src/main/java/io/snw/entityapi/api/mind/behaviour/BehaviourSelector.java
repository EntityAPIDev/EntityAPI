package io.snw.entityapi.api.mind.behaviour;

import io.snw.entityapi.api.ControllableEntity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class BehaviourSelector implements IBehaviourSelector {

    private Map<String, BehaviourItem> behaviourMap = new HashMap<>();
    private ArrayList<BehaviourItem> behaviours = new ArrayList<>();
    private ArrayList<BehaviourItem> activeBehaviours = new ArrayList<>();
    private ControllableEntity controllableEntity;
    private int delay = 0;

    public BehaviourSelector(ControllableEntity entity) {
        this.controllableEntity = entity;
    }

    @Override
    public void addBehaviour(Behaviour behaviour, int priority) {
        this.addBehaviour(behaviour.getDefaultKey(), behaviour, priority);
    }

    @Override
    public void addBehaviour(String key, Behaviour behaviour, int priority) {
        BehaviourItem behaviourItem = new BehaviourItem(behaviour, priority);
        if (this.behaviourMap.containsKey(key)) {
            return;
        }
        this.behaviourMap.put(key, behaviourItem);
        this.behaviours.add(behaviourItem);
    }

    @Override
    public void addAndReplaceBehaviour(String key, Behaviour behaviour, int priority) {
        if (this.behaviourMap.containsKey(key)) {
            this.removeBehaviour(key);
        }
        this.addBehaviour(key, behaviour, priority);
    }

    @Override
    public void removeBehaviour(Behaviour behaviour) {
        Iterator<BehaviourItem> iterator = this.behaviours.iterator();

        while (iterator.hasNext()) {
            BehaviourItem behaviourItem = iterator.next();
            Behaviour behaviour1 = behaviourItem.getBehaviour();

            if (behaviour1 == behaviour) {
                if (this.activeBehaviours.contains(behaviourItem)) {
                    behaviour1.finish();
                    this.activeBehaviours.remove(behaviourItem);
                }

                iterator.remove();
            }
        }
    }

    @Override
    public void removeBehaviour(String key) {
        Iterator<Map.Entry<String, BehaviourItem>> iterator = this.behaviourMap.entrySet().iterator();

        while (iterator.hasNext()) {
            Map.Entry<String, BehaviourItem> entry = iterator.next();
            BehaviourItem behaviourItem = entry.getValue();
            Behaviour behaviour1 = behaviourItem.getBehaviour();

            if (key.equals(entry.getKey())) {
                if (this.activeBehaviours.contains(behaviourItem)) {
                    behaviour1.finish();
                    this.activeBehaviours.remove(behaviourItem);
                }
                if (this.behaviours.contains(behaviourItem)) {
                    this.behaviours.remove(behaviourItem);
                }

                iterator.remove();
            }
        }
    }

    @Override
    public void clearBehaviours(String key) {
        this.behaviourMap.clear();
        this.behaviours.clear();

        Iterator<BehaviourItem> iterator = this.activeBehaviours.iterator();

        while (iterator.hasNext()) {
            iterator.next().getBehaviour().finish();
        }
        this.activeBehaviours.clear();
    }

    @Override
    public Behaviour getBehaviour(String key) {
        Iterator<Map.Entry<String, BehaviourItem>> iterator = this.behaviourMap.entrySet().iterator();

        while (iterator.hasNext()) {
            Map.Entry<String, BehaviourItem> entry = iterator.next();
            BehaviourItem behaviourItem = entry.getValue();
            Behaviour behaviour = behaviourItem.getBehaviour();

            if (key.equals(entry.getKey())) {
                return behaviour;
            }
        }
        return null;
    }

    @Override
    public void updateBehaviours() {
        if (this.delay++ % 3 == 0) {
            Iterator<BehaviourItem> iterator = this.behaviours.iterator();

            while (iterator.hasNext()) {
                BehaviourItem behaviourItem = iterator.next();
                if (this.activeBehaviours.contains(behaviourItem)) {
                    if (this.canUse(behaviourItem) && behaviourItem.getBehaviour().shouldContinue()) {
                        continue;
                    }
                    behaviourItem.getBehaviour().finish();
                    this.activeBehaviours.remove(behaviourItem);
                }

            }

            this.delay = 0;
        } else {
            Iterator<BehaviourItem> iterator = this.activeBehaviours.iterator();

            while (iterator.hasNext()) {
                BehaviourItem behaviourItem = iterator.next();
                if (!behaviourItem.getBehaviour().shouldContinue()) {
                    behaviourItem.getBehaviour().finish();
                    iterator.remove();
                }
            }
        }

        Iterator<BehaviourItem> iterator = this.activeBehaviours.iterator();

        while (iterator.hasNext()) {
            BehaviourItem behaviourItem = iterator.next();
            behaviourItem.getBehaviour().tick();
        }
    }

    private boolean canUse(BehaviourItem behaviourItem) {
        Iterator<BehaviourItem> iterator = this.behaviours.iterator();

        while (iterator.hasNext()) {
            BehaviourItem behaviourItem1 = iterator.next();
            if (behaviourItem1 != behaviourItem) {
                if (behaviourItem.getPriority() > behaviourItem1.getPriority()) {
                    if (!this.areCompatibile(behaviourItem, behaviourItem1) && this.activeBehaviours.contains(behaviourItem1)) {
                        return false;
                    }
                    //goal.i() -> isContinuous
                } else if (!behaviourItem1.getBehaviour().isContinuous() && this.activeBehaviours.contains(behaviourItem1)) {
                    return false;
                }
            }
        }
        return true;
    }

    private boolean areCompatibile(BehaviourItem behaviourItem, BehaviourItem behaviourItem1) {
        return behaviourItem.getBehaviour().getType().isCompatibleWith(behaviourItem1.getBehaviour().getType());
    }
}