package com.wochstudios.soundboard;

import android.app.Activity;
import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.widget.ListView;
import android.widget.Toast;

import com.wochstudios.soundboard.Controllers.DatabaseController;
import com.wochstudios.soundboard.Controllers.DrawerController;
import com.wochstudios.soundboard.DisplayFragments.AddSoundDialogFragment;
import com.wochstudios.soundboard.DisplayFragments.CreateSoundboardFragment;
import com.wochstudios.soundboard.DisplayFragments.SoundboardFragment;
import com.wochstudios.soundboard.Models.Sound;
import com.wochstudios.soundboard.Models.Soundboard;

import java.util.ArrayList;

public class MainActivityController
{
    public static final int CREATE_SOUNDBOARD_FRAGEMENT_CD = 1;
    public static final int ADD_SOUND_FRAGMENT_CD = 2;
    private static final String FRAGMENT_TAG = "current_fragment";
    private static final String CURRENT_SOUNDBOARD = "currentSoundboard";
    private static final String DEFAULT_SOUNDBOARD = "defaultSoundboard";
    private DatabaseController databaseController;
	private DrawerController drawerController;
	private Fragment fragment;
	private DialogFragment dialogFragment;
	private SharedPreferences preferences;
	private FragmentManager fragmentManager;
	private ListView drawerList;
    private MainActivity mainActivity;

    public MainActivityController(Activity activity){
		databaseController = new DatabaseController(activity);
		drawerController = new DrawerController();
		fragmentManager = activity.getFragmentManager();
		PreferenceManager.setDefaultValues(activity, R.xml.preferences, false);
		preferences = PreferenceManager.getDefaultSharedPreferences(activity);
		drawerList = (ListView)activity.findViewById(R.id.left_drawer);
        mainActivity = (MainActivity) activity;
    }
	
	
	public void checkForSoundboards(){
		if(!databaseController.checkForSoundboards()){
			showDialogFragment(CREATE_SOUNDBOARD_FRAGEMENT_CD);
		}
	}
	
	private void replaceContentFrameWithFragment(Fragment frag){
		fragmentManager.beginTransaction()
			.replace(R.id.content_frame,frag, FRAGMENT_TAG)
			.commit();
	}
	
	public void initSoundboardFragment(){
		fragment = new SoundboardFragment(databaseController.getSoundboard(preferences.getString(DEFAULT_SOUNDBOARD,"")));
		replaceContentFrameWithFragment(fragment);
	}
	
	
	public void updateSoundboardFragment(String id){
		preferences.edit().putString(CURRENT_SOUNDBOARD,id).commit();
		((SoundboardFragment)fragment).refreshListView(databaseController.getSoundboard(id));
	}
	
	

	public void  updateDrawerList(){
        drawerController.refreshDrawerList(drawerList, databaseController.getSoundboards());
	}
	
	
	public void showDialogFragment(int fragment_cd){
		switch (fragment_cd){
			case CREATE_SOUNDBOARD_FRAGEMENT_CD:
				dialogFragment = new CreateSoundboardFragment(databaseController);
				break;
			case ADD_SOUND_FRAGMENT_CD:
                if (databaseController.checkForSoundboards()) {
                    String id = (preferences.getString(CURRENT_SOUNDBOARD, "").isEmpty()) ?
                            preferences.getString(DEFAULT_SOUNDBOARD, "") : preferences.getString(CURRENT_SOUNDBOARD, "");
                    dialogFragment = new AddSoundDialogFragment(databaseController, id);
                } else {
                    Toast.makeText(mainActivity, "Please create a soundboard first", Toast.LENGTH_SHORT).show();
                }
                break;
			default:
				return;
		}
		dialogFragment.show(fragmentManager, dialogFragment.getClass().getSimpleName());
	}
	
	public void startSettingsActivity(Activity activity){
		Intent intent = new Intent();
		intent.putStringArrayListExtra("SOUNDBOARD_NAMES",getSoundboardNames());
		intent.putStringArrayListExtra("SOUNDBOARD_IDS", databaseController.getSoundboardIds());
		intent.setClass(activity, SettingsActivity.class);
        activity.startActivityForResult(intent, 0);
    }

	public void removeSoundboard(String id){
		Soundboard s = databaseController.getSoundboard(id);
		for(Sound sound : s.getSounds()){
			databaseController.removeSoundFromSoundboard(sound.getID()+"");
		}
		if(id.equals(preferences.getString(DEFAULT_SOUNDBOARD,""))){
			preferences.edit().putString(DEFAULT_SOUNDBOARD,"0").commit();
		}
		if(id.equals(getCurrentSoundboardId())){
			preferences.edit().putString(CURRENT_SOUNDBOARD,preferences.getString(DEFAULT_SOUNDBOARD,"")).commit();
		}
		databaseController.removeSoundboardFromDatabase(id);	
		updateDrawerList();
		updateSoundboardFragment(getCurrentSoundboardId());
	}

	public void removeSound(String id){databaseController.removeSoundFromSoundboard(id);}
		
	public ArrayList<String> getSoundboardNames(){
		return databaseController.getSoundboardNames();
	}

    public void renameSoundboard(String newName, String soundboardId) {
        databaseController.renameSoundboard(newName, soundboardId);
    }


    public String getCurrentSoundboardId(){
		return preferences.getString(CURRENT_SOUNDBOARD,"");
	}
	
	
	public ActionBarDrawerToggle getToggle(Activity act, DrawerLayout dl, int open, int close){
		return drawerController.getToggle(act, dl, open, close);
	}

    public DatabaseController getDatabaseController(){
        return databaseController;
    }

	}
