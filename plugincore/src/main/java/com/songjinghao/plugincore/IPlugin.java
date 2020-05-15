package com.songjinghao.plugincore;

import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;

/**
 * Created by songjinghao on 2019/5/28.
 *
 * 所有外置APK中的App页面必现要实现的接口类，这个类是规范所有外置APK的Activity的标准
 * 面向接口开发
 */
public interface IPlugin {

    /**
     * 注入上下文
     */
    void attach(Activity proxyActivity);

    void onCreate(Bundle saveInstanceState);

    void onStart();

    void onResume();

    void onPause();

    void onStop();

    void onDestroy();

    void onSaveInstanceState(Bundle outState);

    public boolean onTouchEvent(MotionEvent event);

    void onBackPressed();
}
