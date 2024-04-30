package com.example.mygame2d;

import android.app.Activity;
import android.os.Bundle;
import android.view.WindowManager;
import android.view.Window;


public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    //set windows to fullscreen
        Window window = getWindow();
        window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
        );

        //set content to view
        setContentView(new Game(this));
    }
}