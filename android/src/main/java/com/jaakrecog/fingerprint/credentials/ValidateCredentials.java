package com.jaakrecog.fingerprint.credentials;

import org.json.JSONException;

import java.io.IOException;

public interface ValidateCredentials {
    String validateCredentials(String apiToken,Boolean dev) throws JSONException, IOException;
}
