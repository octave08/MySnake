package com.iot.mysnake;


import java.util.Vector;

import taesung.group.romanticcat.RankingData;
import taesung.group.romanticcat.RankingManager.DBManager;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

public class RankingManager {
	private static final String TABLE_NAME	= "MY_SNAKE_DB";
	private static final String SCORE 		= "score";
	private static final String APPLE		= "apple";
	private static final String TIME 		= "time";
	private static final String[] FROM 		= { BaseColumns._ID, SCORE, APPLE, TIME};
	private static final int MAX_RANK 		= 10; // 랭킹 최대 저장 갯수
	private static final String ORDER_BY 			= SCORE + " DESC";
	
	private DBManager	m_dbManager			= null;
	private Activity 	m_activity			= null;


	public class DBManager extends SQLiteOpenHelper {
		   private static final String DATABASE_NAME 	= "mysnake.db";
		   private static final int DATABASE_VERSION 	= 1;
		   
		   /** Create a helper object for the Events database */
		   public DBManager(Context ctx) { 
		      super(ctx, DATABASE_NAME, null, DATABASE_VERSION);
		   }
		   
		   @Override
		   public void onCreate(SQLiteDatabase db) { 
			  db.execSQL("CREATE TABLE " + TABLE_NAME + " (" 
		    		+ BaseColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " 
			        + SCORE + " INTEGER," 
			        + APPLE + " INTEGER," 
		            + TIME + " TEXT NOT NULL);"
		            );
		   }

		   @Override
		   public void onUpgrade(SQLiteDatabase db, int oldVersion, 
		         int newVersion) {
		      db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
		      onCreate(db);
		   }
		   
		   private void addRanking(RankingData ranking) {
			      SQLiteDatabase db = getWritableDatabase();
			      ContentValues values = new ContentValues();
			      values.put(SCORE, ranking.getScore());
			      values.put(TIME, ranking.getApple());
			      values.put(TIME, ranking.getTime());
			      db.insertOrThrow(TABLE_NAME, null, values);
		   }
		   
		   private Cursor getRanking(Activity activity) {
			      SQLiteDatabase db = getReadableDatabase();
			      Cursor cursor = db.query(TABLE_NAME, FROM, null, null, null,
			            null, ORDER_BY);
			      activity.startManagingCursor(cursor);
			      return cursor;
		   }
			   
		   private Vector<RankingData> getListFromCursor(Cursor cursor) {
				   
				   Vector<RankingData> list = new Vector<RankingData>();
				   int ranking =1;

			      while (cursor.moveToNext()) {
			    	  RankingData data = new RankingData();
			    	  long score 	= cursor.getLong(1);
			    	  long apple 	= cursor.getLong(2);
			    	  String time 	= cursor.getString(3);
			  
			    	  data.setRanking(ranking++);
			    	  data.setScore(score);
			    	  data.setApple(apple);
			    	  data.setTime(time);
			         
			    	  list.add(data);			         
			      }      
			      
			      return list;
		   }
		}
	
	public RankingManager(Activity activity) {
		super();
		// TODO Auto-generated constructor stub
		m_activity = activity;
		m_dbManager = new DBManager(activity);
	}

	// 점수를 주면 순위 알려주기
	public long getRanking(long score)
	{
		long ranking = 0;
		
		// 전체 랭킹 목록을 점수 내림차순으로 가져온다.
		Vector<RankingData> list = this.getRankingList();
		
		// score보다 처음으로 작은 수의 인덱스를 찾는다.
		int index = 0;
		for(RankingData data:list)
		{
			if(data.getScore()<=score) 		// 순위는 인덱스 +1 이다.
			{
				ranking = index+1;
				break;
			}
			index++;
		}
		
		ranking = index + 1;
		
		return ranking;
	}
	
	// 점수와 순위 기록하기
	boolean saveRanking(long score, String name)
	{
		// 점수, 날짜, 이름 기록
		RankingData data = new RankingData();
		data.setScore(score);
		data.setName(name);
		data.setDate(System.currentTimeMillis());

		// 만약 100위권을 벗어나면 넣지 않음
		if(this.getRanking(score)>MAX_RANK) return false;
		
		m_dbManager.addRanking(data); 
		
		return true;
	}
	
	// 랭킹 목록을 점수 내림차순으로 가져오기
	public Vector<RankingData> getRankingList()
	{
		Cursor cursor = m_dbManager.getRanking(m_activity);
		
		return m_dbManager.getListFromCursor(cursor);
	}

	public boolean saveRanking(long score, String name, long date) {
		// TODO Auto-generated method stub
		// 점수, 날짜, 이름 기록
		RankingData data = new RankingData();
		data.setScore(score);
		data.setName(name);
		data.setDate(date);

		// 만약 100위권을 벗어나면 넣지 않음
		if(this.getRanking(score)>MAX_RANK) return false;
		
		m_dbManager.addRanking(data); 
		
		return true;		
	}

	public String getLastUserName() {
		// TODO Auto-generated method stub
		
		// 전체 랭킹 목록을 시간  내림차순으로 가져온다.
		Vector<RankingData> list = this.getRankingListSortByTimeDesc();
		
		if(list.size()>0) return list.get(0).getName();
		
		return null;
	}

	private Vector<RankingData> getRankingListSortByTimeDesc() {
		// TODO Auto-generated method stub
		Cursor cursor = m_dbManager.getRankingSortByTimeDesc(m_activity);
		
		return m_dbManager.getListFromCursor(cursor);		
	}
	
	
}
