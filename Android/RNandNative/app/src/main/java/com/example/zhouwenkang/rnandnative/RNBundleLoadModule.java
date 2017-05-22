package com.example.zhouwenkang.rnandnative;


import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.modules.core.DeviceEventManagerModule;
import com.facebook.react.modules.core.DeviceEventManagerModule;

public class RNBundleLoadModule extends ReactContextBaseJavaModule {

    private ReactApplicationContext reactApplicationContextAction;
    public RNBundleLoadModule(ReactApplicationContext reactApplicationContext) {
        super(reactApplicationContext);
        reactApplicationContextAction = reactApplicationContext;
    }
    @Override
    public String getName() {
        return "BundleLoad";
    }

    @ReactMethod
    public void goPage(int pageid) {
        System.out.println("########"+pageid+"########");
        // failedCallback.invoke();
        WritableMap params = Arguments.createMap();
        params.putInt("name", pageid);
        reactApplicationContextAction
                .getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)
                .emit("test", params);
    }
}