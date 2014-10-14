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
	private static final int MAX_RANK 		= 10; // ��ŷ �ִ� ���� ����
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

	// ������ �ָ� ���� �˷��ֱ�
	public long getRanking(long score)
	{
		long ranking = 0;
		
		// ��ü ��ŷ ����� ���� ������������ �����´�.
		Vector<RankingData> list = this.getRankingList();
		
		// score���� ó������ ���� ���� �ε����� ã�´�.
		int index = 0;
		for(RankingData data:list)
		{
			if(data.getScore()<=score) 		// ������ �ε��� +1 �̴�.
			{
				ranking = index+1;
				break;
			}
			index++;
		}
		
		ranking = index + 1;
		
		return ranking;
	}
	
	// ������ ���� ����ϱ�
	boolean saveRanking(long score, String name)
	{
		// ����, ��¥, �̸� ���
		RankingData data = new RankingData();
		data.setScore(score);
		data.setName(name);
		data.setDate(System.currentTimeMillis());

		// ���� 100������ ����� ���� ����
		if(this.getRanking(score)>MAX_RANK) return false;
		
		m_dbManager.addRanking(data); 
		
		return true;
	}
	
	// ��ŷ ����� ���� ������������ ��������
	public Vector<RankingData> getRankingList()
	{
		Cursor cursor = m_dbManager.getRanking(m_activity);
		
		return m_dbManager.getListFromCursor(cursor);
	}

	public boolean saveRanking(long score, String name, long date) {
		// TODO Auto-generated method stub
		// ����, ��¥, �̸� ���
		RankingData data = new RankingData();
		data.setScore(score);
		data.setName(name);
		data.setDate(date);

		// ���� 100������ ����� ���� ����
		if(this.getRanking(score)>MAX_RANK) return false;
		
		m_dbManager.addRanking(data); 
		
		return true;		
	}

	public String getLastUserName() {
		// TODO Auto-generated method stub
		
		// ��ü ��ŷ ����� �ð�  ������������ �����´�.
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
