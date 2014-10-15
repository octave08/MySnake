package com.iot.mysnake;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

public class ActivityGameover extends Activity {
	private Intent m_intent = null;
	
	private int m_gameMode = 0;
	
	private String m_resultScore = null;
	private String m_resultApple = null;
	
	private TextView m_textResultScore 	= null;
	private TextView m_textResultApple	= null;
	
	private int m_resultTime = 0;
	private int m_resultM10  = 0;	private ImageView m_resultImgM10;
	private int m_resultM01  = 0;	private ImageView m_resultImgM01;
	private int m_resultS10  = 0;	private ImageView m_resultImgS10;
	private int m_resultS01  = 0;	private ImageView m_resultImgS01;
	private int m_resultMs10 = 0;	private ImageView m_resultImgMs10;
	private int m_resultMs01 = 0;	private ImageView m_resultImgMs01;
	
	private int[] m_imgNum = new int[11];
	
	
	private RankingManager m_rankingManager = null;
	private long m_ranking	= 0;
	private long m_score	= 0;
	private long m_apple	= 0;
	private long m_time		= 0;
	
	private ImageView m_imgRanking = null;
	
	private ImageView m_imgBack 	= null;
	private ImageView m_imgRetry 	= null;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.layout_gameover);
		
		getPlayData();
		
		m_textResultScore = (TextView)findViewById(R.id.textResultGameScore);
		m_textResultApple = (TextView)findViewById(R.id.textResultAppleScore);		
		m_textResultScore.setText(m_resultScore);
		m_textResultApple.setText(m_resultApple);
	
	
		combineTime();
		setImgNum();
		setImageTime();
		setImageResouce();
		
	
		m_rankingManager = new RankingManager(this);
		
		m_score = Integer.parseInt(m_resultScore);
		m_apple = Integer.parseInt(m_resultApple);
		m_time = m_resultTime;
		m_ranking = m_rankingManager.getRanking(m_score);
		
		m_imgRanking = (ImageView)findViewById(R.id.imgRanking);
		m_imgRanking.setImageResource(m_imgNum[(int)m_ranking]);

		if(m_ranking<10)
			m_rankingManager.saveRanking(m_score, m_apple, m_time);
		
		//set button retry, back
		m_imgBack = (ImageView)findViewById(R.id.imgBack2);
		m_imgRetry = (ImageView)findViewById(R.id.imgRetry);
		
		m_imgBack.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}	
		});
		
		m_imgRetry.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(ActivityGameover.this, ActivityPlay.class);
				intent.putExtra("gamemode", m_gameMode);
				startActivity(intent);
				finish();
			}		
		});
	}
	
	private void getPlayData() {
		m_intent = getIntent();
		m_resultScore = m_intent.getStringExtra("gameScore");
		m_resultApple = m_intent.getStringExtra("appleScore");
		m_resultM10 = m_intent.getIntExtra("m10", 0);
		m_resultM01 = m_intent.getIntExtra("m01", 0);
		m_resultS10 = m_intent.getIntExtra("s10", 0);
		m_resultS01 = m_intent.getIntExtra("s01", 0);
		m_resultMs10 = m_intent.getIntExtra("ms10", 0);
		m_resultMs01 = m_intent.getIntExtra("ms01", 0);	
		m_gameMode = m_intent.getIntExtra("gameMode", 0);
	}
	
	private void setImgNum() {
		for(int i=0;i<11;i++)
			m_imgNum[i] = R.drawable.num0+i;
	}
	private void setImageTime() {
		m_resultImgM10	=(ImageView)findViewById(R.id.imageResultM10);
		m_resultImgM01	=(ImageView)findViewById(R.id.imageResultM01);
		m_resultImgS10	=(ImageView)findViewById(R.id.imageResultS10);
		m_resultImgS01	=(ImageView)findViewById(R.id.imageResultS01);
		m_resultImgMs10	=(ImageView)findViewById(R.id.imageResultMs10);
		m_resultImgMs01	=(ImageView)findViewById(R.id.imageResultMs01);
	}
	private void setImageResouce() {
		m_resultImgM10.setImageResource(m_imgNum[m_resultM10]);
		m_resultImgM01.setImageResource(m_imgNum[m_resultM01]);
		m_resultImgS10.setImageResource(m_imgNum[m_resultS10]);
		m_resultImgS01.setImageResource(m_imgNum[m_resultS01]);
		m_resultImgMs10.setImageResource(m_imgNum[m_resultMs10]);
		m_resultImgMs01.setImageResource(m_imgNum[m_resultMs01]);
	}
	
	private void combineTime() {
		m_resultTime = m_resultM10*100000 + m_resultM01*10000
						+ m_resultS10*1000 + m_resultS01*100
						+ m_resultMs10*10 + m_resultMs01;
	}

	
	
}
