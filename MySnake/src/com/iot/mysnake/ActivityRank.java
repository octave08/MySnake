package com.iot.mysnake;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.widget.ListView;

public class ActivityRank extends Activity {
	
	private ListView m_list;
	private CustomAdapter m_adapter;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.layout_rank);
		
		m_adapter = new CustomAdapter(this);
		m_list = (ListView)findViewById(R.id.listView);
		m_list.setAdapter(m_adapter);
		
		setupListItem();
	}
	
	private void setupListItem() {
		long[] ranking = {1, 2, 3};
		
		m_adapter.clear();
		
		for(int i=0;i<ranking.length;i++) {
			final RankingData rankingData = new RankingData();
			rankingData.setRanking(ranking[i]);
			
			m_adapter.addData(rankingData);
		}
		
		m_adapter.notifyDataSetChanged();
	}
}
