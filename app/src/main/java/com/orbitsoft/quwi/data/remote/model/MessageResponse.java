package com.orbitsoft.quwi.data.remote.model;

import com.squareup.moshi.Json;

public class MessageResponse {

    @Json(name = "id_user")
    int idUser;

    @Json(name = "snippet")
    String message;

    @Json(name = "dta_create")
    String lastUpd;

    public String getMessage() {
        return message;
    }

    public String getLastUpd() {
        return lastUpd;
    }
}
