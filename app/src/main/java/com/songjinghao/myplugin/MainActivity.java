package com.songjinghao.myplugin;

import android.Manifest;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.songjinghao.plugincore.PluginManager;
import com.songjinghao.plugincore.ProxyActivity;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private String apkName = "login_apk.apk";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        PluginManager.getInstance().setContext(this);
    }

    public void loadApk(View view) {
        loadApk();
    }

    private void loadApk() {
        // 获取权限
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 100);

        // 动态加载第三方插件APK
        PluginManager.getInstance().loadPath(Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + apkName);
    }

    public void loadPluginRes(View view) {
        try {
            // 动态加载插件资源
            // 宿主 App 资源
            int identifier = getResources().getIdentifier("ic_launcher", "mipmap", getPackageName());
            int carId = getResources().getIdentifier("car", "drawable", getPackageName());
            int carId1 = getResources().getIdentifier("car", "drawable", PluginManager.getInstance().getPluginApk().mPackageInfo.packageName);
            // 插件 App 资源
            int carId_plugin = PluginManager.getInstance().getPluginApk().mResources.getIdentifier("car", "drawable", PluginManager.getInstance().getPluginApk().mPackageInfo.packageName);

            Log.d(TAG, "identifier: " + Integer.toHexString(identifier));
            Log.d(TAG, "carId: " + Integer.toHexString(carId));
            Log.d(TAG, "carId1: " + Integer.toHexString(carId1));
            Log.d(TAG, "carId_plugin: " + Integer.toHexString(carId_plugin));

            ImageView imageView = findViewById(R.id.iv);
            imageView.setImageDrawable(PluginManager.getInstance().getPluginApk().mResources.getDrawable(carId_plugin));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void jumpToPluginActivity(View view) {
        try {
            Intent intent = new Intent(this, ProxyActivity.class);
            // 拿到插件APK的第一个Activity的名字(manifest文件中第一个activity)
            String activityName = PluginManager.getInstance().getPluginApk().mPackageInfo.activities[0].name;
            intent.putExtra("activityName", activityName);
            startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
