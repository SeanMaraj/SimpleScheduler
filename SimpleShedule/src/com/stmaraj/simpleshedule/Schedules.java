package com.stmaraj.simpleshedule;

import android.os.Bundle;
import android.R.integer;
import android.app.Activity;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.Toast;

public class Schedules extends Activity implements OnClickListener {
	
	ScheduleEntryField firstSchedule;
	Button btnAdd;
	RelativeLayout relativeLayout;
	int id = 1001;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_schedules);
		
		relativeLayout = (RelativeLayout)findViewById(R.id.RelativeLayoutSchedules2);
		
		// set id so future schedules can stack
		firstSchedule = (ScheduleEntryField)findViewById(R.id.firstSchedule);
		firstSchedule.setId(1000);

		// create add button and place below first schedule
		btnAdd = new Button(this);
		btnAdd.setOnClickListener(this);
		btnAdd.setGravity(Gravity.CENTER);
		RelativeLayout.LayoutParams btnAddParams = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		btnAddParams.addRule(RelativeLayout.BELOW, 1000);
		relativeLayout.addView(btnAdd, btnAddParams);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.schedules, menu);
		return true;
	}

	@Override
	public void onClick(View v) {
		ScheduleEntryField schedule = new ScheduleEntryField(this);
		schedule.setId(id);
		
		//set length and width of schedule 
		RelativeLayout.LayoutParams parameters = new RelativeLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
		parameters.addRule(RelativeLayout.BELOW, id-1);
		relativeLayout.addView(schedule, parameters);
		
		// move button below new schedule
		RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) btnAdd.getLayoutParams();
		layoutParams.addRule(RelativeLayout.BELOW, id);
		btnAdd.setLayoutParams(layoutParams);
		
		id++;
	}

}
