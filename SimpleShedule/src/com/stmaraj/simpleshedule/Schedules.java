package com.stmaraj.simpleshedule;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.GregorianCalendar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.R.integer;
import android.R.string;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.text.format.DateFormat;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.TimePicker.OnTimeChangedListener;
import android.widget.Toast;

public class Schedules extends FragmentActivity implements OnClickListener {
	
	RelativeLayout relativeLayout;
	ScheduleEntryField firstSchedule;
	Button btnAdd;
	TextView txtDate;
	TimePicker timePicker;
	SetTimeDialog setTimeDialog;
	
	int id = 1001;
	int dayOfMonth = 0, monthOfYear = 0, year = 0 ;
	int hour1, hour2;
	int minute1, minute2;
	
	int schedulesCount = 1;
	
	FragmentManager fm = getSupportFragmentManager();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_schedules);
		
		relativeLayout = (RelativeLayout)findViewById(R.id.RelativeLayoutSchedules2);
		txtDate = (TextView)findViewById(R.id.txtDate);		
		
		// set id of first schedule which has already been created so future schedules can stack
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
    	// create new schedule entry field
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
		
		schedulesCount++;
		id++;
    }
    
    public void setTime(final View v)
    {
    	// send the id of the schedule entry field
    	Bundle bundle = new Bundle(1);
    	bundle.putInt("viewId", ((View)v.getParent().getParent()).getId());
    	
    	setTimeDialog = new SetTimeDialog();
	    setTimeDialog.setArguments(bundle);
    	setTimeDialog.show(fm, "setTimeD"); 
    }
    
    public void setDate() 
    {
    	// create dialog to set the date
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
    
    public void saveSchedules(View v) throws IOException, JSONException
    {
    	// array to save data
    	JSONArray schedulesArray = new JSONArray();
    	
    	// save date
    	JSONObject dateJSON = new JSONObject();
    	GregorianCalendar date = new GregorianCalendar(year, monthOfYear, dayOfMonth);
    	dateJSON.put("date", DateFormat.format("MMMM dd,  yyyy", date));
    	schedulesArray.put(dateJSON);
    	
    	// save all entry field data into array
    	for (int i=0; i<schedulesCount; i++)
    	{
    		ScheduleEntryField scheduleEntryField = (ScheduleEntryField)findViewById(1000+i);
    		
    		JSONObject scheduleJSON = new JSONObject();
    		scheduleJSON.put("time1", scheduleEntryField.getTime1());
    		scheduleJSON.put("time2", scheduleEntryField.getTime2());
    		schedulesArray.put(scheduleJSON);
    	}
    	
    	String filename = String.valueOf(monthOfYear) + String.valueOf(dayOfMonth) + String.valueOf(year);
    	
		FileOutputStream outputStream;
		outputStream = openFileOutput(filename, Context.MODE_PRIVATE);
		outputStream.write(schedulesArray.toString().getBytes());
		outputStream.close();
	
		Toast.makeText(this, filename, Toast.LENGTH_SHORT).show();
    }
    
    public void loadSchedules(View v) throws JSONException, IOException
    {
    	ScrollView scrollView = (ScrollView)findViewById(R.id.scrollView);
    	scrollView.removeAllViews();
    	
//    	StringBuffer fileContent = new StringBuffer("");
//    	FileInputStream fileInputStream;
//		int ch;
//		
//		// set filename according to date
//    	String filename = String.valueOf(monthOfYear) + String.valueOf(dayOfMonth) + String.valueOf(year);
//    	Toast.makeText(this, filename, Toast.LENGTH_SHORT).show();
//		
//		fileInputStream = openFileInput(filename);
//		
//		while ((ch = fileInputStream.read()) != -1)
//		{
//			fileContent.append((char)ch); 
//		}
//		
//		JSONArray schedulesArray = new JSONArray(new String(fileContent));
//		
//		// minus one length because of date in array
//		for (int i=0; i<schedulesArray.length()-1; i++)
//    	{
//    		ScheduleEntryField scheduleEntryField = (ScheduleEntryField)findViewById(1000+i);
//    		
//    		JSONObject scheduleJSON = schedulesArray.getJSONObject(i+1);
//    		
//    		scheduleEntryField.setTime1(String.valueOf(scheduleJSON.get("time1")));
//    		scheduleEntryField.setTime2(String.valueOf(scheduleJSON.get("time2")));
//    	}
			

    }
    
    public void setTimeValues(int hour1, int hour2, int minute1, int minute2)
    {
    	this.hour1 = hour1;
    	this.hour2 = hour2;
    	this.minute1 = minute1;
    	this.minute2 = minute2;	
    }
    
    public void deleteSchedule(View v) {
    
    	// find parent entry field
    	ScheduleEntryField scheduleEntryField = (ScheduleEntryField)v.getParent().getParent();
    	
    	// delete all views within entry field and delete entry field itself
		scheduleEntryField.removeAllViews();
		((ScrollView)scheduleEntryField.getParent().getParent()).removeView(scheduleEntryField);
		
		schedulesCount--;
	}
    
    

}
