package com.wochstudios.soundboard.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import android.os.Environment;

public class FileWriter {
	
	public File writeFile(InputStream stream,String name){
		 	
		 try {
			String filename = Environment.getExternalStorageDirectory().toString()+File.separator+"download"+File.separator+name+".mp3";
			copyInputStreamToOutputStream(stream, filename);   
		   } catch (IOException io) {
			   return null;
		   }
		   return new File(Environment.getExternalStorageDirectory().toString()+File.separator+"download"+File.separator+name+".mp3");
		    
	}
	
	private void copyInputStreamToOutputStream(InputStream stream, String file){
		FileOutputStream fos = new FileOutputStream(file);
		fos.write(getBuffeFromInputStream(stream));
		fos.close();
	}
	
	private byte[] getBuffeFromInputStream(InputStream stream){
		byte[] buffer = new byte[ins.available()];
		ins.read(buffer);
		ins.close();
		return buffer
	}
	
}
