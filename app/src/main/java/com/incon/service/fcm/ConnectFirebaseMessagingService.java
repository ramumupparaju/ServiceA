package com.incon.service.fcm;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.incon.service.AppConstants;
import com.incon.service.R;
import com.incon.service.ui.home.HomeActivity;
import com.incon.service.utils.SharedPrefsUtils;

import java.util.Map;

import static com.incon.service.AppConstants.LoginPrefs.LOGGED_IN;


/**
 * ` * Created by radar on 30/12/16.
 */
public class ConnectFirebaseMessagingService extends FirebaseMessagingService
        implements AppConstants.PushConstants {

    private static final String TAG = ConnectFirebaseMessagingService.class.getName();

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        //Calling method to generate notification
        sendNotification(remoteMessage.getNotification());

        Bundle bundle = new Bundle();
        for (Map.Entry<String, String> entry : remoteMessage.getData().entrySet()) {
            bundle.putString(entry.getKey(), entry.getValue());
        }

    }

    private void sendNotification(RemoteMessage.Notification messageBody) {

        String title = messageBody.getTitle();
        String description = messageBody.getBody();
        int notificationId = 0;

        Context context = getApplicationContext();
        Intent intent = new Intent(context, HomeActivity.class);
        /*if (!TextUtils.isEmpty(pushType)) {
            intent.putExtra(TueoConstants.PushIntentConstants.PUSH_TYPE, pushType);
            if (pushType.equals(
                    TueoConstants.PushSubTypeConstants.NEW_ALERT_INTERACTION_NAVIGATE)) {
                intent.putExtra(TueoConstants.PushIntentConstants.PUSH_ALERT_ID, alertId);
            }
        }*/
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP
                | Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT);
        int iUniqueId = (int) (System.currentTimeMillis() & 0xfffffff);
        PendingIntent contentIntent = PendingIntent.getActivity(this, iUniqueId, intent, 0);

        Notification.Builder notificationBuilder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationBuilder = new Notification.Builder(context,
                    context.getResources().getString(R.string.notification_id_channel));
        } else {
            notificationBuilder = new Notification.Builder(context);
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationBuilder.setColor(ContextCompat.getColor(context, R.color
                    .colorPrimary));
        }

        notificationBuilder.setSmallIcon(R.mipmap.ic_launcher)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher))
                .setContentTitle(title)
//                .setContentText(message)
                .setStyle(new Notification.BigTextStyle().bigText(description))
                .setContentText(description)
                .setAutoCancel(true)
                .setPriority(Notification.PRIORITY_HIGH)
                .setDefaults(Notification.FLAG_AUTO_CANCEL | Notification.DEFAULT_LIGHTS
                        | Notification.DEFAULT_VIBRATE | Notification.DEFAULT_SOUND)
                .setContentIntent(contentIntent);

        NotificationManager notificationManager = (NotificationManager) getSystemService(
                Context.NOTIFICATION_SERVICE);
        notificationManager.notify(notificationId, notificationBuilder.build());

        //checks whether user is logged or not, and sends local broadcast to refresh screens
        boolean isLoggedIn = SharedPrefsUtils.loginProvider().
                getBooleanPreference(LOGGED_IN, false);
        // checks if loggedin user and notification user matches
        if (isLoggedIn) {
//            sendLocalBroadcast(payload);
        }
    }

    private void sendLocalBroadcast(String payload) {

        /*try {
            JSONObject jsonObject = new JSONObject(payload);
            String pushType = jsonObject.getString("type");
            if (!pushType.equals(TueoConstants.PushSubTypeConstants.GENERIC)
                    && !pushType.equals(TueoConstants.PushSubTypeConstants.NEW_TASK_GENERAL)) {
                AlertsMainFragment.doRefresh = true;
                DashboardMainFragment.doRefresh = true;
            } else if (pushType.equals(TueoConstants.PushSubTypeConstants.NEW_TASK_GENERAL)) {
                DashboardMainFragment.doRefresh = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }*/

        // refreshes the current visible screen.
        Intent intent = new Intent(AppConstants.PushSubTypeConstants.CONNECT_PUSH);
        // You can also include some extra data.
        intent.putExtra(AppConstants.PushIntentConstants.PUSH_PAYLOAD, payload);
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }
}
