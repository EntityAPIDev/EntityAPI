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

package org.entityapi.api.entity.mind;

import com.captainbern.reflection.ClassTemplate;
import com.captainbern.reflection.Reflection;
import com.captainbern.reflection.SafeConstructor;
import com.captainbern.reflection.matcher.AbstractMatcher;
import org.entityapi.api.entity.ControllableEntity;
import org.entityapi.api.events.ControllableEntityEvent;
import org.entityapi.api.plugin.EntityAPI;
import org.entityapi.exceptions.AttributeMindRequiredException;

import java.lang.reflect.Constructor;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

public abstract class Attribute<T extends ControllableEntityEvent> {

    protected Mind mind;

    public T call(Object... args) {
        T event = null;
        try {
            event = getNewEvent(args);
        } catch (ArrayIndexOutOfBoundsException ignored) {

        }

        if (event != null) // FIXME
            EntityAPI.getCore().getServer().getPluginManager().callEvent(event);

        return call(event);
    }

    protected T call(T event) {
        return event;
    }

    @SuppressWarnings("unchecked")
    protected T getNewEvent(final Object... args) {
        Type paramType = this.getClass().getGenericSuperclass();
        Class<?> paramClass = (Class<?>) paramType;

        Type paramEventType = paramClass.getGenericSuperclass();
        Class<T> event = (Class<T>) ((ParameterizedType) paramEventType).getActualTypeArguments()[0];

        ClassTemplate<T> template = new Reflection().reflect(event);

        try {

            List<SafeConstructor<T>> candidates = template.getSafeConstructors(new AbstractMatcher<Constructor>() {
                @Override
                public boolean matches(Constructor constructor) {
                    if (constructor.getParameterTypes().length != args.length)
                        return false;

                    Class<?>[] argTypes = constructor.getParameterTypes();
                    for (int i = 0; i < argTypes.length; i++) {
                        if (!argTypes[i].isAssignableFrom(args[i].getClass()))
                            return false;
                    }
                    return true;
                }
            });

            if (candidates.size() > 0) {
                return candidates.get(0).getAccessor().invoke(args);
            } else {
                throw new IllegalStateException(); // Will throw a "proper" exception
            }

        } catch (Exception e) { // TODO: Should we be throwing exceptions?
            if (args.length > 0) {
                String argBuilder = args[0].getClass().getCanonicalName();
                for (int i = 1; i < args.length; i++) {
                    argBuilder += ", " + args[i].getClass().getCanonicalName();
                }

                throw new RuntimeException("Failed to get the right constructor! (Event class: " + event.getCanonicalName() +
                        "\nArguments: " + argBuilder);
            } else {
                throw new RuntimeException("Failed to get the right constructor! (Event class: " + event.getCanonicalName() + ")");
            }
        }
    }

    protected Attribute applyTo(Mind mind) {
        Attribute attribute = this.copyTo(mind);
        if (attribute == null) {
            try {
                attribute = this.getClass().newInstance();
            } catch (InstantiationException | IllegalAccessException e) {
                throw new RuntimeException("Failed to copyTo new Attribute! Ensure that the Attribute#copyTo(Mind) method constructs a new Attribute that can be applied to an entity's mind.", e);
            }
        }
        if (attribute != null) {
            attribute.mind = mind;
        }
        return attribute;
    }

    public abstract Attribute copyTo(Mind mind);

    protected void removeFrom(Mind mind) {
        this.mind = null;
        this.onRemove(mind);
    }

    protected void onRemove(Mind mind) {
    }

    public Mind getMind() {
        if (mind == null) {
            throw new AttributeMindRequiredException();
        }
        return mind;
    }

    public ControllableEntity getControllableEntity() {
        return this.mind.getControllableEntity();
    }

    public abstract String getKey();

    public void tick() {

    }
}