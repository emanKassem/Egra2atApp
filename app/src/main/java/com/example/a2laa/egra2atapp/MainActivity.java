package com.example.a2laa.egra2atapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;
import android.widget.TextView;

import com.example.a2laa.egra2atapp.app.App;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.Egra2atRecyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.addEgraa)
    Button addEgraa;
    @BindView(R.id.NoEgra2atTV)
    TextView NoEgra2atTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        App.setContext(this);

    }
}
