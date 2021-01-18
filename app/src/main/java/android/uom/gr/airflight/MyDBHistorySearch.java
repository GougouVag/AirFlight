package android.uom.gr.airflight;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LamPrOs on 15/1/2017.
 */

public class MyDBHistorySearch extends SQLiteOpenHelper {

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "historyManager";

    // Contacts table name
    private static final String TABLE_HISTORY = "history";

    // Contacts Table Columns names
    private static final String KEY_ID = "id";
    public static final String FROM = "fromGo";
    public static final String TO = "toGo";
    public static final String ADULTS_NUMBER = "adults_number";
    public static final String CHILDREN_NUMBER = "children_number";
    public static final String INFANTS_NUMBER = "infants_number";
    public static final String WAY_IS = "way_is";
    public static final String DEPART_DAY = "depart_day";
    public static final String ARRIVAL_DAY = "arrival_day";

    public MyDBHistorySearch(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_HISTORY_TABLE = "CREATE TABLE " + TABLE_HISTORY + "("
                + KEY_ID +
                FROM + " TEXT NOT NULL , " +
                TO + " TEXT NOT NULL , " +
                ADULTS_NUMBER + " TEXT NOT NULL , " +
                CHILDREN_NUMBER + " TEXT NOT NULL , " +
                INFANTS_NUMBER + " TEXT NOT NULL , " +
                WAY_IS + " TEXT NOT NULL , " +
                DEPART_DAY + " TEXT NOT NULL , " +
                ARRIVAL_DAY + " TEXT NOT NULL , " + " TEXT" + ")";
        db.execSQL(CREATE_HISTORY_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_HISTORY);

        // Create tables again
        onCreate(db);
    }

    /**
     * All CRUD(Create, Read, Update, Delete) Operations
     */

    // Adding new contact
    void addContact(ItemForHistory itemForHistory) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(FROM,itemForHistory.getFrom());
        values.put(TO,itemForHistory.getTo());
        values.put(ADULTS_NUMBER,itemForHistory.getAdults_number());
        values.put(CHILDREN_NUMBER,itemForHistory.getChildren_number());
        values.put(INFANTS_NUMBER,itemForHistory.getInfants());
        values.put(WAY_IS,itemForHistory.getWay_is());
        values.put(DEPART_DAY,itemForHistory.getDepart_day());
        values.put(ARRIVAL_DAY,itemForHistory.getArrival_day());

        // Inserting Row
        db.insert(TABLE_HISTORY, null, values);
        db.close();; // Closing database connection
    }

    // Getting single contact
    ItemForHistory getContact(int id) {
        SQLiteDatabase db = this.getReadableDatabase();




        Cursor cursor = db.query(TABLE_HISTORY, new String[] { KEY_ID,
                        FROM, TO,ADULTS_NUMBER,CHILDREN_NUMBER,INFANTS_NUMBER,WAY_IS,DEPART_DAY,ARRIVAL_DAY }, KEY_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        ItemForHistory itemForHistory = new ItemForHistory(Integer.parseInt(cursor.getString(0)),cursor.getString(1), cursor.getString(3),cursor.getString(4),cursor.getString(5),cursor.getString(6),cursor.getString(7),cursor.getString(8),cursor.getString(9));
        // return contact
        return itemForHistory;
    }

    // Getting All Contacts
    public List<ItemForHistory> getAllContacts() {
        List<ItemForHistory> historyList = new ArrayList<ItemForHistory>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_HISTORY;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                ItemForHistory itemForHistory = new ItemForHistory(0,null,null,null,null,null,null,null,null);
                itemForHistory.setId(Integer.parseInt(cursor.getString(0)));
                itemForHistory.setFrom(cursor.getString(1));
                itemForHistory.setTo(cursor.getString(2));
                itemForHistory.setAdults_number(cursor.getString(3));
                itemForHistory.setChildren_number(cursor.getString(4));
                itemForHistory.setInfants(cursor.getString(5));
                itemForHistory.setWay_is(cursor.getString(6));
                itemForHistory.setDepart_day(cursor.getString(7));
                itemForHistory.setArrival_day(cursor.getString(8));
                // Adding contact to list
                historyList.add(itemForHistory);
            } while (cursor.moveToNext());
        }

        // return contact list
        return historyList;
    }

    // Updating single contact
    public int updateContact(ItemForHistory itemForHistory) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(FROM,itemForHistory.getFrom());
        values.put(TO,itemForHistory.getTo());
        values.put(ADULTS_NUMBER,itemForHistory.getAdults_number());
        values.put(CHILDREN_NUMBER,itemForHistory.getChildren_number());
        values.put(INFANTS_NUMBER,itemForHistory.getInfants());
        values.put(WAY_IS,itemForHistory.getWay_is());
        values.put(DEPART_DAY,itemForHistory.getDepart_day());
        values.put(ARRIVAL_DAY,itemForHistory.getArrival_day());

        // updating row
        return db.update(TABLE_HISTORY, values, KEY_ID + " = ?",
                new String[] { String.valueOf(itemForHistory.getId()) });
    }

    // Deleting single contact
    public void deleteContact(ItemForHistory itemForHistory) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_HISTORY, KEY_ID + " = ?",
                new String[] { String.valueOf(itemForHistory.getId()) });
        db.close();
    }


    // Getting contacts Count
    public int getContactsCount() {
        String countQuery = "SELECT  * FROM " + TABLE_HISTORY;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();

        // return count
        return cursor.getCount();
    }

}