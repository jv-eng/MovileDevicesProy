package com.example.movileappsproyect.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

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
}
