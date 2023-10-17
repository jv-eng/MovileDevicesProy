package com.example.movileappsproyect.model.localizationModels;

import com.google.gson.annotations.SerializedName;

public class ISSLocationModel {

    @SerializedName("iss_position")
    private ISSPositionModel position;
    @SerializedName("timestamp")
    private long timestamp;
    @SerializedName("message")
    private String message;

    public String getLatitude() {
        return position.getLatitude();
    }

    public String getLongitude() {
        return position.getLatitude();
    }

    public long getTimestamp() {
        return timestamp;
    }

    public String getMessage() {
        return message;
    }


}
