package com.example.movileappsproyect.model.localizationModels;

//General object
public class LocationModel {
    private DeviceLocationModel device;
    private ISSLocationModel iss;
    private int distanceToISS;

    public DeviceLocationModel getDevice() {
        return device;
    }

    public void setDevice(DeviceLocationModel device) {
        this.device = device;
    }

    public ISSLocationModel getIss() {
        return iss;
    }

    public void setIss(ISSLocationModel iss) {
        this.iss = iss;
    }

    public int getDistanceToISS() {
        return distanceToISS;
    }

    public void setDistanceToISS(int distanceToISS) {
        this.distanceToISS = distanceToISS;
    }
}
