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

package org.entityapi.api.reflection;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * This is a WIP class, it will come in handy for some stuff.
 */
public class Modifier<T> {

    protected ClassTemplate<T> targetTemplate;
    protected Object target;

    public Modifier(Object object) {
        this(ClassTemplate.create(object.getClass()), object);
    }

    public Modifier(Class<?> clazz) {
        this(ClassTemplate.create(clazz), null);
    }

    public Modifier(ClassTemplate template, Object object) {
        this.targetTemplate = template;
        this.target = object;
    }

    public <T> Modifier<T> withType(Class type) {
        List<SafeField<?>> layer = new ArrayList<SafeField<?>>();
        HashMap<SafeField<?>, Integer> indexer = new HashMap<SafeField<?>, Integer>();

        int index = 0;

        for (SafeField field : this.targetTemplate.getFields()) {
            if (type != null && type.getClass().isAssignableFrom(field.getType().getType())) {
                layer.add(field);

                if (indexer.containsKey(field))
                    indexer.put(field, index);
            }
            index++;
        }
        return null;
    }
}
