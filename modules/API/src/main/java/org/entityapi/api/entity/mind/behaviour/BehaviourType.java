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

package org.entityapi.api.entity.mind.behaviour;

public enum BehaviourType {

    /*
     * NMS Goals have stored integers to check compatibility -> goal.j()
     * This enum is used to compare these goals easily and more friendly
     */

    /**
     * Subconscious behaviours that the entity does without thinking. Should be compatible with all other behaviours
     */
    SUBCONSCIOUS(0),

    /**
     * Instinctive behaviours of the entity
     */
    INSTINCT(1),

    /**
     * Behaviours involving attention to players
     */
    ATTENTION(2),

    /**
     * Active behaviours requiring full concentration
     */
    ACTION(3),

    /**
     * Behaviours that deal with additions to movement
     */
    MOVEMENT(4),

    /**
     * Behaviours that may occasionally occur
     */
    IMPULSE(5),

    /**
     * Food-related or includes eating of something
     */
    FOOD(7);

    private int id;

    BehaviourType(int id) {
        this.id = id;
    }

    /**
     * Gets the compatibility ID of the behaviour
     *
     * @return compatibility ID
     */
    public int getId() {
        return id;
    }

    /**
     * Checks if a behaviour is compatible with another
     *
     * @param type behaviour to check compatibility against
     * @return true if the two behaviours are compatible
     */
    public boolean isCompatibleWith(BehaviourType type) {
        return (this.getId() & type.getId()) == 0;
    }
}