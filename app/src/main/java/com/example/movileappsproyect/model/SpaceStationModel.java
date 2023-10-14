package com.example.movileappsproyect.model;

import android.graphics.Bitmap;

import com.google.gson.annotations.SerializedName;

public class SpaceStationModel {
    @SerializedName("id")
    private int id;
    @SerializedName("name")
    private String nombre;
    @SerializedName("url")
    private String url;
    @SerializedName("founded")
    private String founded;
    @SerializedName("deorbited")
    private String deorbited;
    @SerializedName("description")
    private String description;
    @SerializedName("orbit")
    private String orbit;
    @SerializedName("image_url")
    private String image;
    private Bitmap bImage;

    @Override
    public String toString() {
        return "SpaceStationModel{" +
                "nombre='" + nombre + '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getFounded() {
        return founded;
    }

    public void setFounded(String founded) {
        this.founded = founded;
    }

    public String getDeorbited() {
        return deorbited;
    }

    public void setDeorbited(String deorbited) {
        this.deorbited = deorbited;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getOrbit() {
        return orbit;
    }

    public void setOrbit(String orbit) {
        this.orbit = orbit;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Bitmap getbImage() {
        return bImage;
    }

    public void setbImage(Bitmap bImage) {
        this.bImage = bImage;
    }
}
