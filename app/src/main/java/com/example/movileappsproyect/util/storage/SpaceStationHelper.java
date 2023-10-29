package com.example.movileappsproyect.util.storage;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class SpaceStationHelper extends SQLiteOpenHelper {

    private static final String NOMBRE_MI_BBDD = "spacestation.db";
    private static final int version = 1;

    public SpaceStationHelper(@Nullable Context ctx) {
        super(ctx, NOMBRE_MI_BBDD, null, version);
    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE station(" +
                "_id INT, \n" +
                "name TEXT, \n" +
                "founded TEXT," +
                "deorbited TEXT," +
                "description TEXT," +
                "orbit TEXT," +
                "image TEXT," +
                "url TEXT," +
                "store_url TEXT" +
                ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
