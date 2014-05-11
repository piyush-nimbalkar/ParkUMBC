package model;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

public class ParkingLot implements Parcelable {

    private long lotId;
    private String name;
    private long currentCount;
    private long capacity;
    private ArrayList<LatLng> corners;
    private LatLng entrance;
    private ArrayList<PermitGroup> permitGroups;

    public ParkingLot() {
        corners = new ArrayList<LatLng>();
        permitGroups = new ArrayList<PermitGroup>();
    }

    public ParkingLot(long lotId, String name, long currentCount, long capacity) {
        this.lotId = lotId;
        this.name = name;
        this.currentCount = currentCount;
        this.capacity = capacity;
        this.entrance = null;
    }

    public ParkingLot(Parcel in) {
        this();
        lotId = in.readLong();
        name = in.readString();
        currentCount = in.readLong();
        capacity = in.readLong();
        in.readTypedList(corners, LatLng.CREATOR);
        entrance = in.readParcelable(LatLng.class.getClassLoader());
        in.readTypedList(permitGroups, PermitGroup.CREATOR);
    }

    public long getLotId() {
        return lotId;
    }

    public String getLotName() {
        return name;
    }

    public long getCurrentCount() {
        return currentCount;
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

    public void setEntrance(LatLng entrance) {
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
        out.writeLong(lotId);
        out.writeString(name);
        out.writeLong(currentCount);
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
