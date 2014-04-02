package com.stmaraj.simpleshedule;


import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.GregorianCalendar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.content.Context;
import android.text.format.DateFormat;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

public class Schedules extends Fragment implements OnClickListener {
	
	RelativeLayout relativeLayout;
	ScheduleEntryField firstSchedule;
	Button btnAdd;
	TextView txtDate;
	TimePicker timePicker;
	SetTimeDialog setTimeDialog;
	View mainView;
	Button btnId, btnSave, btnLoad;
	
	
	int id = 1001;
	int dayOfMonth = 0, monthOfYear = 0, year = 0 ;
	int hour1, hour2;
	int minute1, minute2;
	
	int schedulesCount = 1;
	
	
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
 
        View rootView = inflater.inflate(R.layout.activity_schedules, container, false);
        mainView = rootView;
        
        
        
        
        btnSave = (Button)rootView.findViewById(R.id.btnSave);
        btnLoad = (Button)rootView.findViewById(R.id.btnLoad);
        btnId = (Button)rootView.findViewById(R.id.btnId);
        btnSave.setOnClickListener(this);
        btnLoad.setOnClickListener(this);
        
        relativeLayout = (RelativeLayout)rootView.findViewById(R.id.RelativeLayoutSchedules2);
		txtDate = (TextView)rootView.findViewById(R.id.txtDate);
		txtDate.setOnClickListener(this);
		
		// set id of first schedule which has already been created so future schedules can stack
		firstSchedule = (ScheduleEntryField)rootView.findViewById(R.id.firstSchedule);
		firstSchedule.setId(1000);
		firstSchedule.setButtonOnClick(this);
		
		// create add button and place below first schedule
		btnAdd = new Button(getActivity());
		btnAdd.setId(100);
		btnAdd.setOnClickListener(this);
		btnAdd.setGravity(Gravity.CENTER);
		RelativeLayout.LayoutParams btnAddParams = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		btnAddParams.addRule(RelativeLayout.BELOW, R.id.linearLayoutSchedules);
		relativeLayout.addView(btnAdd, btnAddParams);
		
		
         
        return rootView;
    }

	@Override
	public void onClick(View v) {
		Toast.makeText(getActivity(), "outside", Toast.LENGTH_SHORT).show();
		try {
			switch( v.getId() )
			{
				case 100:
					//Toast.makeText(getActivity(), "inside", Toast.LENGTH_SHORT).show();
					addScheduleField("00:00","00:00", "Empty");
					break;
				case R.id.btnId:
					getId(v);
					break;
				case R.id.btnDelete:
					deleteSchedule(v);
					break;
				case R.id.txtTimeChange:
					setTime(v);
					break;
				case R.id.btnSave:
					saveSchedules(v);
					break;
				case R.id.btnLoad:
					loadSchedules(v);
					break;
				case R.id.txtDate:
					setDate();
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
				
	}
//	
    public void addScheduleField(String time1, String time2, String entryText)
    {
    	// create new schedule entry field
    	final ScheduleEntryField schedule = new ScheduleEntryField(getActivity());
    	schedule.setId(id);
		schedule.setTime1(time1);
		schedule.setTime2(time2);
		schedule.setEntryText(entryText);
		schedule.setClickable(true);
		schedule.setButtonOnClick(this);
		
		// add schedule to layout
		LinearLayout linearLayout = (LinearLayout)getActivity().findViewById(R.id.linearLayoutSchedules);
		linearLayout.addView(schedule);
		
		schedulesCount++;
		id++;
    }
//    
    public void setTime(final View v)
    {
    	FragmentManager ft = getFragmentManager();
    	
    	
    	Toast.makeText(getActivity(), "inside", Toast.LENGTH_SHORT).show();
    	
    	// send the id of the schedule entry field
    	Bundle bundle = new Bundle(1);
    	bundle.putInt("viewId", ((View)v.getParent().getParent()).getId());
    	
    	setTimeDialog = new SetTimeDialog();
	    setTimeDialog.setArguments(bundle);
    	setTimeDialog.show(ft, "setTimeD"); 
    }
    
    public void setDate() 
    {
    	// create dialog to set the date
		DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), dateSetListener, year, monthOfYear, dayOfMonth);
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
    	LinearLayout linearLayout = (LinearLayout)getActivity().findViewById(R.id.linearLayoutSchedules);
    	
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
		outputStream = getActivity().openFileOutput(filename, Context.MODE_PRIVATE);
		outputStream.write(schedulesArray.toString().getBytes());
		outputStream.close();
	
		Toast.makeText(getActivity(), filename, Toast.LENGTH_SHORT).show();
    }
    
    public void loadSchedules(View v) throws JSONException, IOException
    {
    	// remove all existing entries
    	LinearLayout linearLayout = (LinearLayout)getActivity().findViewById(R.id.linearLayoutSchedules);
    	linearLayout.removeAllViews();
    	
    	StringBuffer fileContent = new StringBuffer("");
    	FileInputStream fileInputStream;
		int ch;
		
		// set filename according to date
    	String filename = String.valueOf(monthOfYear) + String.valueOf(dayOfMonth) + String.valueOf(year);
    	Toast.makeText(getActivity(), filename, Toast.LENGTH_SHORT).show();
		
		fileInputStream = getActivity().openFileInput(filename);
		
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
		LinearLayout linearLayout = (LinearLayout)getActivity().findViewById(R.id.linearLayoutSchedules);
		linearLayout.removeView(scheduleEntryField);
		
		schedulesCount--;
    }
    

    
    public void getId(View v)
    {
    	int entryId = ((View)(v.getParent().getParent())).getId();
    	Toast.makeText(getActivity(), String.valueOf(entryId), Toast.LENGTH_SHORT).show();
    }
}
