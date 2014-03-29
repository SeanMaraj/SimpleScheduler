package com.stmaraj.simpleshedule;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;

public class Test extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_test);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.test, menu);
		return true;
	}
	
	public void onClick(View v)
	{
//		Toast toast = Toast.makeText(this, "Hello", Toast.LENGTH_SHORT);
//		toast.show();
//		
//		
//		LinearLayout l1 = (LinearLayout)findViewById(R.id.linLay1);
//		
//		EditText e1 = new EditText(this);
//		
//		TableLayout.LayoutParams pramss = new TableLayout.LayoutParams(LayoutParams.FILL_PARENT,
//                LayoutParams.WRAP_CONTENT, 1);
//		
//		e1.setLayoutParams(pramss);
//		
//		l1.addView(e1);
	}

}
