package com.stmaraj.simpleshedule;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class MyReceiver extends BroadcastReceiver{

	@Override
	public void onReceive(Context context, Intent intent) {
		
		String content = intent.getExtras().getString("content");
		
		Intent service1 = new Intent(context, MyAlarmService.class);
		service1.putExtra("content", content);
		context.startService(service1);
		
	}

}
