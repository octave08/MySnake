package com.iot.mysnake;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;

public class ActivityPlay extends Activity {
	
	private int m_gameMode;
	private final static int STAGE 		= 1;
	private final static int INFINITY 	= 2;
	
	ViewBox m_viewBox = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.layout_play);
		
		Intent intent = getIntent();
		m_gameMode = intent.getIntExtra("gamemode", STAGE);
		
		m_viewBox = (ViewBox)findViewById(R.id.viewBox);
		
	}
	
	

}
