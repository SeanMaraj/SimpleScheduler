package com.stmaraj.simpleshedule;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	public void onClick(View v)
	{
		Intent intent = null;

		switch (v.getId())
		{
			case R.id.btnSchedule: 
				intent = new Intent(this, Schedules.class);
				break;
			case R.id.button1:
				intent = new Intent(this, ToDo.class);
				break;
			case R.id.btnTest:
				intent = new Intent(this, Test.class);
				break;
		}

		startActivity(intent);
	}

}
