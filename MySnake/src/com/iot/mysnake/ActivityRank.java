package com.iot.mysnake;

import java.util.Vector;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.ListView;

public class ActivityRank extends Activity {
	
	private ImageView m_imgBack	= null;
	
	private ListView m_list;
	private CustomAdapter m_adapter;
	
	private RankingManager m_rankingManager;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.layout_rank);
		
		m_imgBack = (ImageView)findViewById(R.id.imgBack);
		m_imgBack.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}	
		});
		
		m_adapter = new CustomAdapter(this);
		m_list = (ListView)findViewById(R.id.listView);
		m_list.setAdapter(m_adapter);
		
		setupListItem();
	}
	
	// setup list Items from DB
	private void setupListItem() {
		
		m_rankingManager = new RankingManager(this);
		Vector<RankingData> list = m_rankingManager.getRankingList();
		
		m_adapter.clear();
		
		for(RankingData data : list) 
			m_adapter.addData(data);
		
		m_adapter.notifyDataSetChanged();
	}
}
