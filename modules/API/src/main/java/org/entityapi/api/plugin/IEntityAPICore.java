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

package org.entityapi.api.plugin;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.util.Vector;
import org.entityapi.api.EntityManager;
import org.entityapi.api.IBasicEntityUtil;
import org.entityapi.api.ISpawnUtil;
import org.entityapi.api.entity.ControllableEntity;

public interface IEntityAPICore extends Plugin {

    public String getVersion();

    public Server getAPIServer();

    public EntityManager createManager(Plugin owningPlugin);

    public EntityManager createEntityManager(Plugin owningPlugin, boolean keepInMemory);

    public void registerManager(String name, EntityManager manager);

    public boolean hasEntityManager(Plugin plugin);

    public boolean hasEntityManager(String pluginName);

    public EntityManager getManagerFor(Plugin plugin);

    public EntityManager getManagerFor(String pluginName);

    public ISpawnUtil getSpawnUtil();

    public IBasicEntityUtil getBasicEntityUtil();

    public void callOnTick(ControllableEntity controllableEntity);

    public boolean callOnInteract(ControllableEntity controllableEntity, Player entity, boolean rightClick);

    public Vector callOnPush(ControllableEntity controllableEntity, double x, double y, double z);

    public boolean callOnCollide(ControllableEntity controllableEntity, Entity entity);

    public void callOnDeath(ControllableEntity controllableEntity);
}