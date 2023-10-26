package com.example.movileappsproyect.util.storage;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

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
                "orbit, image, url FROM station;";
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
            resultado.add(station);
        }
        cursor.close();
        return resultado;
    }

    public void save(SpaceStationModel station) {
        String query = "INSERT INTO station (" +
                "name, founded, deorbited, description, orbit, image, url" +
                ") VALUES (?,?,?,?,?,?,?);";
        SQLiteDatabase sqLiteDatabase = helper.getWritableDatabase();
        sqLiteDatabase.execSQL(query, new String[]{
                station.getNombre(), station.getFounded(), station.getDeorbited(),
                station.getDescription(), station.getOrbit(), station.getImage(),
                station.getUrl()
        });
    }
    public void save(List<SpaceStationModel> stations) {
        for (SpaceStationModel station: stations) {
            save(station);
        }
    }
}
