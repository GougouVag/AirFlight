package android.uom.gr.airflight;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LamPrOs on 16/1/2017.
 */

public class MyDBNotifications extends SQLiteOpenHelper {

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "notificationManager";

    // Contacts table name
    private static final String TABLE_NOTIFICATION= "table_notification";

    // Contacts Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_DATE = "date";
    private static final String KEY_NOTIFICATION = "notification";

    public MyDBNotifications(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_NOTIFICATION + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_DATE + " TEXT,"
                + KEY_NOTIFICATION + " TEXT" + ")";
        db.execSQL(CREATE_CONTACTS_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NOTIFICATION);

        // Create tables again
        onCreate(db);
    }

    /**
     * All CRUD(Create, Read, Update, Delete) Operations
     */

    // Adding new contact
    void addContact(ItemForNotification itemForNotification) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_DATE, itemForNotification.getDate()); // Contact Name
        values.put(KEY_NOTIFICATION, itemForNotification.getNotificationText()); // Contact Phone

        // Inserting Row
        db.insert(TABLE_NOTIFICATION, null, values);
        db.close(); // Closing database connection
    }

    // Getting single contact
    ItemForNotification getContact(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_NOTIFICATION, new String[] { KEY_ID,
                        KEY_DATE, KEY_NOTIFICATION }, KEY_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        ItemForNotification itemForNotification = new ItemForNotification(Integer.parseInt(cursor.getString(0)),cursor.getString(1),cursor.getString(2));
        // return contact
        return itemForNotification;
    }

    // Getting All Contacts
    public List<ItemForNotification> getAllContacts() {
        List<ItemForNotification> contactList = new ArrayList<ItemForNotification>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_NOTIFICATION;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                ItemForNotification itemForNotification = new ItemForNotification(0,null,null);
                itemForNotification.setID(Integer.parseInt(cursor.getString(0)));
                itemForNotification.setDate(cursor.getString(1));
                itemForNotification.setNotificationText(cursor.getString(2));
                // Adding contact to list
                contactList.add(itemForNotification);
            } while (cursor.moveToNext());
        }

        // return contact list
        return contactList;
    }

    // Updating single contact
    public int updateContact(ItemForNotification itemForNotification) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_DATE, itemForNotification.getDate());
        values.put(KEY_NOTIFICATION, itemForNotification.getNotificationText());

        // updating row
        return db.update(TABLE_NOTIFICATION, values, KEY_ID + " = ?",
                new String[] { String.valueOf(itemForNotification.getID()) });
    }

    // Deleting single contact
    public void deleteContact(ItemForNotification itemForNotification) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NOTIFICATION, KEY_ID + " = ?",
                new String[] { String.valueOf(itemForNotification.getID()) });
        db.close();
    }


    // Getting contacts Count
    public int getContactsCount() {
        String countQuery = "SELECT  * FROM " + TABLE_NOTIFICATION;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();

        // return count
        return cursor.getCount();
    }

}
