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

package org.entityapi.game;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class GameRegistry {

    private static final Map<Class<? extends Object>, Object> registry = Maps.newHashMap();
    private static final Set<Class<? extends Object>> permStore = Sets.newSetFromMap(Maps.<Class<? extends Object>, Boolean>newConcurrentMap());

    public static <T, O extends T> O register(Class<T> type, O object) {
        registry.put(type, object);
        return object;
    }

    public static <T, O extends T> O registerPermanently(Class<T> type, O object) {
        registry.put(type, object);
        permStore.add(type);
        return object;
    }

    public static <O> O get(Class<O> type) {
        return type.cast(registry.get(type));
    }

    public static <T> void remove(Class<T> type) {
        registry.remove(type);
    }

    public static void clear() {
        Iterator<Map.Entry<Class<?>, Object>> iterator = registry.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<Class<?>, Object> entry = iterator.next();
            if (!permStore.contains(entry.getKey())) {
                iterator.remove();
            }
        }
    }
}
