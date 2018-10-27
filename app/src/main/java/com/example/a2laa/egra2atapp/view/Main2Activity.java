package com.example.a2laa.egra2atapp.view;

import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.FrameLayout;

import com.example.a2laa.egra2atapp.R;
import com.example.a2laa.egra2atapp.app.App;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Main2Activity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        App.setContext(this);
        MinistriesFragment ministriesFragment = new MinistriesFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.replaceFrameLayout, ministriesFragment);
        transaction.commit();
    }

    @Override
    protected void onResume() {
        super.onResume();
        App.setContext(this);
    }
}
