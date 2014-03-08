package com.stmaraj.simpleshedule;

import android.R.integer;
import android.app.AlertDialog;
import android.app.Dialog;
import android.support.v4.app.DialogFragment;

import android.content.DialogInterface;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

public class SetTimeDialog extends DialogFragment {
	View view;
	

	@Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
	    // Get the layout inflater
	    LayoutInflater inflater = getActivity().getLayoutInflater();

	    // Inflate and set the layout for the dialog
	    // Pass null as the parent view because its going in the dialog layout
	    builder.setView(inflater.inflate(R.layout.settime_layout, null))
	    // Add action buttons
	           .setPositiveButton("Pos", new DialogInterface.OnClickListener() {
	               @Override
	               public void onClick(DialogInterface dialogInterface, int id) {
	            	   
						Dialog dialog = SetTimeDialog.this.getDialog();
						TimePicker timePicker1 = (TimePicker)dialog.findViewById(R.id.timePicker1);
						TimePicker timePicker2 = (TimePicker)dialog.findViewById(R.id.timePicker2);
						int hour1 = timePicker1.getCurrentHour();
						int hour2 = timePicker2.getCurrentHour();
						int min1 = timePicker1.getCurrentMinute();
						int min2 = timePicker2.getCurrentMinute();
						
						TextView tv1 = (TextView)getActivity().findViewById(R.id.txtTime1);
						
						tv1.setText(String.valueOf(hour1));
						
						String viewIdString = String.valueOf(getArguments().getInt("viewId"));
						
						int scheduleEntryFieldId = getArguments().getInt("viewId");
						
						ScheduleEntryField scheduleEntryField = (ScheduleEntryField)getActivity().findViewById(scheduleEntryFieldId);
						scheduleEntryField.setTime1("TEst");
						
				    	tv1.setText("fd");
						
						Toast.makeText(getActivity(), viewIdString, Toast.LENGTH_LONG).show();
	                   
	               }
	           })
	           .setNegativeButton("Neg", new DialogInterface.OnClickListener() {
	               public void onClick(DialogInterface dialog, int id) {
	                   //LoginDialogFragment.this.getDialog().cancel();
	               }
	           });      
	    return builder.create();
    }
	
	
	
	
}
