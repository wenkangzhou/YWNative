
package com.example.zhouwenkang.rnandnative;

import android.app.Activity;
import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.KeyEvent;

import com.facebook.react.JSCConfig;
import com.facebook.react.ReactApplication;
import com.facebook.react.common.LifecycleState;
import com.facebook.react.ReactInstanceManager;
import com.facebook.react.ReactRootView;
import com.facebook.react.modules.core.DefaultHardwareBackBtnHandler;
import com.facebook.react.shell.MainReactPackage;
import com.facebook.react.ReactInstanceManagerBuilder;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;


public class MyRNActivity extends Activity implements DefaultHardwareBackBtnHandler {

    private long mDownloadId;

    private ReactRootView mReactRootView;
    private ReactInstanceManager mReactInstanceManager;

    private DownloadManager dm;

    public static void startActivity(Context context){
        Intent intent = new Intent(context, MyRNActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mReactRootView = new ReactRootView(this);
        ReactInstanceManagerBuilder builder = ReactInstanceManager.builder()
                .setApplication(getApplication())
                //.setBundleAssetName("index.android.bundle")
                .setJSMainModuleName("index.android")
                .addPackage(new MainReactPackage())
                .addPackage(new RNJavaReactPackage())
                .setUseDeveloperSupport(true)
                .setInitialLifecycleState(LifecycleState.RESUMED);

        File bundleFile = new File(getExternalCacheDir(),"finalbundle/index.android.bundle");
        if(bundleFile.exists()){
            //这里不生效的原因估计是开发环境用本地localhost的bundle优先级高
            System.out.println("+++++MyRNActivity+++++" + bundleFile.getAbsolutePath());
            builder.setJSBundleFile(bundleFile.getAbsolutePath());
        } else {
            System.out.println("+++++MyRNActivity+++++" + "Asset");
            builder.setBundleAssetName("index.android.bundle");
        }
        mReactInstanceManager = builder.build();
        mReactRootView.startReactApplication(mReactInstanceManager, "rnandnative", null);
        setContentView(mReactRootView);
        //updateJsBundle();
    }

    private void updateJsBundle(){
        if(BuildConfig.BUNDLE_VERSION == "1.0.0"){//TODO:这里需要发起异步获取服务端的版本号，然后和打包版本号比对

            Context context=MyRNActivity.this;//首先，在Activity里获取context
            File file=context.getFilesDir();
            String path=file.getAbsolutePath();
            System.out.println(path);
            System.out.println(Environment.getExternalStorageDirectory().toString());
            System.out.println(getExternalCacheDir());
            File reactDir = new File(getExternalCacheDir(),"finalbundle");
            System.out.println(reactDir.getAbsolutePath());
            if(!reactDir.exists()){
                reactDir.mkdirs();
            }
            File reactZipDir = new File(getExternalCacheDir(),"finalbundle/finalbundle.zip");
            if(reactZipDir.exists()){
                deleteDir(reactZipDir);
            }
            System.out.println("file://"+new File(getExternalCacheDir(),"finalbundle/finalbundle.zip").getAbsolutePath());
            DownloadManager.Request request = new DownloadManager.Request(Uri.parse("https://raw.githubusercontent.com/wenkangzhou/YWNative/master/HotUpdateRes/finalbundle.zip"));
            //request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI);
            request.setDestinationUri(Uri.parse("file://"+new File(getExternalCacheDir(),"finalbundle/finalbundle.zip").getAbsolutePath()));
            //在通知栏中显示，默认就是显示的
            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE);
            request.setVisibleInDownloadsUi(true);
            dm = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
            mDownloadId = dm.enqueue(request);

            //注册广播接收者，监听下载状态
            registerReceiver(receiver,
                    new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
        }
    }
    //广播接受者，接收下载状态
    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            checkDownloadStatus();//检查下载状态
        }
    };
    //检查下载状态
    private void checkDownloadStatus() {
        System.out.println("检查下载状态");
        DownloadManager.Query query = new DownloadManager.Query();
        query.setFilterById(mDownloadId);//筛选下载任务，传入任务ID，可变参数
        Cursor c = dm.query(query);
        if (c.moveToFirst()) {
            int status = c.getInt(c.getColumnIndex(DownloadManager.COLUMN_STATUS));
            switch (status) {
                case DownloadManager.STATUS_PAUSED:
                    Log.i("heeeeeeee",">>>下载暂停");
                    System.out.println("下载暂停");
                case DownloadManager.STATUS_PENDING:
                    Log.i("heeeeeeee",">>>下载延迟");
                    System.out.println("下载延迟");
                case DownloadManager.STATUS_RUNNING:
                    Log.i("heeeeeeee",">>>正在下载");
                    System.out.println("正在下载");
                    break;
                case DownloadManager.STATUS_SUCCESSFUL:
                    Log.i("heeeeeeee",">>>下载完成");
                    //下载完成
                    replaceBundle();
                    break;
                case DownloadManager.STATUS_FAILED:
                    Log.i("heeeeeeee",">>>下载失败");
                    System.out.println("下载失败");
                    break;
            }
        }
    }
    protected void  replaceBundle() {
        System.out.println("下载成功");
        File reactDir = new File(getExternalCacheDir(),"finalbundle");
        System.out.println(reactDir.getAbsolutePath());
        if(!reactDir.exists()){
            System.out.println("创建");
            reactDir.mkdirs();
        }
        final File saveFile = new File(reactDir,"finalbundle.zip");
        boolean result = unzip(saveFile);
        if(result){//解压成功后保存当前最新bundle的版本
            if(true) {//立即加载bundle
                System.out.println("加载bundle");
//                ((ReactApplication) getReactApplicationContext()).getReactNativeHost().clear();
//                getCurrentActivity().recreate();
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
                            com.facebook.react.cxxbridge.JSBundleLoader.createFileLoader(new File(getExternalCacheDir()+"/finalbundle","index.android.bundle").getAbsolutePath()));
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
        }else{//解压失败应该删除掉有问题的文件，防止RN加载错误的bundle文件
            System.out.println("解压失败");
            File reactbundleDir = new File(getExternalCacheDir(),"finalbundle");
            deleteDir(reactbundleDir);
        }
    }
    private static boolean unzip(File zipFile){
        if(zipFile != null && zipFile.exists()){
            ZipInputStream inZip = null;
            try {
                inZip = new ZipInputStream(new FileInputStream(zipFile));
                ZipEntry zipEntry;
                String entryName;
                File dir = zipFile.getParentFile();
                while ((zipEntry = inZip.getNextEntry()) != null) {
                    entryName = zipEntry.getName();
                    if (zipEntry.isDirectory()) {
                        File folder = new File(dir,entryName);
                        folder.mkdirs();
                    } else {
                        File file = new File(dir,entryName);
                        file.createNewFile();

                        FileOutputStream fos = new FileOutputStream(file);
                        int len;
                        byte[] buffer = new byte[1024];
                        while ((len = inZip.read(buffer)) != -1) {
                            fos.write(buffer, 0, len);
                            fos.flush();
                        }
                        fos.close();
                    }
                }
                //("+++++解压完成+++++");
                return true;
            } catch (IOException e) {
                e.printStackTrace();
                //("+++++解压失败+++++");
                return false;
            }finally {
                try {
                    if(inZip != null){
                        inZip.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }else {
            return false;
        }
    }

    private static void deleteDir(File dir){
        if (dir==null||!dir.exists()) {
            return;
        } else {
            if (dir.isFile()) {
                dir.delete();
                return;
            }
        }
        if (dir.isDirectory()) {
            File[] childFile = dir.listFiles();
            if (childFile == null || childFile.length == 0) {
                dir.delete();
                return;
            }
            for (File f : childFile) {
                deleteDir(f);
            }
            dir.delete();
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
        //unregisterReceiver(receiver); //和updateJsBundle()同步开关
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