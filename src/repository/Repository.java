package repository;

import java.util.ArrayList;

import android.util.Log;
import com.example.parkumbc.UpdateParkingTask;
import model.ParkingLot;
import model.PermitGroup;
import storage.DataStorage;
import android.content.Context;

public class Repository {

    private static final String TAG = "ENTRY";
    private final Context context;
    private DataStorage dataStorage;

    public Repository(Context _context) {
        context = _context;
        dataStorage = new DataStorage(context);
    }

    public void storeParkingLots(ArrayList<ParkingLot> parkingLots) {
        dataStorage.createParkingLots(parkingLots);
    }

    public boolean updateParkingLot(long parking_lot_id, boolean is_parked) {
        new UpdateParkingTask(parking_lot_id, is_parked).execute();
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
