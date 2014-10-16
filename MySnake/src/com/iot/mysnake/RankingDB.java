package com.iot.mysnake;

import java.util.Vector;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

public class RankingDB extends SQLiteOpenHelper {
	private static final String DATABASE_NAME = "mysnakerank.db";
	private static final int DATABASE_VERSION = 6;
	
	private static final String TABLE_NAME 	= "table_mysnake";
	private static final String SCORE		= "score";
	private static final String APPLE		= "apple";
	private static final String TIME		= "time";
	private static final String[] FROM		= { BaseColumns._ID, SCORE, APPLE, TIME};
	public static final int MAX_RANK 		= 9;
	private static final String ORDER_BY	= SCORE + " DESC";
	

	public RankingDB(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		// TODO Auto-generated constructor stub
	}
	
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		db.execSQL("CREATE TABLE " + TABLE_NAME + "(" 
				+ BaseColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " 
				+ SCORE + " LONG," 
				+ APPLE + " LONG," 
				+ TIME + " TEXT NOT NULL);"
				);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
		onCreate(db);
	}
	
	public void addRanking(RankingData playData) {
		SQLiteDatabase db = getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(SCORE, playData.getScore());
		values.put(APPLE, playData.getApple());
		values.put(TIME, playData.getTime());
		db.insertOrThrow(TABLE_NAME, null, values);

	}
	
	
	public Cursor getRanking(Activity activity) {
		SQLiteDatabase db = getReadableDatabase();
		Cursor cursor = db.query(TABLE_NAME, FROM, null, null, null, null, ORDER_BY);
		activity.startManagingCursor(cursor);
		return cursor;	
	}
	
	public Vector<RankingData> getListFromCursor(Cursor cursor) {
		Vector<RankingData> list = new Vector<RankingData>();
		int ranking = 1;
		
		while(cursor.moveToNext()) {
			RankingData data = new RankingData();
			long score	= cursor.getLong(1);
			long apple	= cursor.getLong(2);
			long time	= cursor.getLong(3);
			
			
			if(ranking<10) {
				data.setRanking(ranking);
				data.setScore(score);
				data.setApple(apple);
				data.setTime(time);
				list.add(data);
			}
			else
				break;
			ranking++;
		}
		
		return list;
	}
	
}
