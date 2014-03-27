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

		// Inflate and set the layout for the dialog
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		LayoutInflater inflater = getActivity().getLayoutInflater();
		builder.setView(inflater.inflate(R.layout.settime_layout, null))

				.setTitle("Choose Time")
				.setPositiveButton("Set",
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialogInterface, int id) {
						
								Dialog dialog = SetTimeDialog.this.getDialog();
								TimePicker timePicker1 = (TimePicker) dialog.findViewById(R.id.timePicker1);
								TimePicker timePicker2 = (TimePicker) dialog.findViewById(R.id.timePicker2);

								// get all values
								int hour1 = timePicker1.getCurrentHour();
								int hour2 = timePicker2.getCurrentHour();
								int min1 = timePicker1.getCurrentMinute();
								int min2 = timePicker2.getCurrentMinute();

								// get the id of the schedule field entry that was clicked
								int scheduleEntryFieldId = getArguments().getInt("viewId");
								Schedules schedulesActivity = (Schedules) getActivity();
								ScheduleEntryField scheduleEntryField = (ScheduleEntryField) schedulesActivity.findViewById(scheduleEntryFieldId);
								
								// display the chosen time in the schedule entry field
								scheduleEntryField.setTime1(String.valueOf(hour1 + ":" + String.valueOf(min1)));
								scheduleEntryField.setTime2(String.valueOf(hour2 + ":" + String.valueOf(min2)));
							}
						})
				.setNegativeButton("Cancel",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								// LoginDialogFragment.this.getDialog().cancel();
							}
						});
		return builder.create();
	}

}
