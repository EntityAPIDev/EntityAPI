package io.snw.entityapi.nbt;

import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

public class NBTStreamToolsTest {

    private final File file = new File("./test.data");

    @Test
    public void testWrite() throws Exception {
        NBTTagCompound compound = new NBTTagCompound();
        compound.setString("name", "test1");

        file.delete();

        NBTStreamTools.write(compound, new FileOutputStream(file));
    }

    @Test
    public void testRead() throws Exception {
        NBTTagCompound compound = NBTStreamTools.read(new FileInputStream(file));

        for(String key : compound.getKeys()) {
            System.out.println("Key: " + key + "\nValue: " + compound.get(key).toString());
        }

        System.out.println(compound.getString("name"));
    }
}
