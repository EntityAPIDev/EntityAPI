package org.entityapi.nms.v1_7_R1.network;

import net.minecraft.server.v1_7_R1.NetworkManager;
import net.minecraft.util.io.netty.channel.Channel;
import org.entityapi.internal.Constants;
import org.entityapi.api.reflection.FieldAccessor;
import org.entityapi.api.reflection.SafeField;

import java.net.InetSocketAddress;

public class FixedNetworkManager extends NetworkManager {

    private FieldAccessor<Channel> channelField = new SafeField<Channel>(super.getClass(), Constants.NetworkManager.CHANNEL_FIELD);
    private FieldAccessor<InetSocketAddress> addressField = new SafeField<InetSocketAddress>(super.getClass(), Constants.NetworkManager.ADDRESS_FIELD);

    public FixedNetworkManager() {
        super(false);
        this.swapFields();
    }

    protected void swapFields() {
        channelField.set(this, new NullChannel(null));
        addressField.set(this, null);
    }
}
