package com.jaakrecog.fingerprint;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
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

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

@CapacitorPlugin(name = "FingerPrint")
public class FingerPrintPlugin extends Plugin {

    private FingerPrint implementation = new FingerPrint();
    private ValidateCredentialsImpl validateCredentials;
     @PluginMethod
    public void callFingerAcequisition(PluginCall call) {
        try {
            String apiKey = call.getString("apikey");
            Boolean dev = call.getBoolean("develop");

            validateCredentials= new ValidateCredentialsImpl(this.getContext());
            String jwToken=validateCredentials.validateCredentials(apiKey,dev);

            Intent callIntentActivity =
                    new Intent(getActivity(),
                    Class.forName("com.jaakit.fingeracequisition.FingerActivity"));
            callIntentActivity.putExtra("jwtoken",jwToken);
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
        Uri imgURILeft = Uri.parse(result.getData().getStringExtra("fingerLeftBytes"));
        Uri imgURIRigth = Uri.parse(result.getData().getStringExtra("fingerRigthBytes"));

        try{

            ContentResolver cr = getContext().getContentResolver();
            InputStream inputStreamLeftFinger = cr.openInputStream(imgURILeft);
            Utils.copyInputStreamToFile(inputStreamLeftFinger,new File("dedo_izquierdo.wsq"));
            InputStream inputStreamRigthFinger = cr.openInputStream(imgURIRigth);
            Utils.copyInputStreamToFile(inputStreamRigthFinger,new File("dedo_derecho.wsq"));
            JSObject ret = new JSObject();
            ret.put("fingerRigth", imgURIRigth);
            ret.put("fingerLeft", imgURILeft);
            call.resolve(ret);

        }catch (FileNotFoundException fileNotFoundException){
            fileNotFoundException.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }



    }

}


