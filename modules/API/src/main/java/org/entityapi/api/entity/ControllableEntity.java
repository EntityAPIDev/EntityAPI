/*
 * This file is part of EntityAPI.
 *
 * EntityAPI is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * EntityAPI is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with EntityAPI.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.entityapi.api.entity;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.LivingEntity;
import org.bukkit.util.Vector;
import org.entityapi.api.EntityManager;
import org.entityapi.api.entity.mind.Mind;

import java.util.HashMap;
import java.util.Map;

public abstract interface ControllableEntity<T extends LivingEntity> extends Nameable, Attacking {

    // TODO: JavaDucks. Quack

    public EntityManager getEntityManager();

    public abstract int getId();

    public Mind getMind();

    public abstract T getBukkitEntity();

    public abstract ControllableEntityType getEntityType();

    public float getHeight();

    public float getWidth();

    public boolean spawnEntity(Location spawnLocation);

    public boolean hasSpawned();

    @Override
    public String getName();

    @Override
    public boolean setName(String name);

    public Map<String, String> getSounds(EntitySound type);

    public String getSound(EntitySound type);

    public String getSound(EntitySound type, String key);

    public String getCustomSound(EntitySound type, String key);

    public void setSound(EntitySound type, Sound toReplace, Sound replaceWith, boolean addOnFail);

    public void setSound(EntitySound type, Sound sound);

    public void setSound(EntitySound type, Sound sound, String key);

    public void setSound(EntitySound type, String sound);

    public void setSound(EntitySound type, String key, String sound);

    public void setSound(EntitySound type, HashMap<String, String> soundMap);

    public Material getLoot();

    public void setLoot(Material material);

    public boolean shouldUpdateAttributes();

    public void setTickAttributes(boolean flag);

    public double getSpeed();

    public void setSpeed(double speed);

    public void setSpeed(double speed, boolean overridePathfindingSpeed);

    public void setPathfindingRange(double range);

    public double getPathfindingRange();

    public boolean navigateTo(LivingEntity livingEntity);

    public boolean navigateTo(LivingEntity livingEntity, double speed);

    public boolean navigateTo(Location to);

    public boolean navigateTo(Location to, double speed);

    public boolean navigateTo(Vector to);

    public boolean navigateTo(Vector to, double speed);

    public void setDefaultBehaviours();

    public boolean isStationary();

    public void setStationary(boolean flag);

    public void setYaw(float value);

    public void setHeadYaw(float value);

    public void setPitch(float value);

    public boolean isControllableRiding();

    public void setControllableRiding(boolean flag);
}
