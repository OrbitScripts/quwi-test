package com.orbitsoft.quwi.data.remote.model;

import com.squareup.moshi.Json;

public class UserResponse {

    @Json(name = "id")
    int id;

    @Json(name = "name")
    String name;

    @Json(name = "avatar_url")
    String avatarUrl;

    boolean isSavedMessages = false;

    public UserResponse getSavedMessagesUserResponse() {
        this.name = "Saved Messages";
        this.isSavedMessages = true;
        return this;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public boolean isSavedMessages() {
        return isSavedMessages;
    }
}
