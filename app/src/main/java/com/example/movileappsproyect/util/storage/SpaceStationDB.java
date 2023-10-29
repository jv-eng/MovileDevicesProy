package com.example.movileappsproyect.util.storage;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.movileappsproyect.activities.MainActivity;
import com.example.movileappsproyect.model.SpaceStationModel;

import java.util.LinkedList;
import java.util.List;

public class SpaceStationDB {
    private SpaceStationHelper helper;
    public SpaceStationDB(SpaceStationHelper helper) {
        this.helper = helper;
    }

    public List<SpaceStationModel> getAll(){
        String query = "SELECT _id, name, founded, deorbited, description," +
                "orbit, image, url, store_url FROM station;";
        SQLiteDatabase sqLiteDatabase = helper.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(query, new String[]{});
        //recorrer elementos
        List<SpaceStationModel> resultado = new LinkedList<>();
        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            String name = cursor.getString(1);
            String founded = cursor.getString(2);
            String deorbited = cursor.getString(3);
            String description = cursor.getString(4);
            String orbit = cursor.getString(5);
            String image = cursor.getString(6);
            String url = cursor.getString(7);
            SpaceStationModel station = new SpaceStationModel(
                    id, name, founded, deorbited, description, orbit, image, url
            );
            station.setStoreUrl(cursor.getString(8));
            resultado.add(station);
        }
        cursor.close();
        return resultado;
    }

    public SpaceStationModel getStation(String name) {
        SpaceStationModel station = null;
        String query = "SELECT _id, name, founded, deorbited, description, orbit, " +
                "image, url, store_url FROM station WHERE name=?;";

        SQLiteDatabase sqLiteDatabase = helper.getReadableDatabase();
        Cursor c = sqLiteDatabase.rawQuery(query, new String[]{name});
        while (c.moveToNext()) {
            station = new SpaceStationModel(
                    c.getInt(0), c.getString(1), c.getString(2), c.getString(3),
                    c.getString(4), c.getString(5), c.getString(6), c.getString(7)
            );
            station.setStoreUrl(c.getString(8));
        }
        c.close();
        return station;
    }

    public void save(SpaceStationModel station) {
        String query = "INSERT INTO station (" +
                "name, founded, deorbited, description, orbit, image, url, store_url" +
                ") VALUES (?,?,?,?,?,?,?,?);";
        SQLiteDatabase sqLiteDatabase = helper.getWritableDatabase();
        sqLiteDatabase.execSQL(query, new String[]{
                station.getNombre(), station.getFounded(), station.getDeorbited(),
                station.getDescription(), station.getOrbit(), station.getImage(),
                station.getUrl(), station.getStoreUrl()
        });
    }

    public void save(List<SpaceStationModel> stations) {
        boolean flag = false;
        for (SpaceStationModel station: stations) {
            if (MainActivity.firstJob) {
                save(station);
                flag = true;
            } else {
                String query = "SELECT * FROM station WHERE name='?';";
                SQLiteDatabase sqLiteDatabase = helper.getWritableDatabase();
                Cursor c = sqLiteDatabase.rawQuery(query, new String[]{station.getNombre()});
                if (c.getCount() == 0) save(station);
                c.close();
            }
        }
        if (flag) MainActivity.firstJob = false;
    }
}
