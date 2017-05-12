package com.example.zhouwenkang.rnandnative;

import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.view.View.OnClickListener;
import android.content.Intent;
import android.view.View;
import android.provider.Settings;
public class StartNativeActivity extends AppCompatActivity {
    private Button rn_button = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rn_button = (Button)findViewById(R.id.rn_button);
        rn_button.setOnClickListener(new MyButtonListener());
    }
    class MyButtonListener implements OnClickListener{
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.rn_button:
                    if (Build.VERSION.SDK_INT >= 23) {
                        if(!Settings.canDrawOverlays(StartNativeActivity.this)) {
                            Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION);
                            startActivity(intent);
                            return;
                        } else {
                            //绘ui代码, 这里说明6.0系统已经有权限了
                        }
                    } else {
                        //绘ui代码,这里android6.0以下的系统直接绘出即可
                    }
                    Intent intent = new Intent(StartNativeActivity.this,MyRNActivity.class);
                    startActivity(intent);
                    break;
            }
//            Intent intent = new Intent();
//            intent.setClass(MainActivity.this, MyRNActivity.class);
//            MainActivity.this.startActivity(intent);
        }
    }
}
