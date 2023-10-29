package com.example.movileappsproyect.model;

import com.google.gson.annotations.SerializedName;

public class SpaceStationRequestModel {
    @SerializedName("count")
    private int id;
    @SerializedName("next")
    private String next;
    @SerializedName("previous")
    private String previous;
    @SerializedName("results")
    private SpaceStationModel [] results;

    public SpaceStationModel [] getResults() {
        return results;
    }

    public String getNext() {
        return next;
    }
}
