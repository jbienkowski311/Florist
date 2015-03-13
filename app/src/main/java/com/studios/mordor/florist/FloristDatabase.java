package com.studios.mordor.florist;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

/**
 * Created by User on 13/03/2015.
 */
public class FloristDatabase extends SQLiteOpenHelper {
//    private static final String TAG = FloristDatabase.class.getSimpleName();

    private static final String DATABASE_NAME = "florist.db";
    private static int DATABASE_VERSION = 2;
    private final Context mContext;

    interface Tables {
        String FLORIST = "florist";
    }

    public FloristDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF EXISTS " + DATABASE_NAME + " ("
        + BaseColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
        + FloristContract.Florist.FLORIST_NAME + "TEXT NOT NULL,"
        + FloristContract.Florist.FLORIST_OCCASION + "TEXT NOT NULL,"
        + FloristContract.Florist.FLORIST_TYPE + "TEXT NOT NULL,"
        + FloristContract.Florist.FLORIST_COLORS + "TEXT NOT NULL,"
        + FloristContract.Florist.FLORIST_ORIGIN + "TEXT NOT NULL,"
        + FloristContract.Florist.FLORIST_MEANING + "TEXT NOT NULL,"
        + FloristContract.Florist.FLORIST_AVAILABILITY + "TEXT NOT NULL");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        int version = oldVersion;
        if(version == 1) {
            //update
            version = 2;
        }

        if(version != DATABASE_VERSION) {
            db.execSQL("DROP TABLE IF EXISTS" + DATABASE_NAME);
            onCreate(db);
        }
    }

    public static void deleteDatabase(Context context) {
        context.deleteDatabase(DATABASE_NAME);
    }
}
