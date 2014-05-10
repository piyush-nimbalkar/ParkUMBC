package model;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

public class ParkingLot implements Parcelable {

    private long lot_id;
    private String name;
    private long current_count;
    private long capacity;
    private ArrayList<LatLng> corners;
    private LatLng entrance;
    private ArrayList<PermitGroup> permitGroups;

    public ParkingLot() {
        corners = new ArrayList<LatLng>();
        permitGroups = new ArrayList<PermitGroup>();
    }

    public ParkingLot(long lot_id, String name, long current_count, long capacity) {
        this.lot_id = lot_id;
        this.name = name;
        this.current_count = current_count;
        this.capacity = capacity;
        this.entrance = null;
    }

    public ParkingLot(Parcel in) {
        this();
        lot_id = in.readLong();
        name = in.readString();
        current_count = in.readLong();
        capacity = in.readLong();
        in.readTypedList(corners, LatLng.CREATOR);
        entrance = in.readParcelable(LatLng.class.getClassLoader());
        in.readTypedList(permitGroups, PermitGroup.CREATOR);
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

    public LatLng getEntrance() {
        return entrance;
    }

    public ArrayList<PermitGroup> getPermitGroups() {
        return permitGroups;
    }

    public void setCorners(ArrayList<LatLng> corners) {
        this.corners = corners;
    }

    public void setEntrance(double lat, double lng) {
        LatLng entrance = new LatLng(lat, lng);
        this.entrance = entrance;
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeLong(lot_id);
        out.writeString(name);
        out.writeLong(current_count);
        out.writeLong(capacity);
        out.writeTypedList(corners);
        out.writeParcelable(entrance, flags);
        out.writeTypedList(permitGroups);
    }

    public static final Parcelable.Creator<ParkingLot> CREATOR = new Parcelable.Creator<ParkingLot>() {

        public ParkingLot createFromParcel(Parcel in) {
            return new ParkingLot(in);
        }

        public ParkingLot[] newArray(int size) {
            return new ParkingLot[size];
        }

    };

}
