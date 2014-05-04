package model;

public class LatLong {

    private double latitude;
    private double longitude;

    public LatLong(double latitude_, double longitude_) {
        latitude = latitude_;
        longitude = longitude_;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

}
