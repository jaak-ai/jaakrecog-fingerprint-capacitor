package com.jaakrecog.fingerprint;

import static android.app.Activity.RESULT_OK;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.util.Base64;
import android.util.Log;

import androidx.activity.result.ActivityResult;

import com.getcapacitor.JSObject;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.ActivityCallback;
import com.getcapacitor.annotation.CapacitorPlugin;
import com.jaakrecog.fingerprint.credentials.ValidateCredentialsImpl;
import com.jaakrecog.fingerprint.utils.Utils;

import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

@CapacitorPlugin(name = "FingerPrint")
public class FingerPrintPlugin extends Plugin {

    private ValidateCredentialsImpl validateCredentials;
     @PluginMethod
    public void callFingerAcquisition(PluginCall call) {
        try {
            String apiKey = call.getString("accessToken");
            Boolean dev =   call.getBoolean("is_production");

            validateCredentials= new ValidateCredentialsImpl(this.getContext());
            String jwToken=validateCredentials.validateCredentials(apiKey,dev);

            Intent callIntentActivity =
                    new Intent(getActivity(),
                    Class.forName("com.jaakit.fingeracequisition.FingerActivity"));
            callIntentActivity.putExtra("jwtoken",jwToken);
            callIntentActivity.putExtra("isProduction",dev);

            startActivityForResult(call,callIntentActivity,"captureFingersResult");


        }catch (Exception ex){
            Log.e("Exception",ex.getMessage());
            JSObject ret = new JSObject();
            ret.put("error", ex.getMessage());
            call.resolve(ret);

        }
     }

    @ActivityCallback
    private void captureFingersResult(PluginCall call, ActivityResult result) {
        if (call == null) {
            return;
        }
        if ((result.getResultCode()== RESULT_OK)) {

            String fingerLeftWsq=    result.getData().getStringExtra("fingerLeftWsq");
            String fingerRigthWsq=   result.getData().getStringExtra("fingerRigthWsq");

            Log.e("Plugin finger result",":"+fingerRigthWsq);
            Log.e("Plugin finger result",":"+fingerRigthWsq);
            byte[] bytesWsqLeft = Base64.decode(fingerLeftWsq, 1);
            InputStream inputStreamLeftFinger = new ByteArrayInputStream(bytesWsqLeft);
            byte[] bytesWsqRigth = Base64.decode(fingerRigthWsq, 1);
            InputStream inputStreamRigthFinger = new ByteArrayInputStream(bytesWsqRigth);



            JSObject ret = new JSObject();
            ret.put("fingerRigth", fingerLeftWsq);
            ret.put("fingerLeft", fingerRigthWsq);
            call.resolve(ret);


        }else{
            String fingerLeftError=   result.getData().getStringExtra("fingerLeftError");
            String fingerRigthError=   result.getData().getStringExtra("fingerRigthError");
            JSObject ret = new JSObject();
            ret.put("errorRigth", fingerLeftError);
            ret.put("errorLeft", fingerRigthError);
            call.resolve(ret);


        }


    }

}


