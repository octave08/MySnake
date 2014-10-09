package com.iot.mysnake;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Layout;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageButton;


public class ActivityIntro extends Activity {

	private ImageButton btnStart;
	private ImageButton btnBackFromStage;
	private ImageButton	btnGoStage1;
	private ImageButton btnBackFromInfinity;
	private ImageButton btnGoInfinity;
	
	private ImageButton btnMode;
	private ImageButton btnBackFromMode;
	private ImageButton btnSetStage;
	private ImageButton btnSetInfinity;
	
	private ImageButton btnRank;
	
	View linearStage	= null;
	View linearInfinity	= null;
	View linearMode		= null;
	
	private int m_gameMode = 1;
	private final static int STAGE = 1;
	private final static int INFINITY = 2;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.layout_intro);
		
		initLinearId();
		
		setBtnStart();
		setBtnMode();


	}
	
	private void initLinearId() {
		linearStage 	= (View)findViewById(R.id.lineartStage);
		linearInfinity 	= (View)findViewById(R.id.linearInfinity);
		linearMode		= (View)findViewById(R.id.linearMode);
	}
	
	// 스타트관련 버튼 설정
	private void setBtnStart() {
		btnStart = (ImageButton)findViewById(R.id.btnGoStart);
		btnStart.setOnClickListener(new OnClickListener() 
		{
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(m_gameMode == STAGE)
					linearStage.setVisibility(View.VISIBLE);
				else
					linearInfinity.setVisibility(View.VISIBLE);
			}
			
		});
		
		btnBackFromStage = (ImageButton)findViewById(R.id.btnBackFromStage);
		btnBackFromStage.setOnClickListener(new OnClickListener() 
		{
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
					linearStage.setVisibility(View.INVISIBLE);
			}
			
		});
		
		btnGoStage1 = (ImageButton)findViewById(R.id.btnStage1);
		btnGoStage1.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(ActivityIntro.this, ActivityPlay.class);
				intent.putExtra("gamemode", m_gameMode);
				startActivityForResult(intent, -1);
			}
			
		});
		
		btnBackFromInfinity = (ImageButton)findViewById(R.id.btnBackFromInfinity);
		btnBackFromInfinity.setOnClickListener(new OnClickListener() 
		{
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
					linearInfinity.setVisibility(View.INVISIBLE);
			}
			
		});
		
		btnGoInfinity = (ImageButton)findViewById(R.id.btnInfinity);
		btnGoInfinity.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(ActivityIntro.this, ActivityPlay.class);
				intent.putExtra("gamemode", m_gameMode);
				startActivityForResult(intent, -1);
			}
			
		});
	}

	
	// Mode관련 버튼 설정
	private void setBtnMode() {
		btnMode = (ImageButton)findViewById(R.id.btnGoMode);
		btnMode.setOnClickListener(new OnClickListener() 
		{
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
					linearMode.setVisibility(View.VISIBLE);
			}
			
		});
		
		btnBackFromMode = (ImageButton)findViewById(R.id.btnBackFromMode);
		btnBackFromMode.setOnClickListener(new OnClickListener() 
		{
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
					linearMode.setVisibility(View.INVISIBLE);
			}
			
		});
		
		btnSetStage = (ImageButton)findViewById(R.id.btnSetStage);
		btnSetStage.setOnClickListener(new OnClickListener() 
		{
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
					m_gameMode = STAGE;
					linearMode.setVisibility(View.INVISIBLE);
			}
			
		});
		
		btnSetInfinity = (ImageButton)findViewById(R.id.btnSetInfinity);
		btnSetInfinity.setOnClickListener(new OnClickListener() 
		{
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
					m_gameMode = INFINITY;
					linearMode.setVisibility(View.INVISIBLE);
			}
			
		});
	}
	
	
	
}
