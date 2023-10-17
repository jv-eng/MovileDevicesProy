package com.example.movileappsproyect.model.localizationModels;

import com.google.gson.annotations.SerializedName;

public class ISSPositionModel {
    @SerializedName("latitude")
    private String latitude;
    @SerializedName("longitude")
    private String longitude;

    public String getLatitude() {
        return latitude;
    }

    public String getLongitude() {
        return longitude;
    }
}
