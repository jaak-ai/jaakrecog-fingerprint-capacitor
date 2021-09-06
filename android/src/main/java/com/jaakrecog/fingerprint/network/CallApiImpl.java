package com.jaakrecog.fingerprint.network;


import org.json.JSONObject;

import java.io.IOException;
import java.util.Objects;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class CallApiImpl implements CallApi {

    public String callPost(String url, JSONObject data) throws IOException {

        OkHttpClient client = new OkHttpClient();

        RequestBody body = RequestBody.create(MediaType.get("application/json; charset=utf-8"), data.toString());
        Request request = new Request.Builder().url(url).post(body).build();
        try (Response response = client.newCall(request).execute()) {
            return Objects.requireNonNull(response.body()).string();
        }
    }
}
