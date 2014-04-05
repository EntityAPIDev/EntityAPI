package org.entityapi.api.reflection.refs;

import org.bukkit.World;
import org.entityapi.api.reflection.*;

public class CraftWorldRef {

    public static final ClassTemplate CRAFT_WORLD = CBClassTemplate.create("CraftWorld");
    public static final MethodAccessor<Object> TO_WORLDSERVER = CRAFT_WORLD.getMethod("getHandle");

    public static final ClassTemplate WORLD = NMSClassTemplate.create("World");
    public static final FieldAccessor WORLD_PROVIDER = WORLD.getField("worldProvider");
    public static final MethodAccessor<Boolean> ADD_ENTITY = WORLD.getMethod("addEntity", NMSClassTemplate.create("Entity").getType());

    public static Object toNMSWorld(World world) { //returns the worldServer
        return TO_WORLDSERVER.invoke(world);
    }

    public static Object getWorldProvider(World world) { //returns the WorldProvider of a bukkit world.
        return WORLD_PROVIDER.get(toNMSWorld(world));
    }

    public static boolean addEntity(World world, Object NMSEntityClazz) {
        return ADD_ENTITY.invoke(toNMSWorld(world), NMSEntityClazz);
    }
}
