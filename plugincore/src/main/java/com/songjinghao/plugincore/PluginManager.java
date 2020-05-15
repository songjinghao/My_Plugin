package com.songjinghao.plugincore;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.content.res.Resources;

import java.io.File;
import java.lang.reflect.Method;

import dalvik.system.DexClassLoader;

/**
 * Created by songjinghao on 2019/7/23.
 *
 * 动态加载第三方插件APK的资源对象、以及类加载器
 */
public class PluginManager {

    private static final PluginManager pluginManager = new PluginManager();

    private PluginManager() {}

    public static PluginManager getInstance() {
        return pluginManager;
    }

    private PluginApk mPluginApk;

    public PluginApk getPluginApk() {
        return mPluginApk;
    }

    private Context mContext;

    public void setContext(Context context) {
        mContext = context;
    }

    /**
     * 根据传进来的路径去动态加载第三方插件APK的资源对象以及类加载器
     * @param apkPath
     */
    public void loadPath(String apkPath) {
        // 通过包管理器获取传进来的这个路径下面的dex文件的包信息类
        PackageInfo packageInfo = mContext.getPackageManager().getPackageArchiveInfo(apkPath,
                PackageManager.GET_ACTIVITIES | PackageManager.GET_SERVICES);
        if (packageInfo == null) {
            return;
        }
        // 创建dexclassloader
        DexClassLoader classLoader = createDexClassLoader(apkPath);
        // 创建Resource
        AssetManager am = createAssetManager(apkPath);
        Resources resources = createResource(am);
        resources.newTheme();
        mPluginApk = new PluginApk(classLoader, resources, packageInfo, am);
    }

    private Resources createResource(AssetManager am) {
        Resources res = mContext.getResources();
        return new Resources(am, res.getDisplayMetrics(), res.getConfiguration());
    }

    private AssetManager createAssetManager(String apkPath) {
        try {
            AssetManager am = AssetManager.class.newInstance();
            Method method = AssetManager.class.getDeclaredMethod("addAssetPath", String.class);
            method.invoke(am, apkPath);
            return am;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private DexClassLoader createDexClassLoader(String apkPath) {
        // 获取当前应用的私有存储路径
        File file = mContext.getDir("dex", Context.MODE_PRIVATE);
        // 获取到了传进来的这个路径下面的dex文件的类加载器
        return new DexClassLoader(apkPath, file.getAbsolutePath(), null, mContext.getClassLoader());
    }

}
