package com.songjinghao.plugincore;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.res.AssetManager;
import android.content.res.Resources;

import dalvik.system.DexClassLoader;

/**
 * Created by songjinghao on 2019/5/28.
 *
 */
public class PluginApk {

    // 第三方插件APK的类加载器
    public DexClassLoader mClassLoader;
    // 第三方插件APK的资源对象
    public Resources mResources;
    // 插件APK的包信息类 因为我们要根据包信息类去获取Activity的名字
    public PackageInfo mPackageInfo;

    public AssetManager mAssetManager;

    public PluginApk(DexClassLoader classLoader, Resources resources, PackageInfo packageInfo, AssetManager assetManager) {
        mClassLoader = classLoader;
        mResources = resources;
        mPackageInfo = packageInfo;
        mAssetManager = assetManager;
    }
}
