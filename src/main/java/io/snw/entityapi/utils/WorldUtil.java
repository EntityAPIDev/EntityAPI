package io.snw.entityapi.utils;

import io.snw.entityapi.reflection.MethodAccessor;
import io.snw.entityapi.reflection.SafeMethod;
import org.bukkit.World;
import org.bukkit.entity.Entity;

import java.util.ArrayList;
import java.util.List;

public class WorldUtil {

    public static final MethodAccessor<?> GET_HANDLE = new SafeMethod(World.class, "getHabdle");

    public static Object getHandle(World world) {
        return GET_HANDLE.invoke(world);
    }

}
