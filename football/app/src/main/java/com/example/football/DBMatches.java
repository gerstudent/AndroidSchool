package com.example.football;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DBMatches {

    private static final String DATABASE_NAME = "simple.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "tableMatches";

    private static final String COLUMN_ID = "id";
    private static final String COLUMN_TEAMHOME = "TeamНоme";
    private static final String COLUMN_TEAMGUAST = "TeamGuest";
    private static final String COLUMN_GOALSHOME = "GoalsHome";
    private static final String COLUMN_GOALSGUAST = "GoalsGuast";

    private static final int NUM_COLUMN_ID = 0;
    private static final int NUM_COLUMN_TEAMHOME = 1;
    private static final int NUM_COLUMN_TEAMGUAST = 2;
    private static final int NUM_COLUMN_GOALSHOME = 3;
    private static final int NUM_COLUMN_GOALSGUEST = 4;

    private SQLiteDatabase mDataBase;

    public DBMatches(Context context) {
        OpenHelper mOpenHelper = new OpenHelper(context);
        mDataBase = mOpenHelper.getWritableDatabase();
    }

    public long insert(String teamhouse,String teamguest,int goalshouse,int goalsguest) {
        ContentValues cv=new ContentValues();
        cv.put(COLUMN_TEAMHOME, teamhouse);
        cv.put(COLUMN_TEAMGUAST, teamguest);
        cv.put(COLUMN_GOALSHOME, goalshouse);
        cv.put(COLUMN_GOALSGUAST,goalsguest);
        return mDataBase.insert(TABLE_NAME, null, cv);
    }

    public int update(Matches md) {
        ContentValues cv=new ContentValues();
        cv.put(COLUMN_TEAMHOME, md.getTeamhouse());
        cv.put(COLUMN_TEAMGUAST, md.getTeamguest());
        cv.put(COLUMN_GOALSHOME, md.getGoalshouse());
        cv.put(COLUMN_GOALSGUAST,md.getGoalsguest());
        return mDataBase.update(TABLE_NAME, cv, COLUMN_ID + " = ?",new String[] { String.valueOf(md.getId())});
    }

    public void deleteAll() {
        mDataBase.delete(TABLE_NAME, null, null);
    }

    public void delete(long id) {
        mDataBase.delete(TABLE_NAME, COLUMN_ID + " = ?", new String[] { String.valueOf(id) });
    }

    public Matches select(long id) {
        Cursor mCursor = mDataBase.query(TABLE_NAME, null, COLUMN_ID + " = ?", new String[]{String.valueOf(id)}, null, null, null);

        mCursor.moveToFirst();
        String TeamHome = mCursor.getString(NUM_COLUMN_TEAMHOME);
        String TeamGuest = mCursor.getString(NUM_COLUMN_TEAMGUAST);
        int GoalsHome = mCursor.getInt(NUM_COLUMN_GOALSHOME);
        int GoalsGuest=mCursor.getInt(NUM_COLUMN_GOALSGUEST);
        return new Matches(id, TeamHome, TeamGuest, GoalsHome,GoalsGuest);
    }

    public ArrayList<Matches> selectAll() {
        Cursor mCursor = mDataBase.query(TABLE_NAME, null, null, null, null, null, null);

        ArrayList<Matches> arr = new ArrayList<Matches>();
        mCursor.moveToFirst();
        if (!mCursor.isAfterLast()) {
            do {
                long id = mCursor.getLong(NUM_COLUMN_ID);
                String TeamHome = mCursor.getString(NUM_COLUMN_TEAMHOME);
                String TeamGuest = mCursor.getString(NUM_COLUMN_TEAMGUAST);
                int GoalsHome = mCursor.getInt(NUM_COLUMN_GOALSHOME);
                int GoalsGuest=mCursor.getInt(NUM_COLUMN_GOALSGUEST);
                arr.add(new Matches(id, TeamHome, TeamGuest, GoalsHome,GoalsGuest));
            } while (mCursor.moveToNext());
        }
        return arr;
    }

    private class OpenHelper extends SQLiteOpenHelper {

        OpenHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }
        @Override
        public void onCreate(SQLiteDatabase db) {
            String query = "CREATE TABLE " + TABLE_NAME + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_TEAMHOME+ " TEXT, " +
                    COLUMN_TEAMGUAST + " TEXT, " +
                    COLUMN_GOALSHOME + " INT,"+
                    COLUMN_GOALSGUAST+" INT);";
            db.execSQL(query);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
            onCreate(db);
        }
    }


}
