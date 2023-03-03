package com.orbitsoft.quwi.data.remote.model;

import com.squareup.moshi.Json;

public class LoginResponse {
    @Json(name = "token")
    String token;

    public String getToken() {
        return token;
    }
}
