package io.snw.entityapi.internal;

import io.snw.entityapi.reflection.MethodAccessor;
import io.snw.entityapi.reflection.SafeMethod;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

public class CommonNMS {

    private static final MethodAccessor<Object> ENTITY_GET_HANDLE = new SafeMethod(Entity.class, Constants.Entity.ENTITY_HANDLE);
    private static final MethodAccessor<Object> PLAYER_GET_HANDLE = new SafeMethod(Player.class, Constants.Entity.PLAYER_HANDLE);

    public static Object getNative(Entity entity) {
        return ENTITY_GET_HANDLE.invoke(entity);
    }

    public static Object getNative(Player player) {
        return PLAYER_GET_HANDLE.invoke(player);
    }

}
