package storage;

import static android.provider.BaseColumns._ID;

import java.util.ArrayList;
import java.util.List;

import model.Entry;
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
    private Context context;

    public DataStorage(Context context) {
        super(context, DB_NAME, null, 1);
        this.context = context;
    }

    private static final String DATABASE_CREATE = "CREATE TABLE " + TABLE_NAME + " (" +
            _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            LATITUDE_COLUMN + " TEXT, " +
            LONGITUDE_COLUMN + " TEXT );";

    public void store(Entry issue) {
        SQLiteDatabase db = getWritableDatabase();
        SQLiteStatement statement = db.compileStatement("insert into " + TABLE_NAME + " (" + LATITUDE_COLUMN + "," + LONGITUDE_COLUMN + ") values ( ?, ? )");
        statement.bindDouble(1, issue.getLatitude());
        statement.bindDouble(2, issue.getLongitude());
        statement.executeInsert();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DATABASE_CREATE);
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
                    Entry entry = new Entry(latitude, longitude);
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
        Entry entry = null;
        if (c != null) {
            if (c.moveToFirst()) {
                do {
                    latitude = c.getDouble(c.getColumnIndex(LATITUDE_COLUMN));
                    longitude = c.getDouble(c.getColumnIndex(LONGITUDE_COLUMN));
                    entry = new Entry(latitude, longitude);
                } while (c.moveToNext());
            }
        }
        return entry;
    }
}