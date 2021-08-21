package com.jaakrecog.fingerprint;

import android.content.Intent;

import com.getcapacitor.JSObject;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.CapacitorPlugin;
import com.jaakit.fingeracequisition.MainActivity;

@CapacitorPlugin(name = "FingerPrint")
public class FingerPrintPlugin extends Plugin {

    private FingerPrint implementation = new FingerPrint();


    @PluginMethod
    public void callFingerAcequisition(PluginCall call) {
        Intent intent = new Intent("com.jaakit.fingeracequisition.MainActivity");
        intent.putExtra("token","ae00738e523998b0c782b06c2c2314675ff01fe1710b006dd3f3f22b6e4ca7388445c16d3b837b7ad89b0ab1ee10ec336def3780d916f6bc103dc380ec0d4df7");
        startActivityForResult(call,intent,"intent_camera");

        String valueFingerLeft = "ajdkjskldjaskljklsad";
        String valueFingerRigth = "ajdkjskldjaskljklsad";

        JSObject ret = new JSObject();
        ret.put("fingerLeft", implementation.callFingerAcequisition(valueFingerLeft));
        ret.put("fingerRigth", implementation.callFingerAcequisition(valueFingerRigth));

        call.resolve(ret);
    }
}
