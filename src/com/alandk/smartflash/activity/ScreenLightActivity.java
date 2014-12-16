package com.alandk.smartflash.activity;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;

public class ScreenLightActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.screen_light);

        LinearLayout linearlayout = (LinearLayout) findViewById(R.id.screenlightlayout);
        int color = getIntent().getExtras().getInt("color");
        linearlayout.setBackgroundColor(color);
    }
}
