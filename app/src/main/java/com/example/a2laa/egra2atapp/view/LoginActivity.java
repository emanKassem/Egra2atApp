package com.example.a2laa.egra2atapp.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.a2laa.egra2atapp.R;
import com.example.a2laa.egra2atapp.app.App;
import com.example.a2laa.egra2atapp.utils.PrefUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.user)
    EditText user;
    @BindView(R.id.pass)
    EditText pass;
    @BindView(R.id.login)
    Button login;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        App.setContext(this);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login();
            }
        });
    }

    private void login() {
        if (!validate()) {
            onLoginFailed();
            return;
        }
        login.setEnabled(false);
        String userName = user.getText().toString();
        String password = pass.getText().toString();
        if (userName.equals("admin")&&password.equals("admin")){
            Toast.makeText(this, "تم تسجيل الدخول بنجاح", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(this, AdminActivity.class);
            PrefUtils.storeKeys(this, getString(R.string.login_key), "true");
            startActivity(intent);
            finish();
        }else {
            Toast.makeText(this, "فشل تسجيل الدخول", Toast.LENGTH_LONG).show();
        }
    }

    private void onLoginFailed() {
        login.setEnabled(true);
    }

    private boolean validate() {
        boolean valid = true;
        String name = user.getText().toString();
        String password = pass.getText().toString();
        if (name.isEmpty()) {
            user.setError("برجاء ادخال اسم المستخدم");
            valid = false;
        }
        if (password.isEmpty() || password.length() < 3) {
            pass.setError("برجاء ادخال رقم المرور");
            valid = false;
        }
        return valid;
    }
}
