package model;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

public class ParkingLot {

    private long lot_id;
    private String name;
    private long current_count;
    private long capacity;
    private ArrayList<LatLng> corners;
    private ArrayList<PermitGroup> permitGroups;

    public ParkingLot(long lot_id, String name, long current_count, long capacity, ArrayList<LatLng> corners) {
        this.lot_id = lot_id;
        this.name = name;
        this.current_count = current_count;
        this.capacity = capacity;
        this.corners = corners;
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

    public void setPermitGroups(ArrayList<PermitGroup> permitGroups) {
        this.permitGroups = permitGroups;
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