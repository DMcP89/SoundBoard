package com.wochstudios.soundboard.utils;

import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;
import android.provider.MediaStore;

import java.io.File;



public class RingtoneMaker {
	public void setRingtone(String name, Uri uri, Context con){
		File k = new File(uri.toString());   
		addURIToContentResolver(k,getContentValues(k,name),con);
	}
	
	private ContentValues getContentValues(File k, String name){
		ContentValues values = new ContentValues();
		values.put(MediaStore.MediaColumns.DATA, k.getAbsolutePath());
		values.put(MediaStore.MediaColumns.TITLE, name);
		values.put(MediaStore.MediaColumns.SIZE, k.length());
		values.put(MediaStore.MediaColumns.MIME_TYPE, "audio/mp3");
		values.put(MediaStore.Audio.Media.ARTIST, "Soundboard");
		values.put(MediaStore.Audio.Media.IS_RINGTONE, true);
		values.put(MediaStore.Audio.Media.IS_NOTIFICATION, true);
		values.put(MediaStore.Audio.Media.IS_ALARM, true);
		values.put(MediaStore.Audio.Media.IS_MUSIC, false);
		return values;
	}
	
	private void addURIToContentResolver(File k, ContentValues values, Context con){
		Uri uri = MediaStore.Audio.Media.getContentUriForPath(k.getAbsolutePath());
		con.getContentResolver().insert(uri, values);	
	}	
}
