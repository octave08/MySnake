package com.iot.mysnake;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;




import android.app.Activity;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CustomAdapter extends BaseAdapter {
	
	private Vector<RankingData> m_rankingList = new Vector<RankingData>();
	private Activity m_activity = null;
	
	private ImageView m_imgRanking 		= null;
	private TextView m_textRankScore 	= null;
	private TextView m_textRankApple 	= null;
	private TextView m_textRankTime		= null; 
	
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
		
		m_textRankScore = (TextView)convertView.findViewById(R.id.textRankScore);
		m_textRankScore.setText(Long.toString(info.getScore()));
		m_textRankApple = (TextView)convertView.findViewById(R.id.textRankApple);
		m_textRankApple.setText(Long.toString(info.getApple()));
		
		DecimalFormat df = new DecimalFormat("##,##,##");
		String strTime = df.format(info.getTime());
		
		m_textRankTime = (TextView)convertView.findViewById(R.id.textRankTime);
		m_textRankTime.setText(strTime);
			
		return convertView;
	}

	private AssetManager getAssets() {
		// TODO Auto-generated method stub
		return null;
	}

}
