package com.stmaraj.simpleshedule;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;


public class ScheduleEntryField extends LinearLayout{
	
	private TextView tv;
	private Button btn;
	

	public ScheduleEntryField(Context context) {
		super(context);
		
		loadViews();
		
	}
 
    public ScheduleEntryField(Context context, AttributeSet attrs) {
        super(context, attrs);
 
        LayoutInflater inflater = LayoutInflater.from(context);
        inflater.inflate(R.layout.test_layout, this);
 
        loadViews();
    }
 
    private void loadViews() {
        tv = (TextView)findViewById(R.id.txtTest);
        btn = (Button)findViewById(R.id.btnTest);

    }


	
	
}
