package storage;

import static android.provider.BaseColumns._ID;

import java.util.ArrayList;
import java.util.List;

import android.widget.ArrayAdapter;
import com.google.android.gms.maps.model.LatLng;
import android.util.Log;
import model.Entry;
import model.ParkingLot;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import model.PermitGroup;

public class DataStorage extends SQLiteOpenHelper {

    private static final String TAG = "DATABASE";
    private static final String DB_NAME = "park_umbc.db";

    private static final String TABLE_ENTRY = "entry";
    private static final String TABLE_PARKING_LOT = "parking_lot";
    private static final String TABLE_CORNER = "corner";
    private static final String TABLE_PERMIT_GROUP = "permit_group";

    private static final String COLUMN_PARKING_LOT_ID = "parking_lot_id";
    private static final String COLUMN_LATITUDE = "latitude";
    private static final String COLUMN_LONGITUDE = "longitude";
    private static final String COLUMN_IS_PARKED = "is_parked";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_CURRENT_COUNT = "current_count";
    private static final String COLUMN_CAPACITY = "capacity";
    private static final String COLUMN_CORNER_INDEX = "corner_index";
    private static final String COLUMN_PERMIT_LETTER = "letter";
    private static final String COLUMN_PERMIT_COLOR = "color";

    private static final String CREATE_ENTRY_TABLE = "CREATE TABLE " +
            TABLE_ENTRY + " (" +
            _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COLUMN_LATITUDE + " TEXT, " +
            COLUMN_LONGITUDE + " TEXT, " +
            COLUMN_PARKING_LOT_ID + " TEXT , " +
            COLUMN_IS_PARKED + " BOOL );";

    private static final String CREATE_PARKING_LOT_TABLE = "CREATE TABLE " +
            TABLE_PARKING_LOT + " (" +
            COLUMN_PARKING_LOT_ID + " INTEGER PRIMARY KEY, " +
            COLUMN_NAME + " VARCHAR(200), " +
            COLUMN_CURRENT_COUNT + " TEXT, " +
            COLUMN_CAPACITY + " TEXT );";

    private static final String CREATE_CORNER_TABLE = "CREATE TABLE " +
            TABLE_CORNER + " (" +
            _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COLUMN_PARKING_LOT_ID + " INTEGER, " +
            COLUMN_LATITUDE + " TEXT, " +
            COLUMN_LONGITUDE + " TEXT, " +
            COLUMN_CORNER_INDEX + " INTEGER);";

    private static final String CREATE_PERMIT_GROUP = "CREATE TABLE " +
            TABLE_PERMIT_GROUP + " (" +
            _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COLUMN_NAME + " TEXT, " +
            COLUMN_PERMIT_LETTER + " TEXT, " +
            COLUMN_PERMIT_COLOR + " TEXT);";

    private static final String INSERT_ENTRY = "INSERT INTO " +
            TABLE_ENTRY + " (" +
            COLUMN_LATITUDE + "," +
            COLUMN_LONGITUDE + "," +
            COLUMN_PARKING_LOT_ID + "," +
            COLUMN_IS_PARKED + ") VALUES (?, ?, ?, ?)";

    private static final String INSERT_PARKING_LOT = "INSERT INTO " +
            TABLE_PARKING_LOT + " (" +
            COLUMN_PARKING_LOT_ID + "," +
            COLUMN_NAME + "," +
            COLUMN_CURRENT_COUNT + "," +
            COLUMN_CAPACITY + ") VALUES (?, ?, ?, ?)";

    private static final String INSERT_CORNER = "INSERT INTO " +
            TABLE_CORNER + " (" +
            COLUMN_PARKING_LOT_ID + "," +
            COLUMN_LATITUDE + "," +
            COLUMN_LONGITUDE + "," +
            COLUMN_CORNER_INDEX + ") VALUES (?, ?, ?, ?)";

    private static final String INSERT_PERMIT_GROUP = "INSERT INTO " +
            TABLE_PERMIT_GROUP + " (" +
            COLUMN_NAME + "," +
            COLUMN_PERMIT_LETTER + "," +
            COLUMN_PERMIT_COLOR + ") VALUES (?, ?, ?)";


