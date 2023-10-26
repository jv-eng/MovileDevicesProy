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
import com.example.movileappsproyect.util.threads.RegisterThread;

import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity {

    private final Pattern emailPattern = Pattern.compile("^(.+)@(.+)$");
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //configurar botones
        Button login_btn = findViewById(R.id.register_btn);
        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView txt_user = findViewById(R.id.register_email);
                TextView txt_pass_1 = findViewById(R.id.register_pass_1);
                TextView txt_pass_2 = findViewById(R.id.register_pass_2);

                //comprobar formato del email
                if(!emailPattern.matcher(txt_user.getText().toString()).matches()){
                    Toast.makeText(RegisterActivity.this, "Please insert a valid Email", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!txt_pass_1.getText().toString().equals(txt_pass_2.getText().toString())) {
                    Toast.makeText(RegisterActivity.this, "Both passwords must be the same", Toast.LENGTH_SHORT).show();
                    return;
                }

                //lanzar thread
                UserModel user = new UserModel(txt_user.getText().toString(), txt_pass_1.getText().toString());
                Thread th = new Thread(new RegisterThread(RegisterActivity.this, user));
                th.start();
            }
        });
    }

    public void prepareUIForDownload() {
        progressDialog  = new ProgressDialog(this);
        progressDialog.setMessage("Creando usuario");
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    public void prepareUIAfterDownload() {
        progressDialog.dismiss();
    }

    public void checkResults(String res) {
        Intent i = new Intent(RegisterActivity.this, MenuActivity.class);
        startActivity(i);
    }
}