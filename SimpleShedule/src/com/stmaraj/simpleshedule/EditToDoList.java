package com.stmaraj.simpleshedule;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;

public class EditToDoList extends Activity {

	EditText txtToDoListItem;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_to_do_list);
		
		txtToDoListItem = (EditText) findViewById(R.id.txtToDoItem);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.edit_to_do_list, menu);
		return true;
	}
	
	public void btnSave(View v)
	{
		String filename = "Item1";
		String item1Content = txtToDoListItem.getText().toString();
		FileOutputStream outputStream;
		
		try {
			outputStream = openFileOutput(filename, Context.MODE_PRIVATE);
			outputStream.write(item1Content.getBytes());
			outputStream.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//setResult(RESULT_OK);
		finish();
	}
	
	@Override
	public void onBackPressed() {
		//setResult(RESULT_OK);
		finish();
	}

}
