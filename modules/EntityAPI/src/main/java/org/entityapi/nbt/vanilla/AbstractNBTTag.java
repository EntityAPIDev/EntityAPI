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

package org.entityapi.nbt.vanilla;

import org.entityapi.api.plugin.EntityAPI;
import org.entityapi.api.wrapper.AbstractWrapper;
import org.entityapi.nbt.NBTBase;

public class AbstractNBTTag<T extends NBTBase> extends AbstractWrapper {

    private Class<T> TAG_TYPE;
    private NBTBase TAG_TYPE_HANDLE;

    public AbstractNBTTag(NBTBase handle) {
        try {
            this.TAG_TYPE = (Class<T>) handle.getClass();
        } catch (ClassCastException e) {
            EntityAPI.LOGGER_REFLECTION.warning("Incompatible TAG_TYPE and TAG_HANDLE!");
        }
        this.TAG_TYPE_HANDLE = handle;
    }

    public NBTBase getTag() {
        return this.TAG_TYPE_HANDLE;
    }
}
