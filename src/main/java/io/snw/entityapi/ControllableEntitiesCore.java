package io.snw.entityapi;

/**
 * Class which will eventually take over some of the stuff in EntityAPI.
 */
public class ControllableEntitiesCore {

    private static ControllableEntitiesCore CORE_INSTANCE;

    public ControllableEntitiesCore(EntityAPI entityAPI) {
        if(CORE_INSTANCE != null) {
            throw new RuntimeException("Only one instance of the core can run!");
        }
        CORE_INSTANCE = this;
    }
}
