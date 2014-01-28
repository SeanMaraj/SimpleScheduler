package com.stmaraj.simpleshedule;

import android.os.Bundle;
import android.R.integer;
import android.app.Activity;
import android.util.AttributeSet;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.Toast;

public class Schedules extends Activity {
	
	
	TableLayout tableLayout1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
	    tableLayout1 = (TableLayout) findViewById(R.id.TableLayout1);

		
		
		setContentView(R.layout.activity_schedules);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.schedules, menu);
		return true;
	}
	
	public void btnAddClick(View v)
	{
//		int i = v.getId();
//		int kj = R.id.btnAdd;
//		
//		if (i == kj)
//		{
//		Toast toast = Toast.makeText(this, i, Toast.LENGTH_LONG);
//		toast.show();
//		}
		
		EditText e1 = new EditText(this);
		
		TableLayout.LayoutParams pramss = new TableLayout.LayoutParams(LayoutParams.FILL_PARENT,
                LayoutParams.WRAP_CONTENT, 1);
		
e1.setLayoutParams(pramss);
		
		tableLayout1.addView(e1);
		
	}

}
