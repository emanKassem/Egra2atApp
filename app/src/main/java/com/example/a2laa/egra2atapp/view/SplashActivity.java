package com.example.a2laa.egra2atapp.view;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.example.a2laa.egra2atapp.MainActivity;
import com.example.a2laa.egra2atapp.R;
import com.example.a2laa.egra2atapp.app.App;
import com.example.a2laa.egra2atapp.utils.PrefUtils;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        App.setContext(this);
        String isLogin = PrefUtils.getKeys(App.getContext(), getString(R.string.login_key));
        if(isLogin != null){
            Intent intent = new Intent(SplashActivity.this, Main2Activity.class);
            startActivity(intent);
            finish();
        }else {
            startActivity(new Intent(SplashActivity.this, LoginActivity.class));
            finish();
        }
    }
}
