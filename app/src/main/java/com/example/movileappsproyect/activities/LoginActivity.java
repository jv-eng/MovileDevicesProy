package com.example.movileappsproyect.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.movileappsproyect.R;
import com.example.movileappsproyect.model.UserModel;
import com.example.movileappsproyect.util.storage.PreferencesManage;
import com.example.movileappsproyect.util.threads.LoginThread;
import com.google.android.material.switchmaterial.SwitchMaterial;

import java.util.regex.Pattern;

public class LoginActivity extends AppCompatActivity {

    private final Pattern emailPattern = Pattern.compile("^(.+)@(.+)$");
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //configurar botones
        Button login_btn = findViewById(R.id.login_btn_login);
        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView txt_user = findViewById(R.id.login_et_username);
                TextView txt_pass = findViewById(R.id.login_et_password);

                //comprobar formato del email
                if(!emailPattern.matcher(txt_user.getText().toString()).matches()){
                    Toast.makeText(LoginActivity.this, "Please insert a valid Email", Toast.LENGTH_SHORT).show();
                    return;
                }

                //lanzar thread
                UserModel user = new UserModel(txt_user.getText().toString(), txt_pass.getText().toString());
                Thread th = new Thread(new LoginThread(LoginActivity.this, user));
                th.start();
            }
        });

        Button register_btn = findViewById(R.id.login_btn_register);
        register_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(i);
            }
        });
    }

    public void prepareUIForDownload() {
        progressDialog  = new ProgressDialog(this);
        progressDialog.setMessage("Comprobando usuario");
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    public void prepareUIAfterDownload() {
        progressDialog.dismiss();
    }

    public void checkResults(String res) {
        SwitchMaterial save_user_btn = findViewById(R.id.login_save_usr);

        //si está activo, guardar
        if (save_user_btn.isChecked()) {
            PreferencesManage.storeUser(this, ((TextView)findViewById(R.id.login_et_username)).getText().toString(),
                    ((TextView)findViewById(R.id.login_et_password)).getText().toString());
        }

        Intent i = new Intent(LoginActivity.this, MenuActivity.class);
        startActivity(i);
    }
}