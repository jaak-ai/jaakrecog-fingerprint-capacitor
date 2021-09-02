package com.jaakrecog.fingerprint.network;


import org.json.JSONObject;

import java.io.IOException;

public interface CallApi {
    String callPost(String url, JSONObject data) throws IOException;
}
