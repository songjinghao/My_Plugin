package com.songjinghao.plugincore;

import android.app.Activity;
import android.content.Intent;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.MotionEvent;

/**
 * Created by songjinghao on 2019/5/28.
 *
 * 代理Activity  壳，管理插件Activity的生命周期
 * 当我们跳转到第三方插件APK里面的Activity的时候会跳转到这个壳里面
 * 然后再经过这个代理Activity去动态加载第三方插件APK的Activity的class对象
 * 然后去调用这个Activity的相对应的生命周期的方法
 */
public class ProxyActivity extends Activity {

    private String activityName;
    private IPlugin mIPlugin;
    private PluginApk mPluginApk;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 真正的目的地的Activity的名字
        activityName = getIntent().getStringExtra("activityName");
        mPluginApk = PluginManager.getInstance().getPluginApk();
        launchPluginActivity();
    }

    private void launchPluginActivity() {
        try {
            // 加载第三方插件APK中的Activity的类对象
            Class<?> clazz = mPluginApk.mClassLoader.loadClass(activityName);
            // 实例化对象
            Object activity = clazz.newInstance();
            // 判断这个对象是否按照我们的标准
            if (activity instanceof IPlugin) {
                mIPlugin = (IPlugin) activity;
                mIPlugin.attach(this);
                Bundle bundle = new Bundle();
                mIPlugin.onCreate(bundle);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 特别注意

    @Override
    public Resources getResources() {
        return mPluginApk != null ? mPluginApk.mResources : super.getResources();
    }

    @Override
    public AssetManager getAssets() {
        return mPluginApk != null ? mPluginApk.mAssetManager : super.getAssets();
    }

    @Override
    public ClassLoader getClassLoader() {
        return mPluginApk != null ? mPluginApk.mClassLoader : super.getClassLoader();
    }

    @Override
    public void startActivity(Intent intent) {
        // SecondActivity.class
        String activityName = intent.getStringExtra("activityName");
        // 插件APK里面跳转Activity时，都是通过ProxyActivity进行跳转的
        Intent intent1 = new Intent(this, ProxyActivity.class);
        intent1.putExtra("activityName", activityName);
        super.startActivity(intent1);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mIPlugin.onStart();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mIPlugin.onDestroy();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mIPlugin.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mIPlugin.onResume();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mIPlugin.onStop();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mIPlugin.onSaveInstanceState(outState);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mIPlugin.onTouchEvent(event);
        return super.onTouchEvent(event);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        mIPlugin.onBackPressed();
    }
}
