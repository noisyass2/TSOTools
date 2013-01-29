package ph.asaboi.droidz.tsotools;

import android.app.Activity;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.util.Log;
import android.view.Menu;
import android.view.View;

public class NotificationsActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_notifications);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_notifications, menu);
		return true;
	}

	public void createNotification(View view) {
	    // Prepare intent which is triggered if the
	    // notification is selected
		Log.d("TSO","Noti");
	    Intent intent = new Intent(this, NotificationsActivity.class);
	    PendingIntent pIntent = PendingIntent.getActivity(this, 0, intent, 0);
	    
	    Intent resultIntent = new Intent(this, NotificationsActivity.class);
	    

	    // Build notification
	    // Actions are just fake
	    NotificationCompat.Builder noti = new NotificationCompat.Builder(this)
	    	.setSmallIcon(R.drawable.coin)
	        .setContentTitle("New mail from " + "test@gmail.com")
	        .setContentText("Subject")	  ;      
	    
	    TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
	    stackBuilder.addParentStack(NotificationsActivity.class);
	    stackBuilder.addNextIntent(resultIntent);
	    
	    PendingIntent resultPendingIntent =
	            stackBuilder.getPendingIntent(
	                0,
	                PendingIntent.FLAG_UPDATE_CURRENT
	            );
	    
	    noti.setContentIntent(resultPendingIntent);

	    
	    //	        .addAction(R.drawable.icon, "Call", pIntent)
//	        .addAction(R.drawable.icon, "More", pIntent)
//	        .addAction(R.drawable.icon, "And more", pIntent)
	    NotificationManager mNotificationManager =
	    	    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
	    
	    mNotificationManager.notify(0,noti.build());
	    

	  }
}
