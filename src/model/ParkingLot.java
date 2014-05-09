package model;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

public class ParkingLot {

    private long lot_id;
    private String name;
    private long current_count;
    private long capacity;
    private ArrayList<LatLng> corners;

    public ParkingLot(long lot_id_, String name_, long current_count_, long capacity_, ArrayList<LatLng> corners_) {
        lot_id = lot_id_;
        name = name_;
        current_count = current_count_;
        capacity = capacity_;
        corners = corners_;
    }

    public long getLotId() {
        return lot_id;
    }

    public String getLotName() {
        return name;
    }

    public long getCurrentCount() {
        return current_count;
    }

    public long getCapacity() {
        return capacity;
    }

    public ArrayList<LatLng> getCorners() {
        return corners;
    }

    public LatLng getMarkerPosition() {
        double latitude = 0, longitude = 0;

        for (LatLng corner : corners) {
            latitude += corner.latitude;
            longitude += corner.longitude;
        }

        if (!corners.isEmpty())
            return new LatLng(latitude / corners.size(), longitude / corners.size());
        else
            return new LatLng(latitude, longitude);
    }

}