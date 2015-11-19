package com.wochstudios.soundboard.Controllers;

import android.content.ContentValues;
import android.content.Context;
import android.util.Log;

import com.wochstudios.soundboard.Database.SounboardContract.SoundboardsTable;
import com.wochstudios.soundboard.Database.SounboardContract.SoundsTable;
import com.wochstudios.soundboard.Database.SoundboardDBHelper;
import com.wochstudios.soundboard.Models.Soundboard;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class DatabaseController
{
	private SoundboardDBHelper mDbHelper;
	
	public DatabaseController(Context con){
		mDbHelper = new  SoundboardDBHelper(con);
		//Add new soundboard for testing.
		//createTestSoundboard();
	}
	
	public void addSoundboardToDatabase(String name){
        Log.i("DatabaseController", "Adding Sounboard:"+name);
		ContentValues v = new ContentValues();
		v.put(SoundboardsTable.COLUMN_NAME,name);
		v.put(SoundboardsTable.COLUMN_DATE_CREATED,
					new SimpleDateFormat("mm/dd/yyyy").format(new Date()).toString());
		mDbHelper.insertIntoDatabase(SoundboardsTable.TABLE_NAME,v);
	}
	
	
	public void addSoundToSoundboard(String title, String uri, String sbid){
		Log.i("DatabaseController", "Adding Song:"+title+"to Soundboard: "+sbid);
		ContentValues v = new ContentValues();
		v.put(SoundsTable.COLUMN_TITLE, title);
		v.put(SoundsTable.COLUMN_URI, uri);
		v.put(SoundsTable.COLUMN_SOUNDBOARD_ID, sbid);
		mDbHelper.insertIntoDatabase(SoundsTable.TABLE_NAME,v);
	}
	
	public void removeSoundFromSoundboard(String id){
        mDbHelper.removeFromDatabase(SoundsTable.TABLE_NAME, id);
    }
	
	public void removeSoundboardFromDatabase(String id){
		mDbHelper.removeFromDatabase(SoundboardsTable.TABLE_NAME,id);
	}
	
		
	public Soundboard getSoundboard(String id){
		Soundboard s = mDbHelper.findSoundboard(id);
		return s;
	}

    public ArrayList<Soundboard> getSoundboards(){
        return  mDbHelper.getAllSoundboards();
    }
	
	public Soundboard getSoundboardFromTitle(String title){
		Soundboard s = mDbHelper.findSoundboardFromTitle(title);
		return s;
	}
	
	
	public boolean checkForSoundboards(){
		Log.d("DatabaseController", "Number of Soundboards: "+mDbHelper.getCountOfSoundboards());
        return mDbHelper.getCountOfSoundboards() > 0;
	}
	
	public ArrayList<String> getSoundboardNames(){
		ArrayList<String> temp = mDbHelper.getSoundboardNames();
		return temp;
	}
	
	public ArrayList<String> getSoundboardIds(){
		return mDbHelper.getSoundboardIds();
	}

    public void renameSoundboard(String newName, String soundboardId) {
        mDbHelper.renameSoundboard(newName, soundboardId);
    }
}
