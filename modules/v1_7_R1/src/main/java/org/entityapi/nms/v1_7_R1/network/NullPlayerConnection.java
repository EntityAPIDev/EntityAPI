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

package org.entityapi.nms.v1_7_R1.network;

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
