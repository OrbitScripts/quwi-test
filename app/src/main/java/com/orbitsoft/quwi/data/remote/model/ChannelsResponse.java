package com.orbitsoft.quwi.data.remote.model;

import com.squareup.moshi.Json;

import java.util.List;

public class ChannelsResponse {

    @Json(name = "channels")
    List<ChannelResponse> channels;

    public List<ChannelResponse> getChannels() {
        return channels;
    }
}
