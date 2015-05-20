package com.wochstudios.soundboard.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;

public class MapLoader {
	private final int KEY = 0;
        private final int VALUE = 1;
        private HashMap <String,String> valueMap = new HashMap<String,String>();
        
	public HashMap<String,String> loadVaules(InputStream valueFile) throws IOException{
        	BufferedReader  bfr = new BufferedReader(new InputStreamReader(valueFile));
        	readBufferIntoMap(bfr);
        	bfr.close();
        	return valueMap;
	}
	
	private void readBufferIntoMap(BufferedReader bfr) throws IOException{
		String line = "";
        	while ((line = bfr.readLine()) != null) {
            		if (!line.equals("")){
                		String[] pair = line.trim().split(":");
                		valueMap.put(pair[KEY].trim(), pair[VALUE].trim());
            		}
        	}
	}
}
