package com.studios.mordor.florist;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.provider.BaseColumns;
import android.text.TextUtils;
import android.util.Log;

/**
 * Created by User on 13/03/2015.
 */
public class FloristProvider extends ContentProvider {
    private FloristDatabase mOpenHelper;

    private static String TAG = FloristProvider.class.getSimpleName();
    private static final UriMatcher sUriMatcher = buildUriMatcher();

    private static final int FLORIST = 100;
    private static final int FLORIST_ID = 200;

    private static UriMatcher buildUriMatcher() {
        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = FloristContract.CONTENT_AUTHORITY;

        matcher.addURI(authority, "florist", FLORIST);
        matcher.addURI(authority, "florist/*", FLORIST_ID);
        return matcher;
    }

    @Override
    public boolean onCreate() {
        mOpenHelper = new FloristDatabase(getContext());
        return true;
    }

    private void deleteDatabase() {
        mOpenHelper.close();
        FloristDatabase.deleteDatabase(getContext());
        mOpenHelper = new FloristDatabase(getContext());
    }

    @Override
    public String getType(Uri uri) {
        final int match = sUriMatcher.match(uri);
        switch(match) {
            case FLORIST:
                return FloristContract.Florist.CONTENT_TYPE;
            case FLORIST_ID:
                return FloristContract.Florist.CONTENT_ITEM_TYPE;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        final SQLiteDatabase db = mOpenHelper.getReadableDatabase();
        final int match = sUriMatcher.match(uri);

        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
        queryBuilder.setTables(FloristDatabase.Tables.FLORIST);

        switch(match) {
            case FLORIST:
                //do nothing
                break;
            case FLORIST_ID:
                String id = FloristContract.Florist.getFlowerId(uri);
                queryBuilder.appendWhere(BaseColumns._ID + "=" + id);
                break;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }

        return queryBuilder.query(db, projection, selection, selectionArgs, null, null, sortOrder);
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        Log.v(TAG, "insert(uri=" + uri + ", values=" + values.toString());
        final SQLiteDatabase db = mOpenHelper.getReadableDatabase();
        final int match = sUriMatcher.match(uri);

        switch(match) {
            case FLORIST:
                long recordId = db.insertOrThrow(FloristDatabase.Tables.FLORIST, null, values);
                return FloristContract.Florist.buildFloristUri(String.valueOf(recordId));
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        Log.v(TAG, "update(uri=" + uri + ", values=" + values.toString());
        final SQLiteDatabase db = mOpenHelper.getReadableDatabase();
        final int match = sUriMatcher.match(uri);
        String selectionCriteria = selection;

        switch(match) {
            case FLORIST:
                //nothing
                break;
            case FLORIST_ID:
                String id = FloristContract.Florist.getFlowerId(uri);
                selectionCriteria = BaseColumns._ID + "=" + id + (!TextUtils.isEmpty(selection) ? " AND (" + selection + ")" : "");
                break;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }

        return db.update(FloristDatabase.Tables.FLORIST, values, selectionCriteria, selectionArgs);
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        Log.v(TAG, "delete(uri=" + uri);

        if(uri.equals(FloristContract.BASE_CONTENT_URI)) {
            deleteDatabase();
            return 0;
        }
        final SQLiteDatabase db = mOpenHelper.getReadableDatabase();
        final int match = sUriMatcher.match(uri);

        switch(match) {
            case FLORIST_ID:
                String id = FloristContract.Florist.getFlowerId(uri);
                String selectionCriteria = BaseColumns._ID + "=" + id + (!TextUtils.isEmpty(selection) ? " AND (" + selection + ")" : "");
                return db.delete(FloristDatabase.Tables.FLORIST, selectionCriteria, selectionArgs);
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
    }
}
