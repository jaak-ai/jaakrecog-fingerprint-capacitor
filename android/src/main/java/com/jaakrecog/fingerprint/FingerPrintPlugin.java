package com.jaakrecog.fingerprint;

import static android.app.Activity.RESULT_OK;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.util.Base64;
import android.util.Log;

import androidx.activity.result.ActivityResult;
import androidx.annotation.RequiresApi;

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

@RequiresApi(api = Build.VERSION_CODES.R)
@CapacitorPlugin(name = "FingerPrint")
public class FingerPrintPlugin extends Plugin {


    Context context;
    String root;

    private ValidateCredentialsImpl validateCredentials;
     @PluginMethod
    public void callFingerAcquisition(PluginCall call) {
        try {
            String apiKey = call.getString("accessToken");
            Boolean dev =   call.getBoolean("is_production");

            context=getContext();
            root = context.getFilesDir().getAbsolutePath();
            validateCredentials= new ValidateCredentialsImpl(context);
            String jwToken=validateCredentials.validateCredentials(apiKey,dev);

            Intent callIntentActivity =
                    new Intent(getActivity(),
                    Class.forName("com.jaakit.fingeracquisition.FingerActivity"));
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
    private void captureFingersResult(PluginCall call, ActivityResult result) throws FileNotFoundException {
        if (call == null) {
            return;
        }

        if ((result.getResultCode()== RESULT_OK)) {


            Uri stringUriFingerLeftWsq=   result.getData().getParcelableExtra("uriWsqFingerLeft");
            Uri stringUriFingerRigthWsq=   result.getData().getParcelableExtra("uriWsqFingerRigth");

            InputStream inl = context.getContentResolver().openInputStream(stringUriFingerLeftWsq);
            InputStream inr = context.getContentResolver().openInputStream(stringUriFingerRigthWsq);
            File dirLeft = new File(root + "/wsq_left_finger.wsq");
            File dirRigth = new File(root + "/wsq_rigth_finger.wsq");


            JSObject ret = new JSObject();
            ret.put("fingerRigth", dirLeft.getAbsolutePath());
            ret.put("fingerLeft", dirRigth.getAbsolutePath());
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


