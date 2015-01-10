package com.fermaursilor.sqlite;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.fermaursilor.model.Ratio;

public class SqliteController extends SQLiteOpenHelper {
	public SqliteController(Context applicationcontext) {
		super(applicationcontext, "fermaursilor.db", null, 1);
		
		Log.i("SQLite", "DB Created");
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		String query;
		query = "CREATE TABLE ratio ( " +
				"id INTEGER PRIMARY KEY AUTOINCREMENT, " +
				"ratio DOUBLE , " +
				"day DEFAULT CURRENT_TIMESTAMP , " +
				"tip_chestionar VARCHAR )";
		
		db.execSQL(query);

		Log.i("SQLite", "Table Created");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		String query; query = "DROP TABLE IF EXISTS ratio"; 
		db.execSQL(query); 
		onCreate(db);
		
		Log.i("SQLite", "DB Upgraded");
	}

	public void insertRatio(double ratio, String tipChestionar) { 
		SQLiteDatabase database = this.getWritableDatabase(); 
		ContentValues values = new ContentValues(); 
		values.put("ratio", ratio);
		values.put("tip_chestionar",tipChestionar);
		database.insert("ratio", null, values); 
		database.close(); 
	}

	
	public ArrayList<Ratio> getAllRatio() { 
		
		String selectQuery = "SELECT * FROM ratio"; 
		SQLiteDatabase database = this.getWritableDatabase(); 
		Cursor cursor = database.rawQuery(selectQuery, null); 
		
		ArrayList<Ratio> list = new ArrayList<Ratio>();
		
		if (cursor.moveToFirst()) { 
			do {
				Ratio ratio = new Ratio(cursor.getInt(0),cursor.getDouble(1), cursor.getString(2),cursor.getString(3));
				list.add(ratio);
			} while (cursor.moveToNext()); 
		} 
		
		database.close();
		
		return list;
	}

}
