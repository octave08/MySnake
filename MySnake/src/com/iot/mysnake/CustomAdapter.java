package com.iot.mysnake;

import java.util.Vector;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

public class CustomAdapter extends BaseAdapter {
	
	private Vector<RankingData> m_rankingList = new Vector<RankingData>();
	private Activity m_activity = null;
	
	private ImageView m_imgRanking = null;
	
	public CustomAdapter(Activity activity) {
		super();
		m_activity = activity;
	}
	
	public void clear() {
		m_rankingList.clear();
	}
	
	public void addData(RankingData rankingData) {
		m_rankingList.add(rankingData);
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return m_rankingList.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return m_rankingList.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		if(convertView == null) {
			convertView = m_activity.getLayoutInflater()
							.inflate(R.layout.list_items, parent, false);
		}
		
		RankingData info = m_rankingList.get(position);
		
		m_imgRanking = (ImageView)convertView.findViewById(R.id.imgRanking);
		m_imgRanking.setImageResource(R.drawable.num0+(int)(info.getRanking()));
			
		return convertView;
	}

}
