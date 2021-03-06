package com.wochstudios.InfiniteSoundboards.adapters;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.wochstudios.InfiniteSoundboards.database.SoundboardContract;
import com.wochstudios.InfiniteSoundboards.models.Sound;
import com.wochstudios.InfiniteSoundboards.R;

/**
 * Created by dave on 9/7/2015.
 */
public class SoundCursorAdapter extends CursorAdapter {

    public SoundCursorAdapter(Context con,Cursor cursor, int flags){
        super(con,cursor,flags);

    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_item, viewGroup, false);
        return view;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView soundText = (TextView)view;
        soundText.setText(cursor.getString(cursor.getColumnIndex(SoundboardContract.SoundsTable.COLUMN_TITLE)));
    }

    public Sound getSound(int postion){
        Cursor cursor = getCursor();
        Sound sound = new Sound();
        if(cursor.moveToPosition(postion)){
            sound.setTitle(cursor.getString(cursor.getColumnIndex(SoundboardContract.SoundsTable.COLUMN_TITLE)));
            sound.setID(cursor.getInt(cursor.getColumnIndex(SoundboardContract.SoundsTable._ID)));
            sound.setSoundboardId(cursor.getString(cursor.getColumnIndex(SoundboardContract.SoundsTable.COLUMN_SOUNDBOARD_ID)));
            sound.setUri(Uri.parse(cursor.getString(cursor.getColumnIndex(SoundboardContract.SoundsTable.COLUMN_URI))));
        }
        return sound;
    }

/*    private Sound convertRowToSound(Cursor cursor){
        int id = cursor.getInt(cursor.getColumnIndex(SoundboardContract.SoundsTable._ID));
        String title = cursor.getString(cursor.getColumnIndex(SoundboardContract.SoundsTable.COLUMN_TITLE));
        String soundboard_id = cursor.getString(cursor.getColumnIndex(SoundboardContract.SoundsTable.COLUMN_SOUNDBOARD_ID));
        String file_uri = cursor.getString(cursor.getColumnIndex(SoundboardContract.SoundsTable.COLUMN_URI));
        return new Sound(id,title, Uri.parse(file_uri), soundboard_id);
    }*/
}
