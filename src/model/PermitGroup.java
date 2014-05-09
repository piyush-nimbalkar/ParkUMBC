package model;

import android.os.Parcel;
import android.os.Parcelable;

public class PermitGroup implements Parcelable {

    private long id;
    private String name;
    private String letter;
    private String color;

    public PermitGroup(long id, String name, String letter, String color) {
        this.id = id;
        this.name = name;
        this.letter = letter;
        this.color = color;
    }

    public PermitGroup(Parcel in) {
        id = in.readLong();
        name = in.readString();
        letter = in.readString();
        color = in.readString();
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getLetter() {
        return letter;
    }

    public String getColor() {
        return color;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeLong(id);
        out.writeString(name);
        out.writeString(letter);
        out.writeString(color);
    }

    public static final Parcelable.Creator<PermitGroup> CREATOR = new Parcelable.Creator<PermitGroup>() {

        public PermitGroup createFromParcel(Parcel in) {
            return new PermitGroup(in);
        }

        public PermitGroup[] newArray(int size) {
            return new PermitGroup[size];
        }

    };

    @Override
    public boolean equals(Object o) {
        if (o != null && o instanceof PermitGroup)
            return (this.id == ((PermitGroup) o).id);
        return false;
    }

    @Override
    public int hashCode() {
        return Long.valueOf(this.id).hashCode();
    }

}
