package com.stmaraj.simpleshedule;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.ListView;

public class ToDo extends Fragment implements OnClickListener {

	EditText entryField;
	List<ToDoListItem> list;
    MyAdapter adapter;
    private boolean inDialog;
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
 
        View rootView = inflater.inflate(R.layout.activity_todo, container, false);
        
        // set onclicklistener for add button
        Button btnSave = (Button)rootView.findViewById(R.id.btnAddItem);
        btnSave.setOnClickListener(this);

        // get the edit text where user enters text
        entryField = (EditText)rootView.findViewById(R.id.entryField);
        
        // instantiate the list
        list = new ArrayList<ToDoListItem>();
        
        // create the adaptor to manage the list
        adapter = new MyAdapter(getActivity().getApplicationContext(), R.layout.list_item, list);
        
        // set the adaptor to the list view
        ListView itemList = (ListView)rootView.findViewById(R.id.itemList);
        itemList.setAdapter(adapter);
        itemList.setLongClickable(true);
        itemList.setClickable(true);

        // open options for item on long click
        itemList.setOnItemLongClickListener(new OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> av, View v, int pos, long id) {
            	Log.i("tag", "here");
            	
            	v.getFocusables(pos);
            	v.setSelected(true);
            	
                return onLongListItemClick(v,pos,id);
            }
        });

        // load item list on launch of application
        try {
			loadItems();
		} catch (Exception e) {
			e.printStackTrace();
		}
         
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
	    public View getView(final int position, View convertView, ViewGroup parent) {
	    	
	    	// get the custom view
    		LayoutInflater inflater = (getActivity()).getLayoutInflater();
	        View row = inflater.inflate(layoutResourceId, parent, false);
	        
	        row.setOnTouchListener(new OnTouchListener() {
				
	        	
	        	// highlight view when touched
				@Override
				public boolean onTouch(View v, MotionEvent event) {
					
					if (!inDialog){
						switch (event.getAction()) {
						case MotionEvent.ACTION_DOWN:
							v.setSelected(true);
							break;
						case MotionEvent.ACTION_UP:
							v.setSelected(false);
						case MotionEvent.ACTION_CANCEL:
						v.setSelected(false);
						default:
							break;
						}
					}
					return false;
				}
			});
	        
	        // set the checkbox according to user data
	        CheckBox checkBox = (CheckBox)row.findViewById(R.id.checkBox);
	        
	        checkBox.setOnCheckedChangeListener(new OnCheckedChangeListener() {
				
				@Override
				public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
					
					// set the item to checked in the data array
					ToDoListItem toDoListItem = list.get(position);
					toDoListItem.setChecked(isChecked);
					list.set(position, toDoListItem);
				}
			});
	        
	        // sets the checkbox in the list item
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
        
        // save item list
        try {
			saveItems();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private boolean onLongListItemClick(final View v, final int pos, long id) {
		
		// flag for when the options dialog is open
		inDialog = true;
		
		String[] items = {"Edit", "Delete"};
		
		// create options dialog for each item
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
	    builder.setTitle("List Item Options")
	           .setItems(items, new DialogInterface.OnClickListener() {
	               public void onClick(DialogInterface dialog, int which) {
	               
	            	switch (which) {
	            	case 0:
	            		editItem(pos);
	            		break;
					case 1:
						deleteItem(pos);
						inDialog = false;
						break;
					default:
						break;
					}
	           }
	    });
	    builder.setOnCancelListener(new OnCancelListener() {
			
			@Override
			public void onCancel(DialogInterface dialog) {
				inDialog = false;
				v.setSelected(false);	
			}
		});

	    AlertDialog alert = builder.create();
        alert.show();

		return true;
	}
	
	private void editItem(final int pos){
		
		// create edit text for user to edit item text
		final EditText editText = new EditText(getActivity().getApplicationContext());
		editText.setHint("Enter Text");
		String itemText = list.get(pos).getText();
		if ( itemText != "")
			editText.setText(list.get(pos).getText());
		
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		builder.setTitle("Edit Item Text")
		.setView(editText)
		.setPositiveButton("Set", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				ToDoListItem toDoListItem = (ToDoListItem)adapter.getItem(pos);
				toDoListItem.setText(editText.getText().toString());
				adapter.remove(list.get(pos));
				adapter.insert(toDoListItem, pos);
				
				// save item list
				try {
					saveItems();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
		AlertDialog alertDialog = builder.create();
		alertDialog.show();
	}
	
	private void deleteItem(int pos){
		
		// delete item from the list
		adapter.remove(list.get(pos));
		
		// save item list
		try {
			saveItems();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void saveItems() throws Exception {
		
    	// array to save data
    	JSONArray listItemJSONArray = new JSONArray();

    	for (ToDoListItem toDoListItem : list) {
    		JSONObject jsonObject = new JSONObject();
    		jsonObject.put("status", toDoListItem.getChecked());
    		jsonObject.put("text", toDoListItem.getText());
			listItemJSONArray.put(jsonObject);
		}
    	

    	// write JSON data file to internal storage
		FileOutputStream outputStream;
		outputStream = getActivity().openFileOutput("itemlist", Context.MODE_PRIVATE);
		outputStream.write(listItemJSONArray.toString().getBytes());
		outputStream.close();
	}
	
	private void loadItems() throws Exception {
		
    	StringBuffer fileContent = new StringBuffer("");
    	FileInputStream fileInputStream;
		int ch;
		
		fileInputStream = getActivity().openFileInput("itemlist");
		
		while ((ch = fileInputStream.read()) != -1)
		{
			fileContent.append((char)ch); 
		}
		
		JSONArray listItemJSONArray = new JSONArray(new String(fileContent));
		
		// minus one length because of date in array
		for (int i=0; i<listItemJSONArray.length(); i++)
    	{
			JSONObject jsonObject = listItemJSONArray.getJSONObject(i);
			ToDoListItem toDoListItem = new ToDoListItem(jsonObject.getBoolean("status"), jsonObject.getString("text"));
			adapter.add(toDoListItem);
    	}
	}
}
