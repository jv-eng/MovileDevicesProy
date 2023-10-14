package com.example.movileappsproyect.model;

import android.graphics.Bitmap;

import com.google.gson.annotations.SerializedName;

public class DayPictureModel {
    @SerializedName("copyright")
    private String copyright;
    @SerializedName("date")
    private String date;
    @SerializedName("explanation")
    private String explanation;
    @SerializedName("hdurl")
    private String url;
    @SerializedName("title")
    private String title;
    private Bitmap bImage;

    @Override
    public String toString() {
        return "DayPictureModel{" +
                "date='" + date + '\'' +
                ", title='" + title + '\'' +
                ", author='" + copyright + '\'' +
                '}';
    }

    public Bitmap getbImage() {
        return bImage;
    }

    public void setbImage(Bitmap bImage) {
        this.bImage = bImage;
    }

    public String getCopyright() {
        return copyright;
    }

    public String getDate() {
        return date;
    }

    public String getExplanation() {
        return explanation;
    }

    public String getUrl() {
        return url;
    }

    public String getTitle() {
        return title;
    }
}
