package io.snw.entityapi.utils;

import net.minecraft.server.v1_7_R1.EntityLiving;
import org.bukkit.entity.Player;

public class PlayerUtil {

    public static Object getHandle(Player player) {
        return EntityUtil.getNavigation((EntityLiving) player);
    }
}
