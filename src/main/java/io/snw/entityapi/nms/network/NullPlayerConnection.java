package io.snw.entityapi.nms.network;

import net.minecraft.server.v1_7_R1.*;

public class NullPlayerConnection extends PlayerConnection {

    public NullPlayerConnection(MinecraftServer minecraftserver, NetworkManager networkmanager, EntityPlayer entityplayer) {
        super(minecraftserver, networkmanager, entityplayer);
    }

    @Override
    public void a(PacketPlayInWindowClick packet) {
    }

    @Override
    public void a(PacketPlayInTransaction packet) {
    }

    @Override
    public void a(PacketPlayInFlying packet) {
    }

    @Override
    public void a(PacketPlayInUpdateSign packet) {
    }

    @Override
    public void a(PacketPlayInBlockDig packet) {
    }

    @Override
    public void a(PacketPlayInBlockPlace packet) {
    }

    @Override
    public void disconnect(String s) {
    }

    @Override
    public void a(PacketPlayInHeldItemSlot packetplayinhelditemslot) {
    }

    @Override
    public void a(PacketPlayInChat packetplayinchat) {
    }

    @Override
    public void sendPacket(Packet packet) {
    }
}
