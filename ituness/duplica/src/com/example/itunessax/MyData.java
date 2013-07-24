package com.example.itunessax;

import android.util.Log;

public class MyData {

	
	//Songlink
	public String mSongLink ;
	//Songtitle
	public  String mTitle ;
	public  String mEntry ;
	//ImageLink
	public  String mImage;
	

	

	public MyData()
	{
		Log.i("default constructor","Called");
	}
	
	
	
	public  MyData(String songTitle, String songWebLink, String albumImageLink)
	{
		mTitle=songTitle;
		mSongLink=songWebLink;
		mImage=albumImageLink;
	 
	}
	
	
	public String getmTitle() {
		return mTitle;
	}
	public String getmSongLink() {
		return mSongLink;
	}
	public void setmSongLink(String mSongLink) {
		this.mSongLink = mSongLink;
	}
	public void setmTitle(String mTitle) {
		this.mTitle = mTitle;
	}
	
	
	public String getmEntry() {
		return mEntry;
	}
	public void setmEntry(String mEntry) {
		this.mEntry = mEntry;
	}
	public String getmImage() {
		return mImage;
	}
	public void setmImage(String mImage) {
		this.mImage = mImage;
	}
	
	
	
	
}
