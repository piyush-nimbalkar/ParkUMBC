package repository;

import java.util.ArrayList;
import java.util.List;

import android.util.Log;
import model.Entry;
import model.ParkingLot;
import model.PermitGroup;
import storage.DataStorage;
import storage.ServerStorage;
import android.content.Context;

public class Repository {

    private static final String TAG = "ENTRY";
    private final Context context;
    private DataStorage dataStorage;

    public Repository(Context _context) {
        context = _context;
        dataStorage = new DataStorage(context);
    }

    public boolean createEntry(double latitude, double longitude, long parking_lot_id, boolean is_parked) {
        Entry entry = new Entry(latitude, longitude, parking_lot_id, is_parked);
        dataStorage.store(entry);
        new ServerStorage().store(parking_lot_id, is_parked);
        return true;
    }

    public ArrayList<ParkingLot> getParkingLots() {
        Log.d(TAG, "In Repository.java");
        return dataStorage.getParkingLots();
    }

    public ArrayList<PermitGroup> getPermitGroups() {
        return dataStorage.getPermitGroups();
    }

}
