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

package org.entityapi.reflection;

import org.entityapi.reflection.utility.CommonReflection;

public class APIReflection {

    public static String COMPAT_NMS_PATH = "org.entityapi.nms." + CommonReflection.getVersionTag();

    public static Class getVersionedClass(String classPath) {
        return CommonReflection.getClass(COMPAT_NMS_PATH + "." + classPath);
    }

    public static Class getControllableEntityClass(String classIdentifier, boolean nms) {
        return getVersionedClass("entity.Controllable" + classIdentifier + (nms ? "Entity" : "Base"));
    }
}