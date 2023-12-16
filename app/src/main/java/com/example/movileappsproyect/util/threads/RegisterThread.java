package com.example.movileappsproyect.util.threads;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.example.movileappsproyect.R;
import com.example.movileappsproyect.activities.RegisterActivity;
import com.example.movileappsproyect.model.UserModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class RegisterThread implements Runnable {
    private final String REGISTER_URL = "https://postman-echo.com/post";
    private Context ctx;
    private UserModel userModel;

    public RegisterThread(Context ctx, UserModel user) {this.ctx = ctx; this.userModel = user;}

    @Override
    public void run() {
        //preparar UI
        ((Activity)ctx).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ((RegisterActivity)ctx).prepareUIForDownload();
            }
        });

        //tarea
        String user = this.userModel.getEmail();
        String pass = this.userModel.getPassword();
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

        firebaseAuth.createUserWithEmailAndPassword(user, pass)
                .addOnCompleteListener(task -> {

                    if (task.isSuccessful()) {
                        // Registro exitoso
                        Log.e("hola","hola");
                        FirebaseUser user_firebase = firebaseAuth.getCurrentUser();
                        if (user_firebase != null) {
                            Toast.makeText(this.ctx, "Usuario registrado correctamente", Toast.LENGTH_SHORT).show();
                            ((Activity)ctx).runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    ((RegisterActivity)ctx).checkResults();
                                }
                            });
                        }
                    } else {
                        // Manejar errores en el registro
                        Exception exception = task.getException();
                        if (exception != null) {
                            // Mostrar mensaje de error o realizar acciones correspondientes
                            Toast.makeText(this.ctx, R.string.register_msg, Toast.LENGTH_SHORT).show();
                        }
                    }
                });

        //fin
        ((Activity)ctx).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ((RegisterActivity)ctx).prepareUIAfterDownload();
            }
        });
    }
}
