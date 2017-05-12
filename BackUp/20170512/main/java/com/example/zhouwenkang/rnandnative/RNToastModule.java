package com.example.zhouwenkang.rnandnative;

import android.widget.Toast;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import java.util.HashMap;
import java.util.Map;

public class RNToastModule extends ReactContextBaseJavaModule {

    private static final String DURAION_SHORT_KEY = "SHORT";
    private static final String DURAION_LONG_KEY = "LONG";

    public RNToastModule(ReactApplicationContext reactApplicationContext) {
        super(reactApplicationContext);

    }
    /*
        这是定义JS端要调用的模块名，比如"RNToastAndroid"
        则到时候就用React.NativeModules.ToastAndroid调用这个模块
     */
    @Override
    public String getName() {
        return "RNToastAndroid";
    }

    /*
        一个可选的方法getContants返回了需要导出给JavaScript使用的常量。
        它并不一定需要实现，但在定义一些可以被JavaScript同步访问到的预定义的值时非常有用。
    */
    @Override
    public Map<String, Object> getConstants() {
        final Map<String, Object> constants = new HashMap<>();
        constants.put(DURAION_SHORT_KEY, Toast.LENGTH_SHORT);
        constants.put(DURAION_LONG_KEY, Toast.LENGTH_LONG);
        return constants;
    }
    /*
        这是用作导出一个方法给JavaScript使用，Java方法需要使用注解@ReactMethod
        所以到时候JS调用就是React.NativeModules.ToastAndroid.show("xx",100)
     */
    @ReactMethod
    public void show(String message, int duration) {
        Toast.makeText(getReactApplicationContext(), message, duration).show();

    }
}