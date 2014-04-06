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

package org.entityapi.nbt;

import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import static junit.framework.Assert.assertEquals;

public class NBTStreamToolsTest {

    private final File DATA_FILE = new File("./data.nbt");

    private final String UNIQUE_KEY = "949ede53-1e61-4b9c-bdf7-0863866dd55d";

    @Test
    public void testWrite() throws Exception {
        if (!DATA_FILE.exists()) {
            DATA_FILE.createNewFile();
        } else {
            DATA_FILE.delete();
        }

        NBTTagCompound compound = new NBTTagCompound();
        compound.setString("key", UNIQUE_KEY);

        NBTStreamTools.write(compound, new FileOutputStream(DATA_FILE));
    }

    @Test
    public void testRead() throws Exception {
        NBTTagCompound compound = NBTStreamTools.read(new FileInputStream(DATA_FILE));

        assertEquals("Saved UUID didn't match the retrieved UUID!\nGiven Key: " + UNIQUE_KEY + "\nRetrieved Key: " + compound.getString("key"), UNIQUE_KEY, compound.getString("key"));

        System.out.print("Test passed!");
    }
}
