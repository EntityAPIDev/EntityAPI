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
