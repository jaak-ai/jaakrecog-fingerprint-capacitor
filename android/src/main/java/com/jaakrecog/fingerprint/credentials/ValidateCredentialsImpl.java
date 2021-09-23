package com.jaakrecog.fingerprint.credentials;

import android.content.Context;

import com.jaakrecog.fingerprint.network.CallApiImpl;

import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;

public class ValidateCredentialsImpl extends Thread implements ValidateCredentials {

    private final CallApiImpl callApi = new CallApiImpl();
    private final Context context;

    public ValidateCredentialsImpl(Context context) {
        this.context = context;
    }


    @Override
    public String validateCredentials(String apiKey,Boolean is_production) throws JSONException, IOException {

        String url = "https://dev.api.jaakrecog.com/api/v1/session/";

        if(is_production){

                url = "https://api.jaakrecog.com/api/v1/session/";
        }
        String response = callApi.callPost(url, new JSONObject().put("apiKey", apiKey));

        CredentialsResponse credentialsResponse = new CredentialsResponse(new JSONObject(response));

        return credentialsResponse.getJWT();

    }


}
