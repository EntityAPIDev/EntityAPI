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

package org.entityapi.api.reflection;

import org.entityapi.EntityAPICore;
import org.entityapi.api.reflection.utility.CommonReflection;

public class NMSClassTemplate extends ClassTemplate {

    protected NMSClassTemplate() {
        setNMSClass(getClass().getSimpleName());
    }

    public NMSClassTemplate(String className) {
        setNMSClass(className);
    }

    protected void setNMSClass(String name) {
        Class clazz = CommonReflection.getMinecraftClass(name);
        if (clazz == null) {
            EntityAPICore.LOGGER_REFLECTION.warning("Failed to find a matching class with name: " + name);
        }
        setClass(clazz);
    }

    public static NMSClassTemplate create(String className) {
        return new NMSClassTemplate(className);
    }
}
