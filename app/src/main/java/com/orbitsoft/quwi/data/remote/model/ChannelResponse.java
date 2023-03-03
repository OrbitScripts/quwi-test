package com.orbitsoft.quwi.data.remote.model;

import com.squareup.moshi.Json;

import java.util.List;

import javax.annotation.Nullable;

public class ChannelResponse {

    @Json(name = "id_partner")
    int partner;

    @Json(name = "id_users")
    int[] idUsers;

    @Nullable
    @Json(name = "message_last")
    MessageResponse messageLast;

    public int getPartner() {
        return partner;
    }

    public int[] getIdUsers() {
        return idUsers;
    }

    @Nullable
    public MessageResponse getMessageLast() {
        return messageLast;
    }
}
