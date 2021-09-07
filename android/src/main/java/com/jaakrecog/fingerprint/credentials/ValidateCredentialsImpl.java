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
    public String validateCredentials(String apiKey,Boolean dev) throws JSONException, IOException {

        String url = "https://dev.api.jaakrecog.com/api/session";

        if(!dev){

                url = "https://api.jaakrecog.com/api/session";
        }
        String response = callApi.callPost(url, new JSONObject().put("apiKey", apiKey));

        CredentialsResponse credentialsResponse = new CredentialsResponse(new JSONObject(response));

        return credentialsResponse.getJWT();

    }


}
