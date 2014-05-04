package repository;

import java.util.ArrayList;
import java.util.List;

import android.util.Log;
import model.Entry;
import model.LatLong;
import model.ParkingLot;
import storage.DataStorage;
import storage.ServerStorage;
import android.content.Context;

public class EntryRepository {

    private static final String TAG = "ENTRY";
    private final Context context;
    private DataStorage dataStorage;

    public EntryRepository(Context _context) {
        context = _context;
        dataStorage = new DataStorage(context);
    }

    public boolean createEntry(double latitude, double longitude, long parking_lot_id, boolean is_parked) {
        Entry entry = new Entry(latitude, longitude, parking_lot_id, is_parked);
        dataStorage.store(entry);
        new ServerStorage().store(parking_lot_id, is_parked);
        return true;
    }

    public Entry getEntry() {
        return dataStorage.getEntryDetails();
    }

    public List<ParkingLot> getParkingLots() {
        List<ParkingLot> parking_lots = dataStorage.getParkingLots();

        for (ParkingLot lot : parking_lots) {
            Log.d(TAG, lot.getLotName());
            ArrayList<LatLong> corners = lot.getCorners();
            Log.d(TAG, String.valueOf(corners.size()));
            for (int i = 0; i < corners.size(); i++) {
                Log.d(TAG, String.valueOf(corners.get(i).getLatitude()) + ", " + String.valueOf(corners.get(i).getLongitude()));
            }
        }
        return parking_lots;
    }
}
