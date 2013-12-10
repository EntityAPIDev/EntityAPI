package io.snw.entityapi.server;

import io.snw.entityapi.EntityAPI;

public class UnknownServer extends CraftBukkitServer {

    public boolean init() {
        if(!super.init()) {
            return false;
        }
        EntityAPI.LOGGER.warning("Could not identify this server brand!");
        return true;
    }

    @Override
    public String getName() {
        return "UNKNOWN SERVER";
    }
}
