package com.example.zhouwenkang.rnandnative;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;

import com.facebook.react.JSCConfig;
import com.facebook.react.ReactApplication;
import com.facebook.react.ReactInstanceManager;
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

public class RNUpdateBundleModule extends ReactContextBaseJavaModule {

    private SharedPreferences mSP;
    private static final String BUNDLE_VERSION = "CurrentBundleVersion";
    private DownloadManager dm;
    private long mDownloadId;
    private ReactInstanceManager mReactInstanceManager;
    Activity myActivity;

    public RNUpdateBundleModule(ReactApplicationContext reactApplicationContext) {
        super(reactApplicationContext);
        mSP = reactApplicationContext.getSharedPreferences("react_bundle", Context.MODE_PRIVATE);
    }
    @Override
    public String getName() {
        return "updateBundle";
    }

    /*
        一个可选的方法getContants返回了需要导出给JavaScript使用的常量。
        它并不一定需要实现，但在定义一些可以被JavaScript同步访问到的预定义的值时非常有用。
    */
    @Override
    public Map<String, Object> getConstants() {
        final Map<String, Object> constants = new HashMap<>();
        //跟随apk一起打包的bundle基础版本号,也就是assets下的bundle版本号
        String bundleVersion = BuildConfig.BUNDLE_VERSION;
        //bundle更新后的当前版本号
        String cacheBundleVersion = mSP.getString(BUNDLE_VERSION,"");
        System.out.println("+++++check version+++++-" + cacheBundleVersion);
        if(!TextUtils.isEmpty(cacheBundleVersion)){
            System.out.println("-+++++check version+++++-" + cacheBundleVersion);
            bundleVersion = cacheBundleVersion;
        }
        System.out.println("-+++++check version+++++-" + bundleVersion);
        constants.put(BUNDLE_VERSION,bundleVersion);
        return constants;
    }
    @ReactMethod
    public void check(String currVersion) {
        System.out.println("+++++check version+++++" + currVersion);
        System.out.println("+++++check version+++++" + BuildConfig.BUNDLE_VERSION);
        System.out.println("+++++check version+++++" + mSP.getString(BUNDLE_VERSION,""));
        String jsBundleVersion = BuildConfig.BUNDLE_VERSION;
        String cacheBundleVersion = mSP.getString(BUNDLE_VERSION,"");
        if(!TextUtils.isEmpty(cacheBundleVersion)){
            jsBundleVersion = cacheBundleVersion;
        }
        //测试时先隐藏
//        if(jsBundleVersion.equals("1.0.0")){//和服务下发的比对
//            System.out.println("已经是最新版本");
//            return;
//        }
        updateJsBundle();
    }
    private void updateJsBundle(){

        Context context= getReactApplicationContext();
        File file=context.getFilesDir();
        String path=file.getAbsolutePath();
        System.out.println(path);
        System.out.println(Environment.getExternalStorageDirectory().toString());
        System.out.println(getReactApplicationContext().getExternalCacheDir());
        File reactDir = new File(getReactApplicationContext().getExternalCacheDir(),"finalbundle");
        System.out.println(reactDir.getAbsolutePath());
        if(!reactDir.exists()){
            reactDir.mkdirs();
        }
        File reactZipDir = new File(getReactApplicationContext().getExternalCacheDir(),"finalbundle/finalbundle.zip");
        if(reactZipDir.exists()){
            deleteDir(reactZipDir);
        }
        System.out.println("file://"+new File(getReactApplicationContext().getExternalCacheDir(),"finalbundle/finalbundle.zip").getAbsolutePath());
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse("https://raw.githubusercontent.com/wenkangzhou/YWNative/master/HotUpdateRes/finalbundle.zip"));
        //request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI);
        request.setDestinationUri(Uri.parse("file://"+new File(getReactApplicationContext().getExternalCacheDir(),"finalbundle/finalbundle.zip").getAbsolutePath()));
        //在通知栏中显示，默认就是显示的
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE);
        request.setVisibleInDownloadsUi(true);
        myActivity = getCurrentActivity();
        dm = (DownloadManager) myActivity.getSystemService(Context.DOWNLOAD_SERVICE);
        mDownloadId = dm.enqueue(request);

        //注册广播接收者，监听下载状态
        myActivity.registerReceiver(receiver,
                new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
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
        File reactDir = new File(getReactApplicationContext().getExternalCacheDir(),"finalbundle");
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
                mSP.edit().putString(BUNDLE_VERSION,"1.0.2").apply();
                Activity currActivity = getCurrentActivity();
                //两个办法都可以 只是在开启本地8081时 优先级低 所以会一直刷不出新的
                if(currActivity != null){
                    ((ReactApplication) currActivity.getApplication()).getReactNativeHost().clear();
                    currActivity.unregisterReceiver(receiver);
                    currActivity.recreate();
                }
//                Toast.makeText(getCurrentActivity(), "Downloading complete", Toast.LENGTH_SHORT).show()
//                try {
//                    ReactApplication application = (ReactApplication) getCurrentActivity().getApplication();
//                    mReactInstanceManager = application.getReactNativeHost().getReactInstanceManager();
//                    //builder.setJSBundleFile(bundleFile.getAbsolutePath());
//                    Class<?> RIManagerClazz = application.getReactNativeHost().getReactInstanceManager().getClass();
//                    Field f = RIManagerClazz.getDeclaredField("mJSCConfig");
//                    f.setAccessible(true);
//                    JSCConfig jscConfig = (JSCConfig)f.get(mReactInstanceManager);
//                    Method method = RIManagerClazz.getDeclaredMethod("recreateReactContextInBackground",
//                            JavaScriptExecutor.Factory.class, JSBundleLoader.class);
//                    method.setAccessible(true);
//                    method.invoke(application.getReactNativeHost().getReactInstanceManager(),
//                            new JSCJavaScriptExecutor.Factory(jscConfig.getConfigMap()),
//                            JSBundleLoader.createFileLoader(new File(getReactApplicationContext().getExternalCacheDir()+"/finalbundle","index.android.bundle").getAbsolutePath()));
//                } catch (NoSuchMethodException e) {
//                    e.printStackTrace();
//                } catch (IllegalAccessException e) {
//                    e.printStackTrace();
//                } catch (InvocationTargetException e) {
//                    e.printStackTrace();
//                } catch (IllegalArgumentException e) {
//                    e.printStackTrace();
//                } catch (NoSuchFieldException e){
//                    e.printStackTrace();
//                }
            }
        }else{//解压失败应该删除掉有问题的文件，防止RN加载错误的bundle文件
            System.out.println("解压失败");
            File reactbundleDir = new File(getReactApplicationContext().getExternalCacheDir(),"finalbundle");
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
}