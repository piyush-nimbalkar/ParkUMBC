package model;

public class ParkingLot {
	private long lot_id;
	private long current_count;
	private long capacity;

    public ParkingLot(long  lot_id, long current_count, long capacity) {
        this.lot_id = lot_id;
        this.current_count = current_count;
        this.capacity = capacity;
    }

    public long getLotId() {
        return lot_id;
    }

    public long getCurrentCount() {
        return current_count;
    }

    public long getCapacity() {
        return capacity;
    }
}