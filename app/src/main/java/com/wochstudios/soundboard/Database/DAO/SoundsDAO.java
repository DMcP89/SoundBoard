package com.wochstudios.soundboard.Database.DAO;

import android.database.*;
import android.content.*;
import android.database.sqlite.*;

import com.wochstudios.soundboard.Database.SounboardContract.SoundsTable;

public class SoundsDAO implements SoundboardDbDAO
{

	@Override
	public boolean insert(SQLiteDatabase db, ContentValues v)
	{
		// TODO: Implement this method
		return false;
	}

	@Override
	public Cursor read()
	{
		// TODO: Implement this method
		return null;
	}

	@Override
	public void delete()
	{
		// TODO: Implement this method
	}

	@Override
	public boolean update()
	{
		// TODO: Implement this method
		return false;
	}

}
