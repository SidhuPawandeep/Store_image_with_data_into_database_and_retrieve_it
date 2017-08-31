package com.example.lenovo.logindemo2.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.lenovo.logindemo2.pojoclasses.LoginPojo;

import java.util.ArrayList;

/**
 * Created by lENOVO on 8/31/2017.
 */

public class ImageDatabase extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;


    private static final String DATABASE_NAME = "image_database";

    private static final String TABLE_NAME = "images";

    // datas Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_PWD = "pwd";
    private static final String KEY_IMGAGE="image";
    private static final String KEY_USER_ID="user_id";


    public ImageDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {

        String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_USER_ID + " INTEGER,"
                + KEY_IMGAGE + " BLOB" + ")";

        db.execSQL(CREATE_TABLE);
       /* String CREATE_data_TABLE = "CREATE TABLE " + TABLE_NAME + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAME + " TEXT,"
                + KEY_EMAIL + " TEXT," +KEY_PWD+ "TEXT"+")";
        db.execSQL(CREATE_data_TABLE);*/
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);

        // Create tables again
        onCreate(db);
    }

    /**
     * All CRUD(Create, Read, Update, Delete) Operations
     */

    // Adding new data
    public int addImage(LoginPojo data) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_USER_ID, data.getUser_id()); // data Name
        values.put(KEY_IMGAGE, data.getImage());
        // Inserting Row
        db.insert(TABLE_NAME, null, values);
        db.close(); // Closing database connection
        return 1;
    }

    // Getting single data
      /*  public LoginPojo getSingleData(String email_id) {
            SQLiteDatabase db = this.getReadableDatabase();

            Cursor cursor = db.query(TABLE_NAME, new String[]{KEY_ID,
                            KEY_NAME, KEY_EMAIL,KEY_PHONE,KEY_IMGAGE}, KEY_EMAIL + "=?",
                    new String[]{String.valueOf(email_id)}, null, null, null, null);


            if (cursor != null)
                cursor.moveToFirst();

            LoginPojo data = new LoginPojo(Integer.parseInt(cursor.getString(0)),
                    cursor.getString(1), cursor.getString(2), cursor.getString(3),cursor.getBlob(1));
            // return data
            return data;
        }
*/
    public LoginPojo getPojoData(int user_id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_NAME, new String[]{KEY_ID, KEY_USER_ID, KEY_IMGAGE }, KEY_USER_ID + "=?",
                new String[]{String.valueOf(user_id)}, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        LoginPojo contact = new LoginPojo(cursor.getInt(0),
                cursor.getInt(1), cursor.getBlob(2));
        return contact;
        //  return contact;
    }
    public ArrayList<LoginPojo> getPojoData1(int user_id) {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<LoginPojo> dataList=new ArrayList<>();
        Cursor cursor = db.query(TABLE_NAME, new String[]{KEY_ID, KEY_USER_ID, KEY_IMGAGE }, KEY_USER_ID + "=?",
                new String[]{String.valueOf(user_id)}, null, null, null, null);
        if (cursor != null)
        {
            if (cursor.moveToFirst()) {
                do {
                    LoginPojo data = new LoginPojo();
                    data.setId(Integer.parseInt(cursor.getString(0)));
                    data.setUser_id(cursor.getInt(1));

                    data.setImage(cursor.getBlob(2));
                    //data.setPassword(cursor.getString(3));
                    // Adding data to list
                    dataList.add(data);
                } while (cursor.moveToNext());
            }

        //  return contact;
    }
        return dataList;
    }

    // Getting All datas
    public ArrayList<LoginPojo> getAlldatas() {
        ArrayList<LoginPojo> dataList=new ArrayList<>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_NAME;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                LoginPojo data = new LoginPojo();
                data.setId(Integer.parseInt(cursor.getString(0)));
                data.setUser_id(cursor.getInt(1));

                data.setImage(cursor.getBlob(2));
                //data.setPassword(cursor.getString(3));
                // Adding data to list
                dataList.add(data);
            } while (cursor.moveToNext());
        }

        // return data list
        return dataList;
    }

    // Updating single user
    public int updateRegisterDB(LoginPojo data) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_USER_ID, data.getUser_id());
        values.put(KEY_IMGAGE,data.getImage());

        // updating row
        return db.update(TABLE_NAME, values, KEY_ID + " = ?",
                new String[]{String.valueOf(data.getId())});
    }

    public void deleteUser(LoginPojo data) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, KEY_ID + " = ?",
                new String[]{String.valueOf(data.getId())});
        db.close();
    }


    // Getting datas Count
    public int getRegisterCount() {
        String countQuery = "SELECT  * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();

        // return count
        return cursor.getCount();
    }


    public boolean checkUser(String email) {

        // array of columns to fetch
        String[] columns = {
                KEY_ID
        };
        SQLiteDatabase db = this.getReadableDatabase();

        // selection criteria
        String selection = KEY_EMAIL + " = ?";

        // selection argument
        String[] selectionArgs = {email};

        // query user table with condition
        /**
         * Here query function is used to fetch records from user table this function works like we use sql query.
         * SQL query equivalent to this query function is
         * SELECT user_id FROM user WHERE user_email = 'jack@androidtutorialshub.com';
         */
        Cursor cursor = db.query(TABLE_NAME, //Table to query
                columns,                    //columns to return
                selection,                  //columns for the WHERE clause
                selectionArgs,              //The values for the WHERE clause
                null,                       //group the rows
                null,                      //filter by row groups
                null);                      //The sort order
        int cursorCount = cursor.getCount();
        cursor.close();
        db.close();

        if (cursorCount > 0) {
            return true;
        }

        return false;
    }

    public boolean checkLoginUser(String email, String pass) {

        // array of columns to fetch
        String[] columns = {
                KEY_ID, KEY_NAME, KEY_EMAIL
        };
        SQLiteDatabase db = this.getReadableDatabase();

        // selection criteria
        String selection = KEY_EMAIL + " = ? AND " + KEY_PWD + " = ?";

        // selection argument
        String[] selectionArgs = {email, pass};

        // query user table with condition
        /**
         * Here query function is used to fetch records from user table this function works like we use sql query.
         * SQL query equivalent to this query function is
         * SELECT user_id FROM user WHERE user_email = 'jack@androidtutorialshub.com';
         */
        Cursor cursor = db.query(TABLE_NAME, //Table to query
                columns,                    //columns to return
                selection,                  //columns for the WHERE clause
                selectionArgs,              //The values for the WHERE clause
                null,                       //group the rows
                null,                      //filter by row groups
                null);                      //The sort order
        int cursorCount = cursor.getCount();
        cursor.close();
        db.close();

        if (cursorCount > 0) {


            // return data
            //return data;
            return true;
        }

        return false;
    }
    public void deleteImage(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, KEY_ID + " = ?",
                new String[] { String.valueOf(id) });
        db.close();
    }

    // Update Password
    public int updateUserPassword(LoginPojo obj) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_PWD, obj.getPassword());

        // updating row
        return db.update(TABLE_NAME, values, KEY_EMAIL + " = ?",
                new String[]{String.valueOf(obj.getEmail())});
    }

    // Updating single contact
    public int updateContacts(LoginPojo contact) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_PWD, contact.getPassword());
        // values.put(KEY_PH_NO, contact.getPhoneNumber());

        // updating row
        return db.update(TABLE_NAME, values, KEY_EMAIL + " = ?",
                new String[]{String.valueOf(contact.getEmail())});
    }
    // Deleting single contact
    public void deleteUser1(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, KEY_ID + " = ?",
                new String[] { String.valueOf(id) });
        db.close();
    }
}


