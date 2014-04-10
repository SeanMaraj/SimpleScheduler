package com.stmaraj.simpleshedule;

import android.app.Activity;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;

public class DisplayNotification extends Activity {

	
	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		
		//int notifyId = getIntent().getExtras().getInt("NotifyID");
//		
		NotificationCompat.Builder mBuilder =
    	        new NotificationCompat.Builder(getApplicationContext())
    	        .setSmallIcon(R.drawable.ic_launcher)
    	        .setContentTitle("My notification")
    	        .setContentText("Hello World!");
    	// Creates an explicit intent for an Activity in your app
    	Intent resultIntent = new Intent(getApplicationContext(), Test.class);

    	// The stack builder object will contain an artificial back stack for the
    	// started Activity.
    	// This ensures that navigating backward from the Activity leads out of
    	// your application to the Home screen.
    	TaskStackBuilder stackBuilder = TaskStackBuilder.create(getApplicationContext());
    	// Adds the back stack for the Intent (but not the Intent itself)
    	stackBuilder.addParentStack(Test.class);
    	// Adds the Intent that starts the Activity to the top of the stack
    	stackBuilder.addNextIntent(resultIntent);
    	PendingIntent resultPendingIntent =
    	        stackBuilder.getPendingIntent(
    	            0,
    	            PendingIntent.FLAG_UPDATE_CURRENT
    	        );
    	mBuilder.setContentIntent(resultPendingIntent);
    	NotificationManager mNotificationManager =
    	    (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
    	// mId allows you to update the notification later on.
    	mNotificationManager.notify(201245, mBuilder.build());
    	
    	// cancel the activity so an empty view doesn't show up 
    	finish();
	}
}
