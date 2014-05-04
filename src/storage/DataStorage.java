package storage;

import static android.provider.BaseColumns._ID;

import java.util.ArrayList;
import java.util.List;

import model.Entry;
import model.ParkingLot;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

public class DataStorage extends SQLiteOpenHelper {
    private static final String TABLE_NAME = "entries";
    private static final String DB_NAME = "parking.db";
    private static final String LATITUDE_COLUMN = "latitude";
    private static final String LONGITUDE_COLUMN = "longitude";
    private static final String PARKING_LOT_ID_COLUMN = "parking_lot_id";
    private static final String IS_PARKED_COLUMN = "is_parked";

    private static final String TABLE_PARKING_LOTS = "parking_lots";
    private static final String LOT_ID_COLUMN = "lot_id";
    private static final String COUNT_COLUMN = "current_count";
    private static final String CAPACITY_COLUMN = "capacity";

    
    public DataStorage(Context context) {
        super(context, DB_NAME, null, 1);
    }

    private static final String DATABASE_CREATE = "CREATE TABLE " + TABLE_NAME + " (" +
            _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            LATITUDE_COLUMN + " TEXT, " +
            LONGITUDE_COLUMN + " TEXT, " +
            PARKING_LOT_ID_COLUMN + " TEXT , " + 
            IS_PARKED_COLUMN + " TEXT );";

    private static final String DATABASE_PARKING_LOTS = "CREATE TABLE " + TABLE_PARKING_LOTS + " (" +
            LOT_ID_COLUMN + " INTEGER PRIMARY KEY, " +
            COUNT_COLUMN + " TEXT, " +
            CAPACITY_COLUMN + " TEXT );";

    public void store(Entry entry) {
        SQLiteDatabase db = getWritableDatabase();
        SQLiteStatement statement = db.compileStatement("insert into " + TABLE_NAME + " (" + LATITUDE_COLUMN + "," + LONGITUDE_COLUMN + "," + PARKING_LOT_ID_COLUMN + "," + IS_PARKED_COLUMN + ") values ( ?, ?, ?, ? )");
        statement.bindDouble(1, entry.getLatitude());
        statement.bindDouble(2, entry.getLongitude());
        statement.bindLong(3, entry.getParkingLotId());
        statement.bindLong(4, entry.getParkingStatus());
        statement.executeInsert();
    }

    public void store_lots(ParkingLot lot) {
        SQLiteDatabase db = getWritableDatabase();
        SQLiteStatement statement = db.compileStatement("insert into " + TABLE_PARKING_LOTS + " (" + LOT_ID_COLUMN + "," + COUNT_COLUMN + "," + CAPACITY_COLUMN + ") values ( ?, ?, ? )");
        statement.bindLong(1, lot.getLotId());
        statement.bindLong(2, lot.getCurrentCount());
        statement.bindLong(3, lot.getCapacity());
        statement.executeInsert();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DATABASE_CREATE);
        db.execSQL(DATABASE_PARKING_LOTS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    public List<Entry> get() {
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        List<Entry> entries = new ArrayList<Entry>();
        if (c != null) {
            if (c.moveToFirst()) {
                do {
                	double latitude = c.getDouble(c.getColumnIndex(LATITUDE_COLUMN));
                	double longitude = c.getDouble(c.getColumnIndex(LONGITUDE_COLUMN));
                	long parking_lot_id = c.getLong(c.getColumnIndex(PARKING_LOT_ID_COLUMN));
                	long is_parked = c.getLong(c.getColumnIndex(IS_PARKED_COLUMN));
                    Entry entry = new Entry(latitude, longitude, parking_lot_id, is_parked);
                    entries.add(entry);
                } while (c.moveToNext());
            }
        }
        return entries;
    }

    public Entry getEntryDetails() {
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        double latitude, longitude;
        long parking_lot_id, is_parked;
        Entry entry = null;
        if (c != null) {
            if (c.moveToFirst()) {
                do {
                    latitude = c.getDouble(c.getColumnIndex(LATITUDE_COLUMN));
                    longitude = c.getDouble(c.getColumnIndex(LONGITUDE_COLUMN));
                	parking_lot_id = c.getLong(c.getColumnIndex(PARKING_LOT_ID_COLUMN));
                	is_parked = c.getLong(c.getColumnIndex(IS_PARKED_COLUMN));
                    entry = new Entry(latitude, longitude, parking_lot_id, is_parked);
                } while (c.moveToNext());
            }
        }
        return entry;
    }
    
    public List<ParkingLot> getParkingLots() {
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + TABLE_PARKING_LOTS, null);
        List<ParkingLot> lots = new ArrayList<ParkingLot>();
        if (c != null) {
            if (c.moveToFirst()) {
                do {
                    long lot_id = c.getLong(c.getColumnIndex(LOT_ID_COLUMN));
                    long count = c.getLong(c.getColumnIndex(COUNT_COLUMN));
                    long capacity = c.getLong(c.getColumnIndex(CAPACITY_COLUMN));
                    ParkingLot lot = new ParkingLot(lot_id, count, capacity);
                    lots.add(lot);
                } while (c.moveToNext());
            }
        }
        return lots;
    }
}