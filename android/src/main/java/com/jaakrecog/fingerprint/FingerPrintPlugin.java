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
import com.jaakit.fingeracquisition.finger_request.FingersAcquisitionObjects;
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
    private ValidateCredentialsImpl validateCredentials;
     @PluginMethod
    public void callFingerAcquisition(PluginCall call) {
        try {
            String apiKey = call.getString("accessToken");
            Boolean dev =   call.getBoolean("is_production");

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
    private void captureFingersResult(PluginCall call, ActivityResult result) throws IOException {
        if (call == null) {
            return;
        }

        if ((result.getResultCode()== RESULT_OK)) {
            JSObject ret = new JSObject();
             FingersAcquisitionObjects fingerAcquisitionObjects=
                    (FingersAcquisitionObjects) result.getData().
                            getSerializableExtra("fingersAcequisitionObjects");

            Log.d("#######################","LEFT HAND");
            Log.d("EXTRACT getEventId",":"+fingerAcquisitionObjects.
                    getFingersAcequisitionObjectsLeftFinger().
                    getFingerSuccessfullResponse().getEventId());
            Log.d("EXTRACT getFingerSide",":"+fingerAcquisitionObjects.
                    getFingersAcequisitionObjectsLeftFinger().
                    getFingerSuccessfullResponse().getFingerSide());
            Log.d("EXTRACT getAcquired",":"+fingerAcquisitionObjects.
                    getFingersAcequisitionObjectsLeftFinger().
                    getFingerSuccessfullResponse().getAcquired());

            Log.d("#######################","RIGHT HAND");

            Log.d("EXTRACT getEventId",":"+fingerAcquisitionObjects.
                    getFingersAcequisitionObjectsRigthFinger()
                    .getFingerSuccessfullResponse().getEventId());
            Log.d("EXTRACT getFingerSide",":"+fingerAcquisitionObjects.
                    getFingersAcequisitionObjectsRigthFinger()
                    .getFingerSuccessfullResponse().getFingerSide());
            Log.d("EXTRACT getAcquired",":"+fingerAcquisitionObjects.
                    getFingersAcequisitionObjectsRigthFinger().
                    getFingerSuccessfullResponse().getAcquired());


            ret.put("eventIdLeft",fingerAcquisitionObjects.
                    getFingersAcequisitionObjectsLeftFinger().getFingerSuccessfullResponse().getEventId());
            ret.put("acquireLeft",fingerAcquisitionObjects.
                   getFingersAcequisitionObjectsLeftFinger().getFingerSuccessfullResponse().getAcquired());



            ret.put("eventIdRight",fingerAcquisitionObjects.
                    getFingersAcequisitionObjectsRigthFinger().getFingerSuccessfullResponse().getEventId());
            ret.put("acquireRight",fingerAcquisitionObjects.
                    getFingersAcequisitionObjectsRigthFinger().getFingerSuccessfullResponse().getAcquired());


             call.resolve(ret);


        }else{
            JSObject ret = new JSObject();
            ret.put("eventIdLeft",result.getData().getStringExtra("eventIdLeft"));
            ret.put("acquireLeft",false);
            ret.put("eventIdRigth", result.getData().getStringExtra("eventIdRigth"));
            ret.put("acquireRigth", false);

            call.resolve(ret);


        }


    }


}


