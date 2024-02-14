package com.webviewgold.myappname;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.annotation.SuppressLint;
import android.os.Handler;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

@SuppressLint("CustomSplashScreen")
public class SplashScreenActivity extends AppCompatActivity{

    protected void onCreate(Bundle savedInstanceState) {
        androidx.core.splashscreen.SplashScreen.installSplashScreen(this);
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (Config.blackStatusBarText) {
                getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            }
        }
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.TIRAMISU && getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            if (BuildConfig.IS_DEBUG_MODE) Log.d("TAG", "onCreate: RUN");
            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_IMMERSIVE
                            | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            );
        }
        getWindow().setNavigationBarColor(getResources().getColor(R.color.colorWhite));
        getWindow().setStatusBarColor(getResources().getColor(R.color.colorWhite));

        Intent intent = new Intent(this, MainActivity.class);
        overridePendingTransition(0, 0);
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        if(getIntent().getExtras() != null) {
            String url = getIntent().getExtras().getString("url");
            if (url != null) {
                if (!url.equals("")) {
                    intent.putExtra("ONESIGNAL_URL", url);
                }
            }
        }
        startActivity(intent);
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.TIRAMISU) {
            //This leads to potential problems with Android 14 Auto-Rotation Landscape Mode, so only run until Android 13
            this.overridePendingTransition(0, 0);
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                public void run() {
                    finish();
                }
            }, 30);
        }

    }
}
