package org.entityapi.nms.v1_7_R1.nbt;

import org.entityapi.nbt.NBTStreamTools;
import org.entityapi.nbt.NBTTagCompound;
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
