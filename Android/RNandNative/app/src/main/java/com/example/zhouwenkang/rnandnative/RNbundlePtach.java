package com.example.zhouwenkang.rnandnative;


import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.cxxbridge.JSBundleLoader;
import com.facebook.react.cxxbridge.JSCJavaScriptExecutor;
import com.facebook.react.cxxbridge.JavaScriptExecutor;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;
import android.app.Activity;
import android.widget.Toast;

public class RNbundlePtach extends ReactContextBaseJavaModule {



    public RNbundlePtach(ReactApplicationContext reactApplicationContext) {
        super(reactApplicationContext);
    }
    @Override
    public String getName() {
        return "BundlePtach";
    }

    @ReactMethod
    public void doit() {
        Toast.makeText(getReactApplicationContext(), "暂不开放",Toast.LENGTH_SHORT).show();
        System.out.println("+++++BundlePtach+++++");
    }


}