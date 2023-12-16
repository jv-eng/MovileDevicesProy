package com.example.movileappsproyect.util.threads;

import android.app.Activity;
import android.content.Context;
import android.widget.Toast;

import com.example.movileappsproyect.R;
import com.example.movileappsproyect.activities.LoginActivity;
import com.example.movileappsproyect.model.UserModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginThread implements Runnable {
    private final String LOGIN_URL = "https://postman-echo.com/post";
    private Context ctx;
    private UserModel userModel;

    public LoginThread(Context ctx, UserModel user) {
        this.ctx = ctx;
        this.userModel = user;
    }

    @Override
    public void run() {
        //preparar UI
        ((Activity)ctx).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ((LoginActivity)ctx).prepareUIForDownload();
            }
        });

        //tarea
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        String email = this.userModel.getEmail();
        String password = this.userModel.getPassword();

        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        // Inicio de sesión exitoso
                        FirebaseUser user = firebaseAuth.getCurrentUser();
                        if (user != null) {
                            // El usuario ha iniciado sesión exitosamente
                            ((Activity)ctx).runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    ((LoginActivity)ctx).checkResults();
                                }
                            });
                        }
                    } else {
                        // Manejar errores en el inicio de sesión
                        Exception exception = task.getException();
                        if (exception != null) {
                            // Mostrar mensaje de error o realizar acciones correspondientes
                            Toast.makeText(this.ctx, R.string.login_msg, Toast.LENGTH_SHORT);
                        }
                    }
                });


        //fin
        ((Activity)ctx).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ((LoginActivity)ctx).prepareUIAfterDownload();
            }
        });
    }
}
