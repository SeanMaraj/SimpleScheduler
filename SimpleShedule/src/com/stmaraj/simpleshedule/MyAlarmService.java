package com.stmaraj.simpleshedule;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

public class MyAlarmService extends Service {

	private NotificationManager mManager;
	
	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
    public void onCreate() 
    {
       // TODO Auto-generated method stub  
       super.onCreate();
    }
	
	@SuppressWarnings("static-access")
	   @Override
	   public void onStart(Intent intent, int startId)
	   {		
			NotificationCompat.Builder mBuilder =
	    	        new NotificationCompat.Builder(getApplicationContext())
	    	        .setSmallIcon(R.drawable.ic_launcher)
	    	        .setContentTitle(intent.getExtras().getString("content"))
	    	        .setAutoCancel(true)
	    	        .setContentText("Hello World!");
			
	    	// Creates an explicit intent for MainActivity
	    	Intent resultIntent = new Intent(getApplicationContext(), MainActivity.class);
	    	// This ensures that navigating backward from the Activity leads to home screen
	    	TaskStackBuilder stackBuilder = TaskStackBuilder.create(getApplicationContext());
	    	// Adds the back stack for the Intent (but not the Intent itself)
	    	stackBuilder.addParentStack(MainActivity.class);
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
	    	mNotificationManager.notify(1, mBuilder.build());
	    	
	    	
//	       super.onStart(intent, startId);
//		
//	       mManager = (NotificationManager) this.getApplicationContext().getSystemService(this.getApplicationContext().NOTIFICATION_SERVICE);
//	       Intent intent1 = new Intent(this.getApplicationContext(),MainActivity.class);
//	     
//	       Notification notification = new Notification(R.drawable.ic_launcher,"This is a test message!", System.currentTimeMillis());
//	       intent1.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP| Intent.FLAG_ACTIVITY_CLEAR_TOP);
//	 
//	       PendingIntent pendingNotificationIntent = PendingIntent.getActivity( this.getApplicationContext(),0, intent1,PendingIntent.FLAG_UPDATE_CURRENT);
//	       notification.flags |= Notification.FLAG_AUTO_CANCEL;
//	       notification.setLatestEventInfo(this.getApplicationContext(), "AlarmManagerDemo", "This is a test message!", pendingNotificationIntent);
//	 
//	       mManager.notify(0, notification);
	    }
	 
	    @Override
	    public void onDestroy() 
	    {
	        // TODO Auto-generated method stub
	        super.onDestroy();
	    }
}
