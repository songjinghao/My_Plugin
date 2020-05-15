package com.songjinghao.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.songjinghao.plugincore.PluginActivity;

public class LoginActivity extends PluginActivity {

    @Override
    public void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_login);
        findViewById(R.id.jump).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(that, SecondActivity.class);
                startActivity(intent);
            }
        });
    }

    // 注意：xml android:onClick 属性定义的方法 无法找到！
    // 因为查找的是 壳 ProxyActivity 的该方法.
    // android:onClick="jumpSecondActivity"
    /*public void jumpSecondActivity(View view) {
        Intent intent = new Intent(that, SecondActivity.class);
        startActivity(intent);
    }*/
}
