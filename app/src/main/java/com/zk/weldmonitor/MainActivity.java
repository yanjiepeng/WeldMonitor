package com.zk.weldmonitor;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }



   /*
    初始化actionbar
    */
    private void initActionBar() {

        ActionBar actionBar = getSupportActionBar();
        actionBar.setCustomView(R.layout.titlebar);
        actionBar.setIcon(R.drawable.mjlogo);
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM | ActionBar.DISPLAY_SHOW_HOME);

    }
}
