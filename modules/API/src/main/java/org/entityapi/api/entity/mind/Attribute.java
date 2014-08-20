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
import com.google.common.base.Function;
import com.google.common.collect.Collections2;
import org.entityapi.api.entity.ControllableEntity;
import org.entityapi.api.events.ControllableEntityEvent;
import org.entityapi.api.plugin.EntityAPI;
import org.entityapi.exceptions.AttributeMindRequiredException;

import javax.annotation.Nullable;
import java.lang.reflect.Constructor;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Collection;

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

    protected T getNewEvent(Object... args) {
        Type paramType = this.getClass().getGenericSuperclass();
        Class<?> paramClass = (Class<?>) paramType;

        Type paramEventType = paramClass.getGenericSuperclass();
        Class<T> event = (Class<T>) ((ParameterizedType) paramEventType).getActualTypeArguments()[0];

        ClassTemplate<T> template = new Reflection().reflect(event);
        Collection<Class> arguments = Collections2.transform(Arrays.asList(args), new Function<Object, Class>() {
            @Override
            public Class apply(@Nullable Object o) {
                if (o instanceof Class)
                    return (Class) o;
                else
                    return o.getClass();
            }
        });

        try {

            SafeConstructor<T> eventConstructor = template.getSafeConstructor(arguments.toArray(new Class[args.length]));

            if (eventConstructor != null)
                return eventConstructor.getAccessor().invoke(args);

        } catch (Exception e) { // TODO: Should we be throwing exceptions?
            if (args.length > 0) {
                StringBuilder argBuilder = new StringBuilder(16 * args.length);
                boolean isFirst = true;
                for (Class<?> arg : arguments) {
                    if (isFirst) {
                        argBuilder.append(arg.getCanonicalName());
                        isFirst = false;
                    } else {
                        argBuilder.append(", " + arg.getCanonicalName());
                    }
                }

                throw new RuntimeException("Failed to get the right constructor! (Event class: " + event.getCanonicalName() +
                        "\nArguments: " + argBuilder.toString());
            } else {
                throw new RuntimeException("Failed to get the right constructor! (Event class: " + event.getCanonicalName() + ")");
            }
        }

        return null;
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