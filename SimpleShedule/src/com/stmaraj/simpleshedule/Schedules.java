package com.stmaraj.simpleshedule;


import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.GregorianCalendar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.content.Context;
import android.text.format.DateFormat;
import android.view.Gravity;
import android.view.LayoutInflater;
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
	Button btnSave, btnLoad;
	
	int id = 1001;
	int dayOfMonth = 0, monthOfYear = 0, year = 0;

	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
 
		// get the main view
        View rootView = inflater.inflate(R.layout.activity_schedules, container, false);

        // set on click listener for save, load, and date buttons
        btnSave = (Button)rootView.findViewById(R.id.btnSave);
        btnLoad = (Button)rootView.findViewById(R.id.btnLoad);
        txtDate = (TextView)rootView.findViewById(R.id.txtDate);
        btnSave.setOnClickListener(this);
        btnLoad.setOnClickListener(this);
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
		relativeLayout = (RelativeLayout)rootView.findViewById(R.id.RelativeLayoutSchedules2);
		relativeLayout.addView(btnAdd, btnAddParams);
		
        return rootView;
    }

	@Override
	public void onClick(View v) {

		try {
			switch( v.getId() )
			{
				case 100:
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
			e.printStackTrace();
		}	
				
	}
	
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
		
		// increment id to ensure each entry has a different id
		id++;
    }
//    
    public void setTime(final View v)
    {
    	FragmentManager ft = getFragmentManager();
    	
    	// send the id of the schedule entry field so text fields can be set
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
    
    // set date variables when user sets date in dialog
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
    	// get linear layout holding the schedule entries
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
    	
    	// create dynamic filename according to date
    	String filename = String.valueOf(monthOfYear) + String.valueOf(dayOfMonth) + String.valueOf(year);
    	
    	// write JSON data file to internal storage
		FileOutputStream outputStream;
		outputStream = getActivity().openFileOutput(filename, Context.MODE_PRIVATE);
		outputStream.write(schedulesArray.toString().getBytes());
		outputStream.close();
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
   
    public void deleteSchedule(View v)
    {
    	// find parent entry field
    	ScheduleEntryField scheduleEntryField = (ScheduleEntryField)v.getParent().getParent();
    	
    	// delete all views within entry field and delete entry field itself
		scheduleEntryField.removeAllViews();
		LinearLayout linearLayout = (LinearLayout)getActivity().findViewById(R.id.linearLayoutSchedules);
		linearLayout.removeView(scheduleEntryField);
    }
    

    
    public void getId(View v)
    {
    	int entryId = ((View)(v.getParent().getParent())).getId();
    	Toast.makeText(getActivity(), String.valueOf(entryId), Toast.LENGTH_SHORT).show();
    }
}
