package storage;

import static android.provider.BaseColumns._ID;

import java.util.ArrayList;
import java.util.List;

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
    private static final String TABLE_PARKING_PERMIT = "parking_permit";

    private static final String COLUMN_PARKING_LOT_ID = "parking_lot_id";
    private static final String COLUMN_LATITUDE = "latitude";
    private static final String COLUMN_LONGITUDE = "longitude";
    private static final String COLUMN_IS_PARKED = "is_parked";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_CURRENT_COUNT = "current_count";
    private static final String COLUMN_CAPACITY = "capacity";
    private static final String COLUMN_CORNER_INDEX = "corner_index";
    private static final String COLUMN_PERMIT_GROUP_ID = "permit_group_id";
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

    private static final String CREATE_PERMIT_GROUP_TABLE = "CREATE TABLE " +
            TABLE_PERMIT_GROUP + " (" +
            COLUMN_PERMIT_GROUP_ID + " INTEGER PRIMARY KEY, " +
            COLUMN_NAME + " TEXT, " +
            COLUMN_PERMIT_LETTER + " TEXT, " +
            COLUMN_PERMIT_COLOR + " TEXT);";

    private static final String CREATE_PARKING_PERMIT_TABLE = "CREATE TABLE " +
            TABLE_PARKING_PERMIT + " (" +
            COLUMN_PARKING_LOT_ID + " INTEGER, " +
            COLUMN_PERMIT_GROUP_ID + " INTEGER, PRIMARY KEY(" +
            COLUMN_PARKING_LOT_ID + ", " +
            COLUMN_PERMIT_GROUP_ID + "));";

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
            COLUMN_PERMIT_GROUP_ID + "," +
            COLUMN_NAME + "," +
            COLUMN_PERMIT_LETTER + "," +
            COLUMN_PERMIT_COLOR + ") VALUES (?, ?, ?, ?)";

    private static final String INSERT_PARKING_PERMIT = "INSERT INTO " +
            TABLE_PARKING_PERMIT + " (" +
            COLUMN_PARKING_LOT_ID + "," +
            COLUMN_PERMIT_GROUP_ID + ") VALUES (?, ?)";


    public DataStorage(Context context) {
        super(context, DB_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_ENTRY_TABLE);
        db.execSQL(CREATE_PARKING_LOT_TABLE);
        db.execSQL(CREATE_CORNER_TABLE);
        db.execSQL(CREATE_PERMIT_GROUP_TABLE);
        db.execSQL(CREATE_PARKING_PERMIT_TABLE);
        create_parking_lots(db);
        createPermitGroups(db);
        createParkingPermits(db);
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
        corners_two.add(new LatLng(39.254879, -76.707530));
        corners_two.add(new LatLng(39.254559, -76.706495));
        corners_two.add(new LatLng(39.253824, -76.707047));
        corners_two.add(new LatLng(39.254131, -76.707514));
        corners_two.add(new LatLng(39.254356, -76.707991));
        corners_two.add(new LatLng(39.254646, -76.707734));

        ArrayList<LatLng> corners_three = new ArrayList<LatLng>();
        corners_three.add(new LatLng(39.260121, -76.697823));
        corners_three.add(new LatLng(39.260501, -76.697426));
        corners_three.add(new LatLng(39.260445, -76.697340));
        corners_three.add(new LatLng(39.260069, -76.697737));

        ArrayList<LatLng> corners_four = new ArrayList<LatLng>();
        corners_four.add(new LatLng(39.254618, -76.704357));
        corners_four.add(new LatLng(39.254335, -76.703821));
        corners_four.add(new LatLng(39.254111, -76.703810));
        corners_four.add(new LatLng(39.253828, -76.704078));
        corners_four.add(new LatLng(39.254269, -76.704754));

        ArrayList<LatLng> corners_five = new ArrayList<LatLng>();
        corners_five.add(new LatLng(39.255686, -76.701346));
        corners_five.add(new LatLng(39.255229, -76.700799));
        corners_five.add(new LatLng(39.254718, -76.701195));
        corners_five.add(new LatLng(39.255545, -76.701828));

        ArrayList<LatLng> corners_six = new ArrayList<LatLng>();
        corners_six.add(new LatLng(39.259118, -76.698454));
        corners_six.add(new LatLng(39.259043, -76.698341));
        corners_six.add(new LatLng(39.259372, -76.698000));
        corners_six.add(new LatLng(39.259442, -76.698118));

        ArrayList<LatLng> corners_seven = new ArrayList<LatLng>();
        corners_seven.add(new LatLng(39.255149, -76.705409));
        corners_seven.add(new LatLng(39.254933, -76.704808));
        corners_seven.add(new LatLng(39.254427, -76.705108));
        corners_seven.add(new LatLng(39.254651, -76.705720));

        ArrayList<LatLng> corners_eight = new ArrayList<LatLng>();
        corners_eight.add(new LatLng(39.252478, -76.704491));
        corners_eight.add(new LatLng(39.252316, -76.704593));
        corners_eight.add(new LatLng(39.252478, -76.704848));
        corners_eight.add(new LatLng(39.252088, -76.705245));
        corners_eight.add(new LatLng(39.252299, -76.705642));
        corners_eight.add(new LatLng(39.252790, -76.705030));

        ArrayList<LatLng> corners_nine = new ArrayList<LatLng>();
        corners_nine.add(new LatLng(39.253375, -76.706623));
        corners_nine.add(new LatLng(39.253105, -76.706119));
        corners_nine.add(new LatLng(39.253018, -76.706178));
        corners_nine.add(new LatLng(39.252927, -76.706039));
        corners_nine.add(new LatLng(39.252544, -76.706403));
        corners_nine.add(new LatLng(39.252607, -76.706741));
        corners_nine.add(new LatLng(39.252598, -76.706854));
        corners_nine.add(new LatLng(39.252731, -76.707310));

        ArrayList<LatLng> corners_ten = new ArrayList<LatLng>();
        corners_ten.add(new LatLng(39.253525, -76.706484));
        corners_ten.add(new LatLng(39.254248, -76.705744));
        corners_ten.add(new LatLng(39.254131, -76.705336));
        corners_ten.add(new LatLng(39.254015, -76.705180));
        corners_ten.add(new LatLng(39.253201, -76.705985));

        ArrayList<LatLng> corners_eleven = new ArrayList<LatLng>();
        corners_eleven.add(new LatLng(39.254206, -76.708099));
        corners_eleven.add(new LatLng(39.253762, -76.707342));
        corners_eleven.add(new LatLng(39.252794, -76.708624));
        corners_eleven.add(new LatLng(39.253579, -76.709322));
        corners_eleven.add(new LatLng(39.253961, -76.708517));

        ArrayList<LatLng> corners_twelve = new ArrayList<LatLng>();
        corners_twelve.add(new LatLng(39.254626, -76.709129));
        corners_twelve.add(new LatLng(39.254372, -76.708249));
        corners_twelve.add(new LatLng(39.254165, -76.708576));
        corners_twelve.add(new LatLng(39.253683, -76.709456));
        corners_twelve.add(new LatLng(39.254086, -76.709713));
        corners_twelve.add(new LatLng(39.254264, -76.709408));

        ArrayList<LatLng> corners_thirteen = new ArrayList<LatLng>();
        corners_thirteen.add(new LatLng(39.255182, -76.708463));
        corners_thirteen.add(new LatLng(39.254987, -76.707814));
        corners_thirteen.add(new LatLng(39.254796, -76.707830));
        corners_thirteen.add(new LatLng(39.254584, -76.708029));
        corners_thirteen.add(new LatLng(39.254796, -76.708624));

        ArrayList<LatLng> corners_fourteen = new ArrayList<LatLng>();
        corners_fourteen.add(new LatLng(39.253961, -76.709928));
        corners_fourteen.add(new LatLng(39.253222, -76.709322));
        corners_fourteen.add(new LatLng(39.253060, -76.709751));
        corners_fourteen.add(new LatLng(39.253762, -76.710320));

        ArrayList<LatLng> corners_fifteen = new ArrayList<LatLng>();
        corners_fifteen.add(new LatLng(39.256611, -76.706999));
        corners_fifteen.add(new LatLng(39.256487, -76.706988));
        corners_fifteen.add(new LatLng(39.256354, -76.706527));
        corners_fifteen.add(new LatLng(39.256611, -76.706548));

        ArrayList<LatLng> corners_sixteen = new ArrayList<LatLng>();
        corners_sixteen.add(new LatLng(39.256603, -76.708410));
        corners_sixteen.add(new LatLng(39.256433, -76.707847));
        corners_sixteen.add(new LatLng(39.256092, -76.707981));
        corners_sixteen.add(new LatLng(39.256329, -76.708646));

        ArrayList<LatLng> corners_seventeen = new ArrayList<LatLng>();
        corners_seventeen.add(new LatLng(39.257853, -76.708034));
        corners_seventeen.add(new LatLng(39.257741, -76.707686));
        corners_seventeen.add(new LatLng(39.257537, -76.707789));
        corners_seventeen.add(new LatLng(39.257658, -76.708186));

        ArrayList<LatLng> corners_eighteen = new ArrayList<LatLng>();
        corners_eighteen.add(new LatLng(39.257726, -76.712255));
        corners_eighteen.add(new LatLng(39.257344, -76.711842));
        corners_eighteen.add(new LatLng(39.256850, -76.712555));
        corners_eighteen.add(new LatLng(39.257265, -76.712995));

        ArrayList<LatLng> corners_nineteen = new ArrayList<LatLng>();
        corners_nineteen.add(new LatLng(39.257307, -76.710672));
        corners_nineteen.add(new LatLng(39.257170, -76.710372));
        corners_nineteen.add(new LatLng(39.256925, -76.710479));
        corners_nineteen.add(new LatLng(39.256954, -76.710533));
        corners_nineteen.add(new LatLng(39.256933, -76.710586));
        corners_nineteen.add(new LatLng(39.257070, -76.710854));
        
        ParkingLot parkingLot = new ParkingLot(1, "Commons", 0, 3);
        parkingLot.setCorners(corners_one);
        parkingLots.add(parkingLot);

        parkingLot = new ParkingLot(2, "Lot 3", 0, 4);
        parkingLot.setCorners(corners_two);
        parkingLots.add(parkingLot);

        parkingLot = new ParkingLot(3, "Drayton Lot", 0, 4);
        parkingLot.setCorners(corners_three);
        parkingLots.add(parkingLot);

        parkingLot = new ParkingLot(4, "Lot 24", 0, 4);
        parkingLot.setCorners(corners_four);
        parkingLots.add(parkingLot);

        parkingLot = new ParkingLot(5, "TRC Lot", 0, 4);
        parkingLot.setCorners(corners_five);
        parkingLots.add(parkingLot);

        parkingLot = new ParkingLot(6, "4805 Lot", 0, 4);
        parkingLot.setCorners(corners_six);
        parkingLots.add(parkingLot);

        parkingLot = new ParkingLot(7, "Lot 23", 0, 4);
        parkingLot.setCorners(corners_seven);
        parkingLots.add(parkingLot);

        parkingLot = new ParkingLot(8, "Parking Services", 0, 4);
        parkingLot.setCorners(corners_eight);
        parkingLots.add(parkingLot);

        parkingLot = new ParkingLot(9, "Stadium Lot 1", 0, 4);
        parkingLot.setCorners(corners_nine);
        parkingLots.add(parkingLot);

        parkingLot = new ParkingLot(10, "Stadium Lot 2", 0, 4);
        parkingLot.setCorners(corners_ten);
        parkingLots.add(parkingLot);

        parkingLot = new ParkingLot(11, "Lot 1", 0, 4);
        parkingLot.setCorners(corners_eleven);
        parkingLots.add(parkingLot);

        parkingLot = new ParkingLot(12, "Lot 2", 0, 4);
        parkingLot.setCorners(corners_twelve);
        parkingLots.add(parkingLot);

        parkingLot = new ParkingLot(13, "Lot 4", 0, 4);
        parkingLot.setCorners(corners_thirteen);
        parkingLots.add(parkingLot);

        parkingLot = new ParkingLot(14, "Commons Garage", 0, 4);
        parkingLot.setCorners(corners_fourteen);
        parkingLots.add(parkingLot);

        parkingLot = new ParkingLot(15, "Lot 12", 0, 4);
        parkingLot.setCorners(corners_fifteen);
        parkingLots.add(parkingLot);

        parkingLot = new ParkingLot(16, "Lot 11", 0, 4);
        parkingLot.setCorners(corners_sixteen);
        parkingLots.add(parkingLot);

        parkingLot = new ParkingLot(17, "Lot 5", 0, 4);
        parkingLot.setCorners(corners_seventeen);
        parkingLots.add(parkingLot);

        parkingLot = new ParkingLot(18, "Walker Avenue Garage", 0, 4);
        parkingLot.setCorners(corners_eighteen);
        parkingLots.add(parkingLot);

        parkingLot = new ParkingLot(19, "Lot 7", 0, 4);
        parkingLot.setCorners(corners_nineteen);
        parkingLots.add(parkingLot);

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

    private void createPermitGroups(SQLiteDatabase db) {
        List<PermitGroup> permits = new ArrayList<PermitGroup>();

        permits.add(new PermitGroup(1, "Commuter Student", "A", "red"));
        permits.add(new PermitGroup(2, "Walker Community Student", "B", "green"));
        permits.add(new PermitGroup(3, "Residential Student (Besides Walker)", "C", "yellow"));
        permits.add(new PermitGroup(4, "Faculty/Staff", "D", "violet"));
        permits.add(new PermitGroup(5, "Gated Faculty/Staff", "E", "violet"));
        permits.add(new PermitGroup(6, "Freshman Resident Student", "F", "orange"));
        permits.add(new PermitGroup(7, "Visitor Parking (Metered Spaces)", "P", "blue"));
        permits.add(new PermitGroup(8, "Event Visitor Parking", "PE", "blue"));
        permits.add(new PermitGroup(9, "Handicap Accessible Parking", "PH", "blue"));
        permits.add(new PermitGroup(10, "Electric Vehicle Charging Station", "EV", "dark_green"));

        for (PermitGroup permit : permits) {
            SQLiteStatement statement = db.compileStatement(INSERT_PERMIT_GROUP);
            statement.bindLong(1, permit.getId());
            statement.bindString(2, permit.getName());
            statement.bindString(3, permit.getLetter());
            statement.bindString(4, permit.getColor());
            statement.executeInsert();
        }
        Log.d(TAG, "Permit groups created.");
    }

    private void createParkingPermits(SQLiteDatabase db) {
        long[][] parking_permit_relation = {{1, 1}, {1, 2}, {1, 5}, {2, 1}, {2, 2}, {2, 4}};

        for (int i = 0; i < parking_permit_relation.length; i++) {
            SQLiteStatement statement = db.compileStatement(INSERT_PARKING_PERMIT);
            statement.bindLong(1, parking_permit_relation[i][0]);
            statement.bindLong(2, parking_permit_relation[i][1]);
            statement.executeInsert();
        }
        Log.d(TAG, "Parking - Permit Group relations created.");
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

                    ParkingLot lot = new ParkingLot(lotId, name, count, capacity);
                    lot.setCorners(getCorners(db, lotId));
                    lot.setPermitGroups(getPermitGroups(db, lotId));

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

    public ArrayList<PermitGroup> getPermitGroups() {
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + TABLE_PERMIT_GROUP, null);
        ArrayList<PermitGroup> permits = new ArrayList<PermitGroup>();

        if (c != null) {
            if (c.moveToFirst()) {
                do {
                    long id = c.getLong(c.getColumnIndex(COLUMN_PERMIT_GROUP_ID));
                    String name = c.getString(c.getColumnIndex(COLUMN_NAME));
                    String letter = c.getString(c.getColumnIndex(COLUMN_PERMIT_LETTER));
                    String color = c.getString(c.getColumnIndex(COLUMN_PERMIT_COLOR));
                    permits.add(new PermitGroup(id, name, letter, color));
                } while (c.moveToNext());
            }
        }
        return permits;
    }

    public ArrayList<PermitGroup> getPermitGroups(SQLiteDatabase db, long lotId) {
        ArrayList<PermitGroup> permits = new ArrayList<PermitGroup>();

        String query = "SELECT * FROM " + TABLE_PARKING_PERMIT + " A INNER JOIN " +
                TABLE_PERMIT_GROUP + " B ON A." + COLUMN_PERMIT_GROUP_ID + "=B." +
                COLUMN_PERMIT_GROUP_ID + " WHERE A." + COLUMN_PARKING_LOT_ID + "=?";

        Cursor c = db.rawQuery(query, new String[]{String.valueOf(lotId)});

        if (c != null) {
            if (c.moveToFirst()) {
                do {
                    long id = c.getLong(c.getColumnIndex(COLUMN_PERMIT_GROUP_ID));
                    String name = c.getString(c.getColumnIndex(COLUMN_NAME));
                    String letter = c.getString(c.getColumnIndex(COLUMN_PERMIT_LETTER));
                    String color = c.getString(c.getColumnIndex(COLUMN_PERMIT_COLOR));
                    permits.add(new PermitGroup(id, name, letter, color));
                } while (c.moveToNext());
            }
        }
        return permits;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

}
