package com.example.movileappsproyect.util.threads;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.example.movileappsproyect.activities.LoginActivity;
import com.example.movileappsproyect.activities.RegisterActivity;
import com.example.movileappsproyect.model.UserModel;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class LoginThread implements Runnable {
    private final String LOGIN_URL = "https://postman-echo.com/post";
    private Context ctx;
    private UserModel userModel;

    public LoginThread(Context ctx, UserModel user) {this.ctx = ctx; this.userModel = user;}

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
        URL url = null;
        try {
            //configurar
            url = new URL(LOGIN_URL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);
            connection.setRequestProperty("Content-Type", "application/json");
            String jsonData = "{\"usuario\": \"" + userModel.getEmail() + "\", \"contraseña\": \""
                    + userModel.getPassword() + "\"}";

            //enviar peticion
            OutputStream os = connection.getOutputStream();
            byte[] input = jsonData.getBytes(StandardCharsets.UTF_8);
            os.write(input, 0, input.length);

            // Leer la respuesta del servidor
            InputStream is = connection.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            StringBuilder response = new StringBuilder();
            String responseLine = null;
            while ((responseLine = br.readLine()) != null) {
                response.append(responseLine.trim());
            }

            //desconectar
            connection.disconnect();
            is.close();
            os.close();

            //tratar resultado
            ((Activity)ctx).runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    ((LoginActivity)ctx).checkResults(response.toString());
                }
            });
        } catch (MalformedURLException e) {
            Log.e("LoginThread","Error al crear la URL");
        } catch (IOException e) {
            Log.e("LoginThread","Error al realizar la petición");
        }

        //fin
        ((Activity)ctx).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ((LoginActivity)ctx).prepareUIAfterDownload();
            }
        });
    }
}
