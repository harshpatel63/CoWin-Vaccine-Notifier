package com.example.cowinvaccinenotifier.network.properties;

import java.util.List;
import javax.annotation.Generated;
import com.squareup.moshi.Json;

@Generated("jsonschema2pojo")
public class SessionClass {

    @Json(name = "sessions")
    private List<Sessions> sessions = null;

    public List<Sessions> getSessions() {
        return sessions;
    }
}
