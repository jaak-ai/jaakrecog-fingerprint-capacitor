package com.jaakrecog.fingerprint;

import android.content.Intent;
import android.util.Log;

import androidx.activity.result.ActivityResult;

import com.getcapacitor.JSObject;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.ActivityCallback;
import com.getcapacitor.annotation.CapacitorPlugin;

@CapacitorPlugin(name = "FingerPrint")
public class FingerPrintPlugin extends Plugin {

    private FingerPrint implementation = new FingerPrint();


    @PluginMethod
    public void callFingerAcequisition(PluginCall call) {
        try {
            Intent callIntentActivity = new Intent(getActivity(),
                    Class.forName("com.jaakit.fingeracequisition.FingerActivity"));
            startActivityForResult(call,callIntentActivity,"finger_acequisition");
        }catch (Exception ex){
            Log.e("Exception",ex.getMessage());
        }
     }

    @ActivityCallback
    private void captureFingersResult(PluginCall call, ActivityResult result) {
        if (call == null) {
            return;
        }
        JSObject ret = new JSObject();
        ret.put("fingerLeft", result.getData().getStringExtra("fingerLeft"));
        ret.put("fingerRigth", result.getData().getStringExtra("fingerRigth"));
        call.resolve(ret);

    }
}


