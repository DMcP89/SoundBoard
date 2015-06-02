package com.wochstudios.soundboard;

import java.util.ArrayList;
import java.util.Collections;



import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.*;
import android.view.View.*;
import android.view.*;
import java.util.zip.*;
import android.graphics.drawable.*;
import android.app.*;

import com.wochstudios.soundboard.Interfaces.AddSoundDialogListener;
import com.wochstudios.soundboard.utils.*;
import java.io.*;

public class MainActivity extends FragmentActivity implements  AddSoundDialogListener {

	private ArrayList<String> Titles;
	private SoundBoardController SBC;
	private MapController MC;
	private AddSoundDialogFragment ASDF;
	private ListView lv;
	
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		init();	
	}//onCreate
	
	
	private void init(){
		MC = new MapController(this);
		MC.createMap();
		SBC = new SoundBoardController(this, MC.getSoundMap());
		Titles = new ArrayList<String>(MC.getSoundMap().keySet());
		Collections.sort(Titles);
		createListView();
		
	}
	
	
	
	private void createListView(){
		lv = (ListView) findViewById(R.id.listView1);
		lv.setAdapter(new ArrayAdapter<String>(this,R.layout.list_item,Titles));
		registerForContextMenu(lv);
		lv.setTextFilterEnabled(true);
		lv.setOnItemClickListener(new ListViewClickListener());
	}
	
	
	 @Override
	 public void onCreateContextMenu(ContextMenu menu, View v,ContextMenuInfo menuInfo) {
	   	if (v.getId()==R.id.listView1) {
	     	AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)menuInfo;
	     	menu.setHeaderTitle(Titles.get(info.position));
	     	String[] menuItems = getResources().getStringArray(R.array.menuItems);
	     	for (int i = 0; i<menuItems.length; i++) {
	       		menu.add(Menu.NONE, i, i, menuItems[i]);
	     	}
	   	}
	 }//onCreateContextMenu
	 
	 @Override
	 public boolean onContextItemSelected(MenuItem item) {
	   AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
	   if(item.getItemId() == 0){
	   	SBC.downloadRingtone(Titles.get(info.position));
	   }else if(item.getItemId() == 1){
		 //MC.RemoveSoundFromMap(Titles.get(info.position),SBC.loadSounds().get(Titles.get(info.position)));
		 MC.RemoveSoundFromMap(Titles.get(info.position));
		 refreshListView();
	   }
	   return true;
	 }//onContextItemSelected
 
	 
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		switch (item.getItemId()){
			case R.id.add_sound:
				ASDF = new AddSoundDialogFragment(MC);
				ASDF.show(getFragmentManager(),"AddSoundDialogFragment");
				return true;
			default:
				return super.onOptionsItemSelected(item);
		}
		
	}

	@Override
	public void onDialogPositiveClick(DialogFragment dialog)
	{
		refreshListView();
	}

	@Override
	public void onDialogNegativeClick(DialogFragment dialog){
		
	}
	
	private void refreshListView(){
		Titles = new ArrayList<String>(MC.getSoundMap().keySet());
		Collections.sort(Titles);
		lv.setAdapter(new ArrayAdapter<String>(this,R.layout.list_item,Titles));
	}


	private class ListViewClickListener implements OnItemClickListener{
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {	
			SBC.playSound(Titles.get(position));
		} 
	}
}
