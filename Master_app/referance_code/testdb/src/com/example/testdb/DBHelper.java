package com.example.testdb;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper{

	private static final String DATABASE_NAME="Aerial_Assist";
	private static final String TABLE_NAME="aerial_assist";
	
    private static final int DATABASE_VERSION = 1;
	
	//names of columns of all needed data
    private static final String ROW_ID = "ROWID";
	private String[] columnNames = {	"team_num",				//team number
										"match_num",			//match number
										"is_red",				//whether alliance is red
										"top_auto",				//top goals in auto
										"topMisses_auto",		//top goal misses in auto
										"topHot_auto",			//top hot goals in auto
										"bottom_auto",			//bottom goals in auto
										"botHot_auto",			//bottom hot goals in auto
										"defense",				//whether team defended
										"movement",				//whether team moved
										"cycle",				//total cycle num
										"assit_throw",			//assist passes (array)
										"throw_fzone",			//passes from feeder zone
										"throw_tzone",			//from truss zone
										"throw_gzone",			//from goal zone
										"assit_catch",			//assist catches (array)
										"catch_fzone",			//catches from feeder zone
										"catch_tzone",			//from truss zone
										"catch_gzone",			//from goal zone
										"truss_throw",			//all truss throws (array)
										"truss_miss",			//all missed truss throws (array)
										"truss_catch",			//truss catches (array)
										"top_tele",				//all top goals made
										"topMisses_tele",		//all top goal misses
										"bottom_tele",			//all bottom goals made
										"cycle_goals",			//which goal was made in each cycle (array)
																/***************************	
																 * 	-1 = missed
																 * 	 0 = nothing
																 * 	 1 = bottom goal
																 * 	 2 = top goal
																 ***************************/
										"cycle_time"			//duration of each cycle
									};
	
	public DBHelper(Context context) {
		  super(context, DATABASE_NAME, null, DATABASE_VERSION); 
	}
	
	@Override
	public void onCreate(SQLiteDatabase db) {		  
		//creating the table "aerial_assist" in the database
		String COMMAND = "CREATE TABLE IF NOT EXISTS "+ TABLE_NAME +
 			   "("+ROW_ID+" 					INTEGER		PRIMARY KEY		AUTOINCREMENT," + //needed to make every data entry a unique entry
 		/*	   " "+columnNames[0]+" 			INT 		    			NOT NULL," +
 			   " "+columnNames[1]+"           	INT    						NOT NULL," + 
                " "+columnNames[2]+"            BOOLEAN    					NOT NULL," + 
                " "+columnNames[3]+"            INT							NOT NULL," + 
                " "+columnNames[4]+"         	INT							NOT NULL," +
                " "+columnNames[5]+" 			INT 				    	NOT NULL," +
 			   " "+columnNames[6]+"           	INT    						NOT NULL," + 
                " "+columnNames[7]+"           	INT    						NOT NULL," + 
                " "+columnNames[8]+" 			BOOLEAN				    	NOT NULL," +
 			   " "+columnNames[9]+"           	BOOLEAN  					NOT NULL," + 
                " "+columnNames[10]+"           INT    						NOT NULL," + 
                " "+columnNames[11]+" 			TEXT 				    	NOT NULL," +
 			   " "+columnNames[12]+"           	INT  	  					NOT NULL," + 
                " "+columnNames[13]+"           INT    						NOT NULL," + 
                " "+columnNames[14]+" 			INT 				    	NOT NULL," +
 			   " "+columnNames[15]+"           	TEXT   						NOT NULL," + 
                " "+columnNames[16]+"           INT    						NOT NULL," + 
                " "+columnNames[17]+" 			INT 				    	NOT NULL," +
 			   " "+columnNames[18]+"           	INT    						NOT NULL," + 
                " "+columnNames[19]+"           TEXT   						NOT NULL," + 
                " "+columnNames[20]+" 			TEXT 				    	NOT NULL," +
 			   " "+columnNames[21]+"           	TEXT   						NOT NULL," + 
                " "+columnNames[22]+"           INT    						NOT NULL," + 
                " "+columnNames[23]+" 			INT 				    	NOT NULL," +
                " "+columnNames[24]+" 			INT 				    	NOT NULL," +
                " "+columnNames[25]+"         	TEXT						NOT NULL," +
          */      " "+columnNames[26]+"         	TEXT						NOT NULL);"; 
		  db.execSQL(COMMAND);
		    
		 }

	@Override
	public void onUpgrade(SQLiteDatabase db, int arg1, int arg2) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
 
        // Create tables again
        onCreate(db);
	}
	
	/********************************* DATABASE INTERACTION METHODS *****************************/
	
	/////////////////////////////////////////// ADDERS ///////////////////////////////////////////
	public void addCycleTime(String input) {
	    SQLiteDatabase db = this.getWritableDatabase();
	 	 
	    //defining value to put into database
	    ContentValues values = new ContentValues();
	    values.put(columnNames[26], input); 
	 
	    // Inserting all values into next Row
	    db.insert(TABLE_NAME, null, values);
	    db.close(); // Closing database connection
	}
	/////////////////////////////////////////// GETTERS ///////////////////////////////////////////
	public String getAllData() {
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_NAME;
 
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
 
        // looping through all rows and adding to list
        String result = "";
        if (cursor.moveToFirst()) {
            do {
            	result += cursor.getString(0) + " , " + cursor.getString(1) + "\n";
            } while (cursor.moveToNext());
        }
 
        // return contact list
        return result;
    }
	public int getNumRows() {
		// get the number of rows of data in the database
        String countQuery = "SELECT  * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();
 
        // return count
        return cursor.getCount();
    }
	
	// methods to immplement later
	/*// Updating single contact
	public int updateContact(Contact contact) {
	    SQLiteDatabase db = this.getWritableDatabase();
	 
	    ContentValues values = new ContentValues();
	    values.put(KEY_NAME, contact.getName());
	    values.put(KEY_PH_NO, contact.getPhoneNumber());
	 
	    // updating row
	    return db.update(TABLE_CONTACTS, values, KEY_ID + " = ?",
	            new String[] { String.valueOf(contact.getID()) });
	}
	// Deleting single contact
	public void deleteContact(Contact contact) {
	    SQLiteDatabase db = this.getWritableDatabase();
	    db.delete(TABLE_CONTACTS, KEY_ID + " = ?",
	            new String[] { String.valueOf(contact.getID()) });
	    db.close();
	}*/
	
}
