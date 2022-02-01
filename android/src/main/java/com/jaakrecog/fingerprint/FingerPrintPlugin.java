package com.jaakrecog.fingerprint;

import static android.app.Activity.RESULT_OK;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import androidx.activity.result.ActivityResult;
import androidx.annotation.RequiresApi;

import com.getcapacitor.JSObject;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.ActivityCallback;
import com.getcapacitor.annotation.CapacitorPlugin;
import com.google.gson.Gson;
import com.jaakit.fingerscapture.model.FingersAcquisitionObjects;
import com.jaakit.fingerscapture.tools.LogInformationData;
import com.jaakrecog.fingerprint.credentials.ValidateCredentialsImpl;

import java.io.IOException;

import io.sentry.Sentry;

@RequiresApi(api = Build.VERSION_CODES.R)
@CapacitorPlugin(name = "FingerPrint")
public class FingerPrintPlugin extends Plugin {
    Context context;
    private LogInformationData log;
    private ValidateCredentialsImpl validateCredentials;
     @PluginMethod
    public void callFingerAcquisition(PluginCall call) {
        try {
            String apiKey = call.getString("accessToken");
            Boolean dev =   call.getBoolean("is_production");
            log = new LogInformationData();
            validateCredentials= new ValidateCredentialsImpl(context);
            String jwToken=validateCredentials.validateCredentials(apiKey,dev);
            Intent callIntentActivity =
                    new Intent(getActivity(),
                    Class.forName("com.jaakit.fingerscapture.FingerActivity"));
            callIntentActivity.putExtra("jwtoken",jwToken);
            callIntentActivity.putExtra("isProduction",dev);
            startActivityForResult(call,callIntentActivity,"captureFingersResult");
        }catch (Exception ex){
            Log.e("Exception",ex.getMessage());
            Sentry.captureException(ex);
            call.reject("Error acquiring finger", ex);
        }
     }

    @ActivityCallback
    private void captureFingersResult(PluginCall call, ActivityResult result) throws IOException {
        if (call == null) {
            return;
        }
        JSObject ret = new JSObject();
        if ((result.getResultCode()== RESULT_OK)) {
            FingersAcquisitionObjects fingerAcquisitionObjects=
                    (FingersAcquisitionObjects) result.getData().
                            getSerializableExtra("fingersAcequisitionObjects");
            ret.put("eventIdLeft",fingerAcquisitionObjects.
                    getFingersAcequisitionObjectsLeftFinger().getFingerSuccessfullResponse().getEventId());
            ret.put("acquireLeft",fingerAcquisitionObjects.
                   getFingersAcequisitionObjectsLeftFinger().getFingerSuccessfullResponse().getAcquired());
            ret.put("eventIdRight",fingerAcquisitionObjects.
                    getFingersAcequisitionObjectsRigthFinger().getFingerSuccessfullResponse().getEventId());
            ret.put("acquireRight",fingerAcquisitionObjects.
                    getFingersAcequisitionObjectsRigthFinger().getFingerSuccessfullResponse().getAcquired());
            Gson gson = new Gson();
            String jsonResult=gson.toJson(fingerAcquisitionObjects);
            log.printoServer("Plugin",jsonResult);
         }else{
            ret.put("eventIdLeft",result.getData().getStringExtra("eventIdLeft"));
            ret.put("acquireLeft",false);
            ret.put("eventIdRigth", result.getData().getStringExtra("eventIdRigth"));
            ret.put("acquireRigth", false);
        }

        call.resolve(ret);
    }


}


