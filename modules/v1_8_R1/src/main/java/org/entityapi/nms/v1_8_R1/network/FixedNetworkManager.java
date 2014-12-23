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

package org.entityapi.nms.v1_8_R1.network;

import com.captainbern.reflection.Reflection;
import com.captainbern.reflection.accessor.FieldAccessor;
import net.minecraft.server.v1_8_R1.NetworkManager;
import net.minecraft.util.io.netty.channel.Channel;

import java.net.SocketAddress;

public class FixedNetworkManager extends NetworkManager {

    private static FieldAccessor<Channel> CHANNEL_FIELD;
    private static FieldAccessor<SocketAddress> ADDRESS_FIELD;

    public FixedNetworkManager() {
        super(false);
        this.swapFields();
    }

    protected void swapFields() {
        if (CHANNEL_FIELD == null) {
            CHANNEL_FIELD = new Reflection().reflect(NetworkManager.class).getSafeFieldByType(Channel.class).getAccessor();
        }

        if (ADDRESS_FIELD == null) {
            ADDRESS_FIELD = new Reflection().reflect(NetworkManager.class).getSafeFieldByType(SocketAddress.class).getAccessor();
        }

        CHANNEL_FIELD.set(this, new NullChannel(null));
        ADDRESS_FIELD.set(this, new SocketAddress() {});
    }
}
