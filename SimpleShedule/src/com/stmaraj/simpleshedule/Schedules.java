package com.stmaraj.simpleshedule;


import java.util.GregorianCalendar;
import android.os.Bundle;
import android.R.integer;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.Dialog;
import android.content.DialogInterface;
import android.text.format.DateFormat;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.RelativeLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.TimePicker.OnTimeChangedListener;
import android.widget.Toast;

public class Schedules extends Activity implements OnClickListener {
	
	RelativeLayout relativeLayout;
	ScheduleEntryField firstSchedule;
	Button btnAdd;
	TextView txtDate;
	int id = 1001;
	int year = 0;
	int monthOfYear = 0;
	int dayOfMonth = 0;
	int hour1;
	int hour2;
	int minute1;
	int minute2;
	TimePicker timePicker;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_schedules);
		
		
		
		relativeLayout = (RelativeLayout)findViewById(R.id.RelativeLayoutSchedules2);
		txtDate = (TextView)findViewById(R.id.txtDate);		
		
		// set id so future schedules can stack
		firstSchedule = (ScheduleEntryField)findViewById(R.id.firstSchedule);
		firstSchedule.setId(1000);
		
		// create add button and place below first schedule
		btnAdd = new Button(this);
		btnAdd.setId(100);
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
		
		switch( v.getId() )
		{
			case 100:
				addScheduleField();
				break;
			case R.id.txtDate:
				setDate();
				break;
		}
	}
	
    public void addScheduleField()
    {
    	final ScheduleEntryField schedule = new ScheduleEntryField(this);
		schedule.setId(id);
		schedule.setClickable(true);
		
		
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
    
    public void testTime(View v) {
    	
    	//timePicker = (TimePicker)findViewById(R.id.timePicker1);
		TextView button = (TextView)findViewById(R.id.testtxtview);
		button.setText("testy");
		
		Toast.makeText(Schedules.this, "btn:", Toast.LENGTH_LONG).show();
    		}

    
    public void setTime(final View v)
    {
    	
    	AlertDialog alert = new AlertDialog.Builder(Schedules.this).create();
    	alert.setTitle("Hello");
    	
    	LayoutInflater inflater = this.getLayoutInflater();
    	alert.setView(inflater.inflate(R.layout.settime_layout, null));
    	
    	alert.show();
    	
    }
    
    public void setDate() 
    {
		DatePickerDialog datePickerDialog = new DatePickerDialog(this, dateSetListener, year, monthOfYear, dayOfMonth);
		datePickerDialog.show();
    }
    
    private OnDateSetListener dateSetListener =  new OnDateSetListener() {
		
		@Override
		public void onDateSet(DatePicker view, int year, int monthOfYear,
				int dayOfMonth) {
			
			Schedules.this.year = year;
			Schedules.this.monthOfYear = monthOfYear;
			Schedules.this.dayOfMonth = dayOfMonth;
			
			GregorianCalendar date = new GregorianCalendar(year, monthOfYear, dayOfMonth);
			txtDate.setText(DateFormat.format("MMMM dd,  yyyy", date));
		}
    };

}
