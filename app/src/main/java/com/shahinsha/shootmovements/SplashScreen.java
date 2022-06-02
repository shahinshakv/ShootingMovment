package com.shahinsha.shootmovements;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.TintableBackgroundView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.shahinsha.shootmovements.utility.TinyDB;

public class SplashScreen extends AppCompatActivity {
    private final int SPLASH_DISPLAY_LENGTH = 3000;
    TinyDB tinyDB;
    boolean isLogin =false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        tinyDB = new TinyDB(this);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                isLogin=tinyDB.getBoolean("isLogin");
                if (isLogin){
                    final Intent mainIntent = new Intent(SplashScreen.this, HomeActivity.class);
                    startActivity(mainIntent);
                    finish();
                }else {
                    final Intent mainIntent = new Intent(SplashScreen.this, SignInPage.class);
                    startActivity(mainIntent);
                    finish();
                }
            }

        }, SPLASH_DISPLAY_LENGTH);

    }
}