    public DataStorage(Context context) {
        super(context, DB_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_ENTRY_TABLE);
        db.execSQL(CREATE_PARKING_LOT_TABLE);
        db.execSQL(CREATE_CORNER_TABLE);
        db.execSQL(CREATE_PERMIT_GROUP);
        create_parking_lots(db);
        createPermitGroups(db);
    }

    private void createPermitGroups(SQLiteDatabase db) {
        List<PermitGroup> permits = new ArrayList<PermitGroup>();

        permits.add(new PermitGroup("Commuter Student", "A", "red"));
        permits.add(new PermitGroup("Walker Community Student", "B", "green"));
        permits.add(new PermitGroup("Residential Student (Besides Walker)", "C", "yellow"));
        permits.add(new PermitGroup("Faculty/Staff", "D", "violet"));
        permits.add(new PermitGroup("Gated Faculty/Staff", "E", "violet"));

        for (PermitGroup permit : permits) {
            SQLiteStatement statement = db.compileStatement(INSERT_PERMIT_GROUP);
            statement.bindString(1, permit.getName());
            statement.bindString(2, permit.getLetter());
            statement.bindString(3, permit.getColor());
            statement.executeInsert();
        }
        Log.d(TAG, "Permit groups created.");
    }

    public void create_parking_lots(SQLiteDatabase db) {
        List<ParkingLot> parkingLots = new ArrayList<ParkingLot>();

        ArrayList<LatLng> corners_one = new ArrayList<LatLng>();
        corners_one.add(new LatLng(39.25531666, -76.71158333));
        corners_one.add(new LatLng(39.255, -76.710483333));
        corners_one.add(new LatLng(39.254633333, -76.710666667));
        corners_one.add(new LatLng(39.254616667, -76.710766667));
        corners_one.add(new LatLng(39.2545, -76.7109));
        corners_one.add(new LatLng(39.2544, -76.710983333));
        corners_one.add(new LatLng(39.254483333, -76.71125));
        corners_one.add(new LatLng(39.2549, -76.711016667));
        corners_one.add(new LatLng(39.2551, -76.7116));

        ArrayList<LatLng> corners_two = new ArrayList<LatLng>();
        corners_two.add(new LatLng(39.254783333, -76.707516667));
        corners_two.add(new LatLng(39.254433333, -76.706733333));
        corners_two.add(new LatLng(39.254133333, -76.706916667));
        corners_two.add(new LatLng(39.254466667, -76.70765));
        corners_two.add(new LatLng(39.254383333, -76.707716667));
        corners_two.add(new LatLng(39.254016667, -76.707083333));
        corners_two.add(new LatLng(39.2535, -76.707583333));
        corners_two.add(new LatLng(39.253783333, -76.708));
        corners_two.add(new LatLng(39.254116667, -76.708116667));

        parkingLots.add(new ParkingLot(1, "COMMONS", 0, 3, corners_one));
        parkingLots.add(new ParkingLot(2, "LOT 25", 0, 4, corners_two));

        for (ParkingLot lot : parkingLots) {
            SQLiteStatement statement = db.compileStatement(INSERT_PARKING_LOT);
            statement.bindLong(1, lot.getLotId());
            statement.bindString(2, lot.getLotName());
            statement.bindLong(3, lot.getCurrentCount());
            statement.bindLong(4, lot.getCapacity());
            statement.executeInsert();
            create_parking_corners(db, lot);
        }
        Log.d(TAG, "Parking lots created.");
    }

    private void create_parking_corners(SQLiteDatabase db, ParkingLot lot) {
        ArrayList<LatLng> corners = lot.getCorners();
        for (int i = 0; i < corners.size(); i++) {
            SQLiteStatement statement = db.compileStatement(INSERT_CORNER);
            statement.bindLong(1, lot.getLotId());
            statement.bindDouble(2, corners.get(i).latitude);
            statement.bindDouble(3, corners.get(i).longitude);
            statement.bindLong(4, i);
            statement.executeInsert();
        }
    }

