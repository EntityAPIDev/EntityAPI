package org.entityapi.api.plugin;

public interface Server {

    public boolean init();

    public boolean postInit();

    public Class getClass(String name);

    public Class getNMSClass(String name);

    public Class getCBClass(String name);

    public String getName();

    public int getVersion();

    public Object getMinecraftServer();

    public String getMCVersion();

    public boolean isCompatible();

}
