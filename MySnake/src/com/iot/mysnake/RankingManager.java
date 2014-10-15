package com.iot.mysnake;

import java.util.Vector;

import android.app.Activity;
import android.database.Cursor;


public class RankingManager {
	private Activity m_activity = null;
	private RankingDB m_rankingDB = null;
	
	
	public RankingManager(Activity activity) {
		super();
		// TODO Auto-generated constructor stub
		m_activity = activity;
		m_rankingDB = new RankingDB(activity);
	}

	
	public long getRanking(long score) {
		long ranking = 0;
		
		Vector<RankingData> list = this.getRankingList();

		int index = 0;
		for(RankingData data:list)
		{
			if(data.getScore()<score) 		
			{
				ranking = index+1;
				break;
			}
			index++;
		}
		ranking = index + 1;
		
		return ranking;
	}
	

	public boolean saveRanking(long score, long apple, long time) {
		RankingData data = new RankingData();
		data.setScore(score);
		data.setApple(apple);
		data.setTime(time);

		if(this.getRanking(score)<=RankingDB.MAX_RANK) {
			m_rankingDB.addRanking(data);
			return true;
		}

		return false;
	}
	
	
	public Vector<RankingData> getRankingList() {
		Cursor cursor = m_rankingDB.getRanking(m_activity);
		
		return m_rankingDB.getListFromCursor(cursor);
	}
	

}
