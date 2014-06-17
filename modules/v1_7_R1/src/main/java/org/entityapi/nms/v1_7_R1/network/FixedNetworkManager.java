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

package org.entityapi.nms.v1_7_R1.network;

import com.captainbern.reflection.Reflection;
import com.captainbern.reflection.accessor.FieldAccessor;
import net.minecraft.server.v1_7_R1.NetworkManager;
import net.minecraft.util.io.netty.channel.Channel;
import org.entityapi.internal.Constants;

import java.net.InetSocketAddress;

public class FixedNetworkManager extends NetworkManager {

    private FieldAccessor<Channel> channelField = new Reflection().reflect(super.getClass()).getSafeFieldByNameAndType(Constants.NetworkManager.CHANNEL_FIELD.get(), Channel.class).getAccessor();
    private FieldAccessor<InetSocketAddress> addressField = new Reflection().reflect(super.getClass()).getSafeFieldByNameAndType(Constants.NetworkManager.ADDRESS_FIELD.get(), InetSocketAddress.class).getAccessor();

    public FixedNetworkManager() {
        super(false);
        this.swapFields();
    }

    protected void swapFields() {
        channelField.set(this, new NullChannel(null));
        addressField.set(this, null);
    }
}
