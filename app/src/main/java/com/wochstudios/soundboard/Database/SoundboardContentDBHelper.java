package com.wochstudios.soundboard.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by dave on 8/6/2015.
 */
public class SoundboardContentDBHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "Soundboard.db";

    public SoundboardContentDBHelper(Context context ){
        super(context,"", null, 0);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SounboardContract.SoundboardsTable.SQL_CREATE_TABLE_SOUNDBOARDS);
        db.execSQL(SounboardContract.SoundsTable.SQL_CREATE_TABLE_SOUNDS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SounboardContract.SoundboardsTable.SQL_DELETE_TABLE_SOUNDBOARDS);
        db.execSQL(SounboardContract.SoundsTable.SQL_DELETE_TABLE_SOUNDS);
        onCreate(db);
    }
}
