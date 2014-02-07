package io.snw.entityapi.utils;

import io.snw.entityapi.reflection.MethodAccessor;
import io.snw.entityapi.reflection.SafeMethod;
import org.bukkit.entity.Player;

public class PlayerUtil {

    private static final MethodAccessor<Object> GET_HANDLE = new SafeMethod<>(Player.class, "getHandle");

    public static Object getHandle(Player player) {
        return GET_HANDLE.invoke(player);
    }
}
