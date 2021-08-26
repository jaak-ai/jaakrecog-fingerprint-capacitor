package com.jaakrecog.fingerprint;

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
@CapacitorPlugin(name = "FingerPrint")
public class FingerPrintPlugin extends Plugin {

    private FingerPrint implementation = new FingerPrint();


    @PluginMethod
    public void callFingerAcequisition(PluginCall call) {
        try {
            String jwToken = call.getString("jwtoken");
            Intent callIntentActivity = new Intent(getActivity(),
                    Class.forName("com.jaakit.fingeracequisition.FingerActivity"));
            callIntentActivity.putExtra("jwtoken",jwToken);
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
        Uri imgURILeft = Uri.parse(result.getData().getStringExtra("fingerLeftBytes"));
        Uri imgURIRigth = Uri.parse(result.getData().getStringExtra("fingerRigthBytes"));
        JSObject ret = new JSObject();
        ret.put("fingerLeft", imgURILeft);
        ret.put("fingerRigth", imgURIRigth);
        call.resolve(ret);

    }
}


