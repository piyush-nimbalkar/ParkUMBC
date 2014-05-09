package model;

import android.os.Parcel;
import android.os.Parcelable;

public class PermitGroup implements Parcelable {

    private String name;
    private String letter;
    private String color;

    public PermitGroup(String name, String letter, String color) {
        this.name = name;
        this.letter = letter;
        this.color = color;
    }

    public PermitGroup(Parcel in) {
        name = in.readString();
        letter = in.readString();
        color = in.readString();
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
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(letter);
        dest.writeString(color);
    }

    public static final Parcelable.Creator<PermitGroup> CREATOR = new Parcelable.Creator<PermitGroup>() {

        public PermitGroup createFromParcel(Parcel in) {
            return new PermitGroup(in);
        }

        public PermitGroup[] newArray(int size) {
            return new PermitGroup[size];
        }

    };

}
