package dev.zopa.safe;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


/**
 * Created by zOpa
 */
public class DBHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 2;
    public static final String DATABASE_NAME = "buttonsDB";
    public static final String TABLE_NAME = "button";

    public static final String NAME = "name";
    public static final String KEY_ID = "_id";
    public static final String POSITION = "position";
    public static final String BARCODE = "barcode";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        // create table
        db.execSQL("create table " + TABLE_NAME + "("
                + KEY_ID + " integer primary key,"
                + NAME + " text,"
                + POSITION + " integer,"
                + BARCODE + " text" + ");");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists " + TABLE_NAME);

        onCreate(db);
    }
}
