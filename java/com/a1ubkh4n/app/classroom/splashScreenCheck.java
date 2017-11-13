package com.a1ubkh4n.app.classroom;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.a1ubkh4n.app.classroom.splash.SplashActivity;

/**
 * Created by Md.Aiub Khan on 06-Feb-17.
 */

public class splashScreenCheck extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences sharedPreferences = getSharedPreferences("prefs", 0);
        if(sharedPreferences.getBoolean("firstRun", false)) {
            startActivity(new Intent(this, MainActivity.class));
            finish();
            return;
        }
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("firstRun", true);
        editor.commit();
        startActivity(new Intent(this, SplashActivity.class));
        finish();
    }
}
