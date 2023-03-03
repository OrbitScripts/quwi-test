package com.orbitsoft.quwi.data.remote.model;

import com.squareup.moshi.Json;

import java.util.List;

public class UsersResponse {

    @Json(name = "users")
    List<UserResponse> users;

    public List<UserResponse> getUsers() {
        return users;
    }
}
