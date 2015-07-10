package com.wochstudios.soundboard.Models;

public abstract class SoundboardModel
{
    private int _ID;
    private String Title;

    public SoundboardModel(){}
    public SoundboardModel(int id, String t){
        this._ID = id;
        this.Title = t;
    }

    public int get_ID() {
        return _ID;
    }

    public void set_ID(int _ID) {
        this._ID = _ID;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }
}
