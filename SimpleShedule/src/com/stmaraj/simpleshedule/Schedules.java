package com.stmaraj.simpleshedule;

import android.os.Bundle;
import android.R.integer;
import android.app.Activity;
import android.util.AttributeSet;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.Toast;

public class Schedules extends Activity {
	
	
	TableLayout tableLayout1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
	    tableLayout1 = (TableLayout) findViewById(R.id.TableLayout1);
		setContentView(R.layout.activity_schedules);
		btnAddClick((LinearLayout)findViewById(R.id.LinearLayoutSchedules));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.schedules, menu);
		return true;
	}
	
	public void btnAddClick(View v)
	{

		LinearLayout linearLayout = (LinearLayout)findViewById(R.id.LinearLayoutSchedules);
		ScheduleEntryField schedule = new ScheduleEntryField(this);
		
		//set length and width of schedule
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT,
                LayoutParams.WRAP_CONTENT);
		
		
//		RelativeLayout.LayoutParams parassms = new RelativeLayout.LayoutParams(LayoutParams.FILL_PARENT,
//                LayoutParams.WRAP_CONTENT);
//		
//		parassms.
//		params.
		schedule.setLayoutParams(params);
		
		//add schedule
		linearLayout.addView(schedule);
		
		Toast.makeText(this, "New Schedule", Toast.LENGTH_SHORT).show();

	}

}
