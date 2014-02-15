package org.entityapi.utils;

import org.entityapi.reflection.MethodAccessor;
import org.entityapi.reflection.SafeMethod;
import org.bukkit.entity.Player;

public class PlayerUtil {

    private static final MethodAccessor<Object> GET_HANDLE = new SafeMethod<>(Player.class, "getHandle");

    public static Object getHandle(Player player) {
        return GET_HANDLE.invoke(player);
    }
}
