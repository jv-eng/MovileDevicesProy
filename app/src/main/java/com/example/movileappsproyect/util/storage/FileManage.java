package com.example.movileappsproyect.util.storage;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;

public class FileManage {
    public static void saveImg(String fileName, Context ctx, Bitmap img) {
        File file = new File(ctx.getFilesDir(), fileName);
        try (FileOutputStream fos = ctx.openFileOutput(
                fileName, Context.MODE_PRIVATE)) {
            fos.write(img.getRowBytes());
            Log.i("MainActivity","fichero guardado: " + fileName);
        } catch (FileNotFoundException e) {
            Log.e("FileNotFound","no se ha encontrado fichero");
        } catch (IOException e) {
            Log.e("IOException","error al guardar fichero");
        }
    }

    public static Bitmap getImg(String fileName, Context ctx) {
        File file = new File(ctx.getFilesDir(), fileName);
        Bitmap myBitmap = null;
        try (FileInputStream inputStream = ctx.openFileInput(fileName)) {
            if(file.exists()){
                myBitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
            } else {
                Log.e("FileManage.getImg","No existe imagen");
            }
        } catch (FileNotFoundException e) {
            Log.e("FileNotFound","no se ha encontrado fichero");
        } catch (IOException e) {
            Log.e("IOException","error al guardar fichero");
        }
        return myBitmap;
    }
}
