package com.stmaraj.simpleshedule;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


public class ScheduleEntryField extends LinearLayout{
	
	private TextView time1;
	private TextView time2;
	private EditText entryText;
	private Button btnOptions;
	private Button btnChangeTime;
	
	private int alarmHour;
	private int alarmMinute;

	public ScheduleEntryField(Context context) {
		super(context);
		
		LayoutInflater inflater = LayoutInflater.from(context);
        inflater.inflate(R.layout.schedule_entry, this);
		
		loadViews();
	}
 
	// called when loading up activity. One field is loaded in this app
	public ScheduleEntryField(Context context, AttributeSet attrs) {
        super(context, attrs);
 
        LayoutInflater inflater = LayoutInflater.from(context);
        inflater.inflate(R.layout.schedule_entry, this);
 
        loadViews();
    }
 
	private void loadViews() {

    	time1 = (TextView)findViewById(R.id.txtTime1);
    	time2 = (TextView)findViewById(R.id.txtTime2);
    	entryText = (EditText)findViewById(R.id.edtEntry);
    	btnChangeTime = (Button)findViewById(R.id.txtTimeChange);
    	btnOptions = (Button)findViewById(R.id.btnOptions);
    	
    	entryText.setHint("Enter text");
    }
	
	public void setTime1(String text, int hour, int minute)
    {
    	time1.setText(text);
    	alarmHour = hour;
    	alarmMinute = minute;
    }
    
	public void setTime2(String text)
    {
    	time2.setText(text);
    }
    
	public String getTime1()
    {
    	return (String)time1.getText();
    }
    
	public String getTime2()
    {
		return (String)time2.getText();
    }
    
	public void setEntryText(String text)
    {
    	entryText.setText(text);
    }
    
	public String getEntryText()
    {
    	return entryText.getText().toString();
		
	}
	
	public int getAlarmHour()
	{
		return alarmHour;
	}
	
	public int getAlarmMinute()
	{
		return alarmMinute;
	}

	public void setButtonOnClick(OnClickListener l)
	{
		btnOptions.setOnClickListener(l);
		btnChangeTime.setOnClickListener(l);
	}
	
    
    
    
}
