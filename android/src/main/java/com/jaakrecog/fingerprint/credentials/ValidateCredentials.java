package com.jaakrecog.fingerprint.credentials;

import org.json.JSONException;

import java.io.IOException;

public interface ValidateCredentials {
    String validateCredentials(String apiToken,Boolean is_production) throws JSONException, IOException;
}
