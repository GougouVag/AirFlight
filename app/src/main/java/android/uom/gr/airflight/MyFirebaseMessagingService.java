package android.uom.gr.airflight;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.WakefulBroadcastReceiver;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Calendar;
import java.util.List;
import java.util.Map;

/**
 * Created by LamPrOs on 15/1/2017.
 */

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = "MyFirebaseMsgService";

    /**
     * Called when message is received.
     *
     * @param remoteMessage Object representing the message received from Firebase Cloud Messaging.
     */
    // [START receive_message]
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
//        Map<String, String> data = remoteMessage.getData();
//        String myCustomKey = data.get("my_custom_key");
//        Log.i("myLams",myCustomKey);
        // [START_EXCLUDE]
        // There are two types of messages data messages and notification messages. Data messages are handled
        // here in onMessageReceived whether the app is in the foreground or background. Data messages are the type
        // traditionally used with GCM. Notification messages are only received here in onMessageReceived when the app
        // is in the foreground. When the app is in the background an automatically generated notification is displayed.
        // When the user taps on the notification they are returned to the app. Messages containing both notification
        // and data payloads are treated as notification messages. The Firebase console always sends notification
        // messages. For more see: https://firebase.google.com/docs/cloud-messaging/concept-options
        // [END_EXCLUDE]

        // TODO(developer): Handle FCM messages here.
        // Not getting messages here? See why this may be: https://goo.gl/39bRNJ


        sendNotification(remoteMessage.getNotification().getBody());

        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(this);

        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        builder.setSound(alarmSound);


        Log.d(TAG, "From: " + remoteMessage.getFrom());

        Log.i("notilam",remoteMessage.getFrom()+","+ remoteMessage.getData()+","+ remoteMessage.getNotification().getBody()+","+remoteMessage.getNotification().getTitle()+","+remoteMessage.getNotification().getBody());

        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
            Log.d(TAG, "Message data payload: " + remoteMessage.getData());
        }

        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {
            Log.d(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());
        }

        // Also if you intend on generating your own notifications as a result of a received FCM
        // message, here is where that should be initiated. See sendNotification method below.
    }
    // [END receive_message]

    /**
     * Create and show a simple notification containing the received FCM message.
     *
     * @param messageBody FCM message body received.
     */
    private void sendNotification(String messageBody) {
        Log.i("lans","lam");
        Log.i("lams",messageBody);
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT);

        Uri defaultSoundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("FCM Message")
                .setContentText(messageBody)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());

        Calendar c = Calendar.getInstance();
        int seconds = c.get(Calendar.SECOND);
        int min = c.get(Calendar.MINUTE);
        int hour = c.get(Calendar.HOUR);
        int date = c.get(Calendar.DATE);
        int month = c.get(Calendar.MONTH);
        int year = c.get(Calendar.YEAR);
        String theMin ="";
        String thehour="";
        if(min>=0 && min<=9){
            theMin = String.format("%02d", min);
        }else{
            theMin = String.valueOf(min);
        }
        if(hour>=0 && hour<=9){
            thehour = String.format("%02d", hour);
        }else{
            thehour = String.valueOf(hour);
        }
        MyDBNotifications db = new  MyDBNotifications(this);

        List<ItemForNotification> notification = db.getAllContacts();

        Log.d("Insert: ", "Inserting ..");
        db.addContact(new ItemForNotification(notification.size() + 1, thehour+":"+theMin+","+String.valueOf(date)+"/"+String.valueOf(month)+"/"+String.valueOf(year) , messageBody));

    }


}