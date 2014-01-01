package io.snw.entityapi.wrapper;

import io.snw.entityapi.EntityAPI;

public class BasicWrapper {

    private Object handle;

    public BasicWrapper() {
    }

    protected void setHandle(Object handle) {
        if (handle == null) {
            EntityAPI.LOGGER_REFLECTION.warning("Cannot create a wrapper with a null handle!");
            return;
        }
        this.handle = handle;
    }

    public Object getHandle() {
        return this.handle;
    }
}
