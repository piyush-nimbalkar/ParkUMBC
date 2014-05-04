package model;

public class ParkingLot {
    private long lot_id;
    private String name;
    private long current_count;
    private long capacity;

    public ParkingLot(long lot_id_, String name_, long current_count_, long capacity_) {
        lot_id = lot_id_;
        name = name_;
        current_count = current_count_;
        capacity = capacity_;
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
}