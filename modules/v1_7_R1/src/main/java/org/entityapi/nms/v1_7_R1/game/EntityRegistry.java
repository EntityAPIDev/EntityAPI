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

package org.entityapi.nms.v1_7_R1.game;

import com.captainbern.minecraft.reflection.MinecraftReflection;
import com.captainbern.reflection.ClassTemplate;
import com.captainbern.reflection.Reflection;
import com.captainbern.reflection.SafeField;
import com.captainbern.reflection.SafeMethod;
import com.captainbern.reflection.accessor.FieldAccessor;
import com.captainbern.reflection.accessor.MethodAccessor;
import org.entityapi.game.EntityRegistrationEntry;
import org.entityapi.game.IEntityRegistry;

import java.util.List;
import java.util.Map;

import static com.captainbern.reflection.matcher.Matchers.combine;
import static com.captainbern.reflection.matcher.Matchers.withArguments;
import static com.captainbern.reflection.matcher.Matchers.withType;

public class EntityRegistry extends IEntityRegistry {

    private static FieldAccessor<Map> NAME_TO_CLASS = null;
    private static FieldAccessor<Map> ID_TO_CLASS = null;
    private static MethodAccessor<Void> REGISTER = null;

    @Override
    public void register(EntityRegistrationEntry entityRegistrationEntry) {
        if (NAME_TO_CLASS == null || ID_TO_CLASS == null || REGISTER == null)
            initializeFields();

        unregister(entityRegistrationEntry);

        REGISTER.invokeStatic(
                entityRegistrationEntry.getEntityClass(),
                entityRegistrationEntry.getName(),
                entityRegistrationEntry.getId()
        );
    }

    @Override
    public void unregister(EntityRegistrationEntry entityRegistrationEntry) {
        if (NAME_TO_CLASS == null || ID_TO_CLASS == null)
            initializeFields();

        NAME_TO_CLASS.getStatic().remove(entityRegistrationEntry.getName());
        ID_TO_CLASS.getStatic().remove(entityRegistrationEntry.getId());
    }

    protected static void initializeFields() {
        ClassTemplate template = new Reflection().reflect(MinecraftReflection.getMinecraftClass("EntityTypes"));

        List<SafeField<Map>> fieldCandidates = template.getSafeFields(withType(Map.class));

        if (NAME_TO_CLASS == null) { // first field
            if (fieldCandidates.size() > 0) {
                for (SafeField<Map> mapField : fieldCandidates) {
                    for (Object key : mapField.getAccessor().getStatic().keySet()) {
                        if (key instanceof String) {
                            Object value = mapField.getAccessor().getStatic().get(key);

                            if (value instanceof Class) {
                                NAME_TO_CLASS = mapField.getAccessor();
                                break;
                            }
                        }
                    }

                    if (NAME_TO_CLASS != null)
                        break; // I want to break free!
                }
            } else {
                throw new RuntimeException("Failed to find the Name -> Class map in the EntityTypes class!");
            }
        }

        if (ID_TO_CLASS == null) { // Third field
            if (fieldCandidates.size() > 0) { // We know this is the third field
                for (SafeField<Map> mapField : fieldCandidates) {
                    for (Object key : mapField.getAccessor().getStatic().keySet()) {
                        if (key instanceof Integer) {
                            Object value = mapField.getAccessor().getStatic().get(key);

                            if (value instanceof Class) {
                                ID_TO_CLASS = mapField.getAccessor();
                                break;
                            }
                        }
                    }

                    if (ID_TO_CLASS != null)
                        break;
                }
            } else {
                throw new RuntimeException("Failed to find the Id -> Class mao in the EntityTypes class!");
            }
        }

        if (REGISTER == null) {
            List<SafeMethod<Void>> candidates = template.getSafeMethods(withArguments(Class.class, String.class, int.class));

            if (candidates.size() > 0) {
                REGISTER = candidates.get(0).getAccessor();
            } else {
                throw new RuntimeException("Failed to get the entity register method! (EntityTypes#a(Class, int, String);)");
            }
        }
    }
}
