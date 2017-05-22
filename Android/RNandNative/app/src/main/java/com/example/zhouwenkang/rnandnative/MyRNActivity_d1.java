package com.example.zhouwenkang.rnandnative;

import android.app.Activity;
import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.KeyEvent;

import com.facebook.react.ReactInstanceManagerBuilder;
import com.facebook.react.common.LifecycleState;
import com.facebook.react.ReactInstanceManager;
import com.facebook.react.ReactRootView;
import com.facebook.react.modules.core.DefaultHardwareBackBtnHandler;
import com.facebook.react.shell.MainReactPackage;
import android.widget.Toast;

import com.facebook.react.JSCConfig;
import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;


public class MyRNActivity_d1 extends Activity implements DefaultHardwareBackBtnHandler {
    private static final String TAG = "MyRNActivity";
    public static final String JS_BUNDLE_REMOTE_URL = "https://raw.githubusercontent.com/hubcarl/smart-react-native-app/debug/app/src/main/assets/index.android.bundle";
    public static final String JS_BUNDLE_LOCAL_FILE = "index.android.bundle";
    public static final String JS_BUNDLE_REACT_UPDATE_PATH = Environment.getExternalStorageDirectory().toString() + File.separator + "finalbundle";
    public static final String JS_BUNDLE_LOCAL_PATH = JS_BUNDLE_REACT_UPDATE_PATH + File.separator + JS_BUNDLE_LOCAL_FILE;

    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            "android.permission.READ_EXTERNAL_STORAGE",
            "android.permission.WRITE_EXTERNAL_STORAGE" };
    private ReactRootView mReactRootView;
    private ReactInstanceManager mReactInstanceManager;
    private CompleteReceiver mDownloadCompleteReceiver;
    private long mDownloadId;

    public static void startActivity(Context context){
        Intent intent = new Intent(context, MyRNActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        iniReactRootView(true);
        verifyStoragePermissions(MyRNActivity_d1.this);
        initDownloadManager();
        updateJSBundle(true);
        /*
        mReactRootView = new ReactRootView(this);
        mReactInstanceManager = ReactInstanceManager.builder()
                .setApplication(getApplication())
                //.setBundleAssetName("index.android.bundle")
                .setJSMainModuleName("index.android")
                .addPackage(new MainReactPackage())
                .addPackage(new RNJavaReactPackage())
                .setUseDeveloperSupport(true)
                .setInitialLifecycleState(LifecycleState.RESUMED)
                .build();
        mReactRootView.startReactApplication(mReactInstanceManager, "rnandnative", null);
        setContentView(mReactRootView);
         */
    }
    public static void verifyStoragePermissions(Activity activity) {

        try {
            //检测是否有写的权限
            int permission = ActivityCompat.checkSelfPermission(activity,
                    "android.permission.WRITE_EXTERNAL_STORAGE");
            if (permission != PackageManager.PERMISSION_GRANTED) {
                // 没有写的权限，去申请写的权限，会弹出对话框
                ActivityCompat.requestPermissions(activity, PERMISSIONS_STORAGE,REQUEST_EXTERNAL_STORAGE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void iniReactRootView(boolean isRelease) {
        ReactInstanceManagerBuilder builder = ReactInstanceManager.builder()
                .setApplication(getApplication())
                //.setBundleAssetName("index.android.bundle")
                .setJSMainModuleName("index.android")
                .addPackage(new MainReactPackage())
                .addPackage(new RNJavaReactPackage())
                .setUseDeveloperSupport(true)
                .setInitialLifecycleState(LifecycleState.RESUMED);
        File file = new File(JS_BUNDLE_LOCAL_PATH);
        if (isRelease && file != null && file.exists()) {
            builder.setJSBundleFile(JS_BUNDLE_LOCAL_PATH);
            Log.i("myrnactivity", "load bundle from local cache");
        } else {
            builder.setBundleAssetName("index.android.bundle");
            Log.i("myrnactivity", "load bundle from asset");
        }

        mReactRootView = new ReactRootView(this);
        mReactInstanceManager = builder.build();
        mReactRootView.startReactApplication(mReactInstanceManager, "rnandnative", null);
        setContentView(mReactRootView);
    }
    private void updateJSBundle(boolean isRelease) {

        final File file = new File(JS_BUNDLE_LOCAL_PATH);
        if (isRelease && file != null && file.exists()) {
            Log.i(TAG, "new bundle exists !");
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    file.delete();
                    Log.i(TAG, "js bundle file delete success");
                }
            }, 5000);
            return;
        }


        File rootDir = new File(JS_BUNDLE_REACT_UPDATE_PATH);
        if (rootDir != null && !rootDir.exists()) {
            rootDir.mkdir();
        }

        File res = new File(JS_BUNDLE_REACT_UPDATE_PATH + File.separator + "drawable-mdpi");
        if (res != null && !res.exists()) {
            res.mkdir();
        }

        //FileAssetUtils.copyAssets(this, "drawable-mdpi", JS_BUNDLE_REACT_UPDATE_PATH);


        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(JS_BUNDLE_REMOTE_URL));
        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI);
        request.setDestinationUri(Uri.parse("file://" + JS_BUNDLE_LOCAL_PATH));
        DownloadManager dm = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
        mDownloadId = dm.enqueue(request);

        Log.i(TAG, "start download remote js bundle file");
    }

    private void initDownloadManager() {
        mDownloadCompleteReceiver = new CompleteReceiver();
        registerReceiver(mDownloadCompleteReceiver, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
    }

    private class CompleteReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            long completeDownloadId = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);
            if (completeDownloadId == mDownloadId) {
                onJSBundleLoadedFromServer();
            }
        }
    }

    private void onJSBundleLoadedFromServer() {
        final File file = new File(JS_BUNDLE_LOCAL_PATH);
        if (file == null || !file.exists()) {
            Log.i(TAG, "js bundle file download error, check URL or network state");
            return;
        }

        Log.i(TAG, "js bundle file file success, reload js bundle");

        //Toast.makeText(MyRNActivity.this, "download bundle complete", Toast.LENGTH_SHORT).show();
        try {

            Class<?> RIManagerClazz = mReactInstanceManager.getClass();

            Field f = RIManagerClazz.getDeclaredField("mJSCConfig");
            f.setAccessible(true);
            JSCConfig jscConfig = (JSCConfig)f.get(mReactInstanceManager);

            Method method = RIManagerClazz.getDeclaredMethod("recreateReactContextInBackground",
                    com.facebook.react.cxxbridge.JavaScriptExecutor.Factory.class,
                    com.facebook.react.cxxbridge.JSBundleLoader.class);
            method.setAccessible(true);
            method.invoke(mReactInstanceManager,
                    new com.facebook.react.cxxbridge.JSCJavaScriptExecutor.Factory(jscConfig.getConfigMap()),
                    com.facebook.react.cxxbridge.JSBundleLoader.createFileLoader(JS_BUNDLE_LOCAL_PATH));
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e){
            e.printStackTrace();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        if(mReactInstanceManager != null){
            mReactInstanceManager.onHostResume(this, this);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

        if(mReactInstanceManager != null){
            mReactInstanceManager.onHostPause(this);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mDownloadCompleteReceiver);
        if(mReactInstanceManager != null){
            mReactInstanceManager.onHostDestroy();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        if(mReactInstanceManager != null){
            mReactInstanceManager.onBackPressed();
        }else{
            super.onBackPressed();
        }
    }

    @Override
    public void invokeDefaultOnBackPressed() {
        super.onBackPressed();
    }
    //我们需要改动一下开发者菜单。
    //默认情况下，任何开发者菜单都可以通过摇晃或者设备类触发，不过这对模拟器不是很有用。
    //所以我们让它在按下Menu键的时候可以显示
    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_MENU && mReactInstanceManager != null) {
            mReactInstanceManager.showDevOptionsDialog();
            return true;
        }
        return super.onKeyUp(keyCode, event);
    }
}