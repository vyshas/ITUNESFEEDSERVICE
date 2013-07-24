package com.sqllite.db;

import java.util.ArrayList;
import java.util.List;

import com.example.itunessax.MyData;


import android.provider.BaseColumns;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class AlbumDatabaseAdapter {
	
	static final String DATABASE_NAME = "album2.db";
	// Login table name
	private static final String TABLE_Create = "Albumstable" ;
    //Initial DB version
	static final int DATABASE_VERSION = 1;
    
	public static final int NAME_COLUMN = 1 ;
	
	private static String[] _columns = { AlbumColumns.Id,AlbumColumns.SongTitle,AlbumColumns.AlbumImageLink,AlbumColumns.SongWebLink};
 
 // Variable to hold the database instance
    public  SQLiteDatabase db;
    // Context of the application using the database.
    private final Context context;
 // Database open/upgrade helper
    private DataBaseHelper dbHelper;
    
      
	public AlbumDatabaseAdapter(Context _context)
	{
		context=_context;
		dbHelper=new DataBaseHelper(context, DATABASE_NAME, null, DATABASE_VERSION);
	}
	
	private interface AlbumColumns extends BaseColumns
    {
    	String Id = "id";
        String SongTitle = "SongTitle";
        String AlbumImageLink = "AlbumImageLink";
        String SongWebLink = "SongWebLink";
        
    }
	
	
	 public  AlbumDatabaseAdapter open() 
	    		throws SQLException 
	    {
	        db = dbHelper.getWritableDatabase();
	        return this;
	    }
	 
	 
	 public void close() 
	    {
	        db.close();
	    }

	    public  SQLiteDatabase getDatabaseInstance()
	    {
	        return db;
	    }
	 
	    //inserting entries
	    public void insertEntry(String SongTitle,String AlbumImageLink,String SongWebLink)
	    {
	    	ContentValues newValues = new ContentValues();
	    	// Assign values for each row.
	    	newValues.put("SongTitle",SongTitle );
	    	newValues.put("AlbumImageLink",AlbumImageLink );
	    	newValues.put("SongWebLink",SongWebLink );
	    	   		
	    	// Insert the row into your table
	    	//db.insert("Albumstable" , null, newValues);
	    	db.insertWithOnConflict(TABLE_Create, null, newValues, SQLiteDatabase.CONFLICT_IGNORE);
	    	Log.i("Successfully saved data to db", "Saved data");
	    	
	    	
	    }
	
	    
	       
	    //to check if records exists in the table
	    public boolean RecordExists() {
	    	   Cursor cursor = db.rawQuery("select 1 from Albumstable where id=?", new String[]{"1"});
	    	   boolean exists = (cursor.getCount() > 0);
	    	   cursor.close();
	    	   return exists;
	    	}
	    
	    
	    public void Testfunction()
	    {
	    	Log.i("Entered getsinglentryfunction", "IDIOT");
	    	return ;
	    }
	    
	    //gets all the data from database ,stores in a list and return the list
	    
	    public  List<MyData> getAllData()
	    {
	        // Equivalent to a SELECT * on the table name. 
	        final Cursor cursor = db.query(TABLE_Create, _columns, null, null, null, null, null);

	        final List<MyData> mydatas = new ArrayList<MyData>();

	        if (cursor != null && cursor.moveToFirst())
	        {
	            do
	            {
	                mydatas.add(getAlbumsFromCursor(cursor));
	            } while (cursor.moveToNext());
	        }

	        if (cursor != null)
	        {
	            cursor.close();
	        }

	        return mydatas;
	    }
	    
	    public MyData getAlbumsFromCursor(Cursor cursor) {
	    	 
	    	 
			//long id = cursor.getLong(cursor.getColumnIndex(AlbumColumns.Id));
			String SongTitle = cursor.getString(cursor.getColumnIndex(AlbumColumns.SongTitle));
			String SongWebLink = cursor.getString(cursor.getColumnIndex(AlbumColumns.SongWebLink));
			
			String AlbumImageLink =cursor.getString(cursor.getColumnIndex(AlbumColumns.AlbumImageLink));
			
			
			return new MyData(SongTitle,SongWebLink ,AlbumImageLink);
			
			
		}
	    
}

