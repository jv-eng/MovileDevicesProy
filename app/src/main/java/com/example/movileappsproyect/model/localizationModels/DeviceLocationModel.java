package com.example.movileappsproyect.model.localizationModels;

import com.google.gson.annotations.SerializedName;

public class DeviceLocationModel {
    @SerializedName("country")
    private String country;
    @SerializedName("nearestPlace")
    private String place;
    @SerializedName("map")
    private String map_url;

    public String getCountry() {
        return country;
    }

    public String getPlace() {
        return place;
    }

    public String getMap_url() {
        return map_url;
    }
}
