package com.orbitsoft.quwi.ui.authFragment.models;

import com.orbitsoft.quwi.data.remote.model.ChannelResponse;
import com.orbitsoft.quwi.data.remote.model.UserResponse;

public class ChannelInfo {

    ChannelResponse channel;
    UserResponse user;

    public ChannelInfo(ChannelResponse channel, UserResponse user) {
        this.channel = channel;
        this.user = user;
    }

    public ChannelResponse getChannel() {
        return channel;
    }

    public UserResponse getUser() {
        return user;
    }
}
