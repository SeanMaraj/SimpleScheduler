package com.stmaraj.simpleshedule;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;

public class ToDo extends Fragment implements OnClickListener {

	EditText entryField;
	List<ToDoListItem> list;
    MyAdapter adapter;
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
 
        View rootView = inflater.inflate(R.layout.activity_todo, container, false);
        
        // set onclicklistener for add button
        Button btnAddItem = (Button)rootView.findViewById(R.id.btnAddItem);
        btnAddItem.setOnClickListener(this);

        // get the edit text where user enters text
        entryField = (EditText)rootView.findViewById(R.id.entryField);
        
        // instantiate the list
        list = new ArrayList<ToDoListItem>();
        
        // create the adaptor to manage the list
        adapter = new MyAdapter(getActivity().getApplicationContext(), R.layout.list_item, list);
        
        // set the adaptor to the list view
        ListView itemList = (ListView)rootView.findViewById(R.id.itemList);
        itemList.setAdapter(adapter);
         
        return rootView;
    }
	
	private class MyAdapter extends ArrayAdapter<ToDoListItem>
	{
		Context context; 
	    int layoutResourceId;    
	    List<ToDoListItem> data = new ArrayList<ToDoListItem>();
	    
	    public MyAdapter(Context context, int layoutResourceId, List<ToDoListItem> data) {
	        super(context, layoutResourceId, data);
	        this.layoutResourceId = layoutResourceId;
	        this.context = context;
	        this.data = data;
	    }
	    
	    // gets the modified adaptor view which is my custom list item view
	    @Override
	    public View getView(int position, View convertView, ViewGroup parent) {
	    	
	    	// get the custom view
    		LayoutInflater inflater = (getActivity()).getLayoutInflater();
	        View row = inflater.inflate(layoutResourceId, parent, false);
	        
	        // set the checkbock according to user data
	        CheckBox checkBox = (CheckBox)row.findViewById(R.id.checkBox);
	        checkBox.setChecked(data.get(position).getChecked());
	        checkBox.setText(data.get(position).getText());
	        
	    	return row;	    	
	    }	
	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.btnAddItem:
			addItem();
			break;
		default:
			break;
		}
	}

	private void addItem() {
		// create new item using user data
		ToDoListItem item = new ToDoListItem(false, entryField.getText().toString());
		
		// add item to list
		adapter.add(item);
        adapter.notifyDataSetChanged();	
	}
}
