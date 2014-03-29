package com.stmaraj.simpleshedule;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TableRow;
import android.widget.TextView;


public class ScheduleEntryField extends LinearLayout{
	
	private TableRow tableRow;
	private TextView time1;
	private TextView time2;
	private EditText entryText;

	public ScheduleEntryField(Context context) {
		super(context);
		
		LayoutInflater inflater = LayoutInflater.from(context);
        inflater.inflate(R.layout.schedule_entry, this);
		
		loadViews();
	}
 
	// called when loading up activity. One field is loaded by default
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
    	
    }
	
	public void setTime1(String text)
    {
    	time1.setText(text);
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
    
    
    
}
