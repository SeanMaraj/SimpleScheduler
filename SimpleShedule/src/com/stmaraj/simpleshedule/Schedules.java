package com.stmaraj.simpleshedule;


import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.GregorianCalendar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.content.Context;
import android.text.format.DateFormat;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;
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
		btnAddParams.addRule(RelativeLayout.BELOW, R.id.linearLayoutSchedules);
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
				addScheduleField("00:00","00:00", "Empty");
				break;
			case R.id.txtDate:
				setDate();
				break;
		}
	}
	
    public void addScheduleField(String time1, String time2, String entryText)
    {
    	// create new schedule entry field
    	final ScheduleEntryField schedule = new ScheduleEntryField(this);
    	schedule.setId(id);
		schedule.setTime1(time1);
		schedule.setTime2(time2);
		schedule.setEntryText(entryText);
		schedule.setClickable(true);
		
		// add schedule to layout
		LinearLayout linearLayout = (LinearLayout)findViewById(R.id.linearLayoutSchedules);
		linearLayout.addView(schedule);
		
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
    	LinearLayout linearLayout = (LinearLayout)findViewById(R.id.linearLayoutSchedules);
    	
    	// array to save data
    	JSONArray schedulesArray = new JSONArray();
    	
    	// save date
    	JSONObject dateJSON = new JSONObject();
    	GregorianCalendar date = new GregorianCalendar(year, monthOfYear, dayOfMonth);
    	dateJSON.put("date", DateFormat.format("MMMM dd,  yyyy", date));
    	schedulesArray.put(dateJSON);
    	
    	// save all entry field data into array
    	for (int i=0; i<linearLayout.getChildCount(); i++)
    	{
    		ScheduleEntryField scheduleEntryField = (ScheduleEntryField) linearLayout.getChildAt(i);
    		
    		JSONObject scheduleJSON = new JSONObject();
    		scheduleJSON.put("time1", scheduleEntryField.getTime1());
    		scheduleJSON.put("time2", scheduleEntryField.getTime2());
    		scheduleJSON.put("entryText", scheduleEntryField.getEntryText());
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
    	// remove all existing entries
    	LinearLayout linearLayout = (LinearLayout)findViewById(R.id.linearLayoutSchedules);
    	linearLayout.removeAllViews();
    	
    	StringBuffer fileContent = new StringBuffer("");
    	FileInputStream fileInputStream;
		int ch;
		
		// set filename according to date
    	String filename = String.valueOf(monthOfYear) + String.valueOf(dayOfMonth) + String.valueOf(year);
    	Toast.makeText(this, filename, Toast.LENGTH_SHORT).show();
		
		fileInputStream = openFileInput(filename);
		
		while ((ch = fileInputStream.read()) != -1)
		{
			fileContent.append((char)ch); 
		}
		
		JSONArray schedulesArray = new JSONArray(new String(fileContent));
		
		// minus one length because of date in array
		for (int i=0; i<schedulesArray.length()-1; i++)
    	{
			// get times from the JSON file
			JSONObject scheduleJSON = schedulesArray.getJSONObject(i+1);
			String time1 = String.valueOf(scheduleJSON.get("time1"));
    		String time2 = String.valueOf(scheduleJSON.get("time2"));
    		String entryText = String.valueOf(scheduleJSON.get("entryText"));
    		
    		addScheduleField(time1, time2, entryText);
    	}
    }
    
    public void setTimeValues(int hour1, int hour2, int minute1, int minute2)
    {
    	this.hour1 = hour1;
    	this.hour2 = hour2;
    	this.minute1 = minute1;
    	this.minute2 = minute2;	
    }
    
    public void deleteSchedule(View v)
    {
    	// find parent entry field
    	ScheduleEntryField scheduleEntryField = (ScheduleEntryField)v.getParent().getParent();
    	
    	// delete all views within entry field and delete entry field itself
		scheduleEntryField.removeAllViews();
		LinearLayout linearLayout = (LinearLayout)findViewById(R.id.linearLayoutSchedules);
		linearLayout.removeView(scheduleEntryField);
		
		schedulesCount--;
    }
    
    public void getId(View v)
    {
    	int entryId = ((View)(v.getParent().getParent())).getId();
    	Toast.makeText(this, String.valueOf(entryId), Toast.LENGTH_SHORT).show();
    }
}
