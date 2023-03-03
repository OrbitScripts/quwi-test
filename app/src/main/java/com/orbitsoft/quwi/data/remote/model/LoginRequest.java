package com.orbitsoft.quwi.data.remote.model;

import com.squareup.moshi.Json;

public class LoginRequest {

    @Json(name = "email")
    String email;

    @Json(name = "password")
    String password;

    public LoginRequest(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