    public void store(Entry entry) {
        SQLiteDatabase db = getWritableDatabase();
        SQLiteStatement statement = db.compileStatement(INSERT_ENTRY);
        statement.bindDouble(1, entry.getLatitude());
        statement.bindDouble(2, entry.getLongitude());
        statement.bindLong(3, entry.getParkingLotId());
        statement.bindLong(4, (entry.getParkingStatus() ? 1 : 0));
        statement.executeInsert();
    }

    public List<Entry> get() {
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + TABLE_ENTRY, null);
        List<Entry> entries = new ArrayList<Entry>();
        if (c != null) {
            if (c.moveToFirst()) {
                do {
                    double latitude = c.getDouble(c.getColumnIndex(COLUMN_LATITUDE));
                    double longitude = c.getDouble(c.getColumnIndex(COLUMN_LONGITUDE));
                    long parking_lot_id = c.getLong(c.getColumnIndex(COLUMN_PARKING_LOT_ID));
                    boolean is_parked = c.getLong(c.getColumnIndex(COLUMN_IS_PARKED)) == 1;
                    Entry entry = new Entry(latitude, longitude, parking_lot_id, is_parked);
                    entries.add(entry);
                } while (c.moveToNext());
            }
        }
        return entries;
    }

    public Entry getEntryDetails() {
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + TABLE_ENTRY, null);
        double latitude, longitude;
        long parking_lot_id;
        boolean is_parked;
        Entry entry = null;
        if (c != null) {
            if (c.moveToFirst()) {
                do {
                    latitude = c.getDouble(c.getColumnIndex(COLUMN_LATITUDE));
                    longitude = c.getDouble(c.getColumnIndex(COLUMN_LONGITUDE));
                    parking_lot_id = c.getLong(c.getColumnIndex(COLUMN_PARKING_LOT_ID));
                    is_parked = c.getLong(c.getColumnIndex(COLUMN_IS_PARKED)) == 1;
                    entry = new Entry(latitude, longitude, parking_lot_id, is_parked);
                } while (c.moveToNext());
            }
        }
        return entry;
    }

    public List<ParkingLot> getParkingLots() {
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + TABLE_PARKING_LOT, null);
        List<ParkingLot> lots = new ArrayList<ParkingLot>();

        if (c != null) {
            if (c.moveToFirst()) {
                do {
                    long lotId = c.getLong(c.getColumnIndex(COLUMN_PARKING_LOT_ID));
                    String name = c.getString(c.getColumnIndex(COLUMN_NAME));
                    long count = c.getLong(c.getColumnIndex(COLUMN_CURRENT_COUNT));
                    long capacity = c.getLong(c.getColumnIndex(COLUMN_CAPACITY));
                    ArrayList<LatLng> corners = getCorners(db, lotId);

                    ParkingLot lot = new ParkingLot(lotId, name, count, capacity, corners);
                    lots.add(lot);
                } while (c.moveToNext());
            }
        }
        return lots;
    }

    private ArrayList<LatLng> getCorners(SQLiteDatabase db, long lotId) {
        ArrayList<LatLng> corners = new ArrayList<LatLng>();

        Cursor c = db.query(TABLE_CORNER, null, COLUMN_PARKING_LOT_ID + "=?",
                new String[]{String.valueOf(lotId)}, null, null, COLUMN_CORNER_INDEX, null);

        if (c != null) {
            if (c.moveToFirst()) {
                do {
                    double latitude = c.getDouble(c.getColumnIndex(COLUMN_LATITUDE));
                    double longitude = c.getDouble(c.getColumnIndex(COLUMN_LONGITUDE));
                    corners.add(new LatLng(latitude, longitude));
                } while (c.moveToNext());
            }
        }
        return corners;
    }

    public List<PermitGroup> getPermitGroups() {
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + TABLE_PERMIT_GROUP, null);
        List<PermitGroup> permits = new ArrayList<PermitGroup>();

        if (c != null) {
            if (c.moveToFirst()) {
                do {
                    String name = c.getString(c.getColumnIndex(COLUMN_NAME));
                    String letter = c.getString(c.getColumnIndex(COLUMN_PERMIT_LETTER));
                    String color = c.getString(c.getColumnIndex(COLUMN_PERMIT_COLOR));
                    permits.add(new PermitGroup(name, letter, color));
                } while (c.moveToNext());
            }
        }
        return permits;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

}
