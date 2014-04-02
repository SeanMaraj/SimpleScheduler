package com.stmaraj.simpleshedule;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class ToDo extends Fragment {

	//TextView lblItem1;
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
 
        View rootView = inflater.inflate(R.layout.activity_todo, container, false);
         
        return rootView;
    }
	
//	@Override
//	protected void onCreate(Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
//		setContentView(R.layout.activity_todo);
//		
//		lblItem1 = (TextView) findViewById(R.id.lblItem1);
//		
//		loadList();
//	}
//
//	private void loadList()
//	{
//		String filename = "Item1";
//		StringBuffer fileContent = new StringBuffer("");
//		int ch;
//		
//		FileInputStream fileInputStream;
//
//		try
//		{
//			fileInputStream = openFileInput(filename);
//			
//			while ((ch = fileInputStream.read()) != -1)
//			{
//				fileContent.append((char)ch); 
//			}
//			
//			lblItem1.setText(new String(fileContent));
//		}
//		catch (FileNotFoundException e) {
//			e.printStackTrace();
//			lblItem1.setText("File Not Found");
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//			
//		
//		
//	}
//
//	@Override
//	public boolean onCreateOptionsMenu(Menu menu) {
//		// Inflate the menu; this adds items to the action bar if it is present.
//		getMenuInflater().inflate(R.menu.to_do, menu);
//		return true;
//	}
//	
//	public void onClick(View v)
//	{
//		Intent intent = new Intent(this, EditToDoList.class);
//		startActivityForResult(intent, 1);
//	}
//	
//	@Override
//	protected void onResume() {
//		// TODO Auto-generated method stub
//		super.onResume();
//		loadList();
//	}
	
	/*@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		
		loadList();
	}*/
}
