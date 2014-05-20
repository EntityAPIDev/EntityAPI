/*
 * Copyright (C) EntityAPI Team
 *
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

package org.entityapi.api.reflection.refs;

import org.entityapi.api.reflection.ClassTemplate;
import org.entityapi.api.reflection.FieldAccessor;
import org.entityapi.api.reflection.NMSClassTemplate;

public class WorldServerRef {

    public static final ClassTemplate CLASS_TEMPLATE = NMSClassTemplate.create("WorldServer");
    public static final ClassTemplate WORLD_CLASS_TEMPLATE = NMSClassTemplate.create("World");

    public static final FieldAccessor ENTITY_TRACKER = CLASS_TEMPLATE.getField("tracker");
    public static final FieldAccessor PLAYER_CHUNK_MAP = CLASS_TEMPLATE.getField("manager");
    public static final FieldAccessor CHUNK_PROVIDER_SERVER = CLASS_TEMPLATE.getField("chunkProviderServer");
    public static final FieldAccessor<Boolean> SAVING_SWITCH = CLASS_TEMPLATE.getField("savingDisabled");

    public static Object getEntityTracker(Object worldServer) {
        return ENTITY_TRACKER.get(worldServer);
    }

    public static Object getPlayerChunkMap(Object worldServer) {
        return PLAYER_CHUNK_MAP.get(worldServer);
    }
}
