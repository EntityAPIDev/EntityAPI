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

package org.entityapi.nms.v1_7_R1.entity;

import org.bukkit.entity.Horse;
import org.entityapi.api.ControllableEntityType;
import org.entityapi.api.EntityManager;
import org.entityapi.api.EntitySound;
import org.entityapi.api.entity.ControllableHorse;

public class ControllableHorseBase extends ControllableBaseEntity<Horse, ControllableHorseEntity> implements ControllableHorse {

    public ControllableHorseBase(int id, EntityManager manager) {
        super(id, ControllableEntityType.HORSE, manager);
    }

    public ControllableHorseBase(int id, ControllableHorseEntity entityHandle, EntityManager manager) {
        this(id, manager);
        this.handle = entityHandle;
        this.loot = entityHandle.getDefaultMaterialLoot();
    }

    @Override
    public void initSounds() {
        for (String key : new String[]{"idle", "hit", "death"}) {
            this.setSound(EntitySound.IDLE, "zombie", "mob.horse.zombie." + key);
            this.setSound(EntitySound.IDLE, "skeleton", "mob.horse.skeleton." + key);
            this.setSound(EntitySound.IDLE, "normal", "mob.horse." + key);
            this.setSound(EntitySound.IDLE, "donkey", "mob.horse.donkey." + key);
        }

        this.setSound(EntitySound.STEP, "gallop", "mob.horse.gallop");
        this.setSound(EntitySound.STEP, "wood", "mob.horse.wood");
        this.setSound(EntitySound.STEP, "soft", "mob.horse.soft");
        this.setSound(EntitySound.STEP, "armor", "mob.horse.armor");
        this.setSound(EntitySound.STEP, "leatherarmor", "mob.horse.leather");
        this.setSound(EntitySound.STEP, "land", "mob.horse.land");

        this.setSound(EntitySound.BREATHE, "mob.horse.breathe");
        this.setSound(EntitySound.JUMP, "mob.horse.jump");
    }
}