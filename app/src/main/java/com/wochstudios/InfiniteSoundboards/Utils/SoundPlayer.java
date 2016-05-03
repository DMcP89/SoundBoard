package com.wochstudios.infinitesoundboards.utils;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.IBinder;
import android.util.Log;

import com.wochstudios.infinitesoundboards.service.MediaPlayerService;

import java.io.FileDescriptor;
import java.io.FileNotFoundException;
import java.io.IOException;

public class SoundPlayer implements MediaPlayer.OnCompletionListener {
	private MediaPlayer mp;
	private MediaPlayerService service;
	private Intent mediaPlayerIntent;
	private boolean bound;
	
	public SoundPlayer(){
		mp = new MediaPlayer();
        mp.setLooping(false);
	}
	
	public boolean playSound(Context con, Uri uri){
		try {
			if(!mp.isPlaying()){
                mp.setDataSource(getFileDescriptorFromUri(con, uri));
                mp.prepare();
				mp.start();
                mp.setOnCompletionListener(this);
				return true;
			}
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			return false;
		} catch (IllegalStateException e) {
			e.printStackTrace();
			return false;
		} catch(IOException e){
            e.printStackTrace();
            return false;
        }
        return false;
	}

    @Override
    public void onCompletion(MediaPlayer mediaPlayer) {
        Log.d(this.getClass().getSimpleName(),"On Complete");
        mediaPlayer.stop();
        mediaPlayer.reset();
    }


    private static FileDescriptor getFileDescriptorFromUri(Context context, Uri uri){
        FileDescriptor fd = null;
        try {
            fd = context.getContentResolver().openAssetFileDescriptor(uri,"r").getFileDescriptor();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return fd;
    }




}
