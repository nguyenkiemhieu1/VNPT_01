package vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.service.notification;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.IdRes;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;
import android.view.View;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.alamkanak.weekview.WeekViewEvent;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.jaredrummler.android.processes.AndroidProcesses;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.Date;
import me.leolin.shortcutbadger.ShortcutBadger;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.R;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.common.Constants;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.configuration.Application;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.configuration.ConfigNotification;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.configuration.NotificationUtils;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.event.NumberbadgeEvent;


/**
 * Created by LinhLK - 0948012236 on 9/29/2016.
 */

public class VNPTiOfficeFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = "TagFirebaseMessService";
    private NotificationUtils notificationUtils;

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        if (remoteMessage == null) return;
        //Displaying data in log
        //It is optional
        Log.d(TAG, "From: " + remoteMessage.getFrom());
        //Calling method to generate notification
        // Check if message contains a notification payload.
        String title = "";
        String content = "";
        int badges = 0;
        if (remoteMessage.getNotification() != null) {
            Log.e(TAG, "Notification Body: " + remoteMessage.getNotification().getBody());
            content = remoteMessage.getNotification().getBody();
            title = remoteMessage.getNotification().getTitle();

        }
        JSONObject json = null;
        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
            Log.e(TAG, "PersonSendNotifyInfo Payload: " + remoteMessage.getData().toString());
            try {
                json = new JSONObject(remoteMessage.getData());
                ShortcutBadger.applyCount(getApplicationContext(), Integer.parseInt(json.getString(ConfigNotification.NOTIFICATION_BADGE)));
                EventBus.getDefault().postSticky(new NumberbadgeEvent(Integer.parseInt(json.getString(ConfigNotification.NOTIFICATION_BADGE))));
            } catch (Exception e) {
                Log.e(TAG, "Exception: " + e.getMessage());
            }
        }
        try {
            if (title == null || title.equals("")) {
                title = json.getString("title");
            }
            if (content == null || content.equals("")) {
                content = json.getString("body");
            }
            if (json != null && json.getInt("badge") > 0) {
                badges = json.getInt("badge");
            }


        } catch (Exception ex) {
            title = getString(R.string.NEW_NOTIFICATION);
            content = getString(R.string.NEW_NOTIFICATION);
        }
        if (title == null || title.equals("")) {
            title = getString(R.string.NEW_NOTIFICATION);
        }
        if (content == null || content.equals("")) {
            content = getString(R.string.NEW_NOTIFICATION);
        }
        if (title != null && !title.equals("") && content != null && !content.equals("") && json != null) {
            try {
                final String notificationId = json.getString(ConfigNotification.NOTIFICATION_IOFFICE_ID);
                int notificationID = 0;
                notificationID = Integer.parseInt(notificationId);
                if (Application.getApp().getAppPrefs().isLogined()) {
                    if (json.getString("type") != null && !json.getString("type").contains(Constants.TYPE_NOTIFY_SIGN_DOCUMENT))
                        ShowNotify(getApplicationContext(), title, content, badges, notificationUtils.createNotificationChannel(this), notificationID, json);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public void ShowNotify(Context context, String title, String message, int badges, String CHANNEL_ID, int Notification_ID, JSONObject jsonObject) {
        notificationUtils = new NotificationUtils(context);
        notificationUtils.playNotificationSound(context);
        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        Intent broadcastIntent = new Intent(this, HandleNotificationReceiver.class);
        broadcastIntent.putExtra("TITLE", title);
        broadcastIntent.putExtra("MESSAGE", message);
        broadcastIntent.putExtra("JSON", jsonObject.toString());

        PendingIntent actionIntent = PendingIntent.getBroadcast(this, 0, broadcastIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        @SuppressLint("ResourceAsColor") Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle(title)
                .setContentText(message)
                .setTicker(title)
                .setWhen(notificationUtils.getTimeMilliSec(new Date().toString()))
                .setSmallIcon(R.drawable.logo_vnpt)
                .setLargeIcon(BitmapFactory.decodeResource(this.getResources(), R.mipmap.ic_app_launcher))
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .setColor(getResources().getColor(R.color.colorTextBlue))
                .setSound(alarmSound)
                .setContentIntent(actionIntent)
                .setAutoCancel(true)
                .setOnlyAlertOnce(true)
                .setStyle(new NotificationCompat
                        .BigTextStyle()
                        .bigText(message)
                        .setBigContentTitle(title))
                .build();
        ShortcutBadger.applyCount(context, badges);
        notificationManager.notify(Notification_ID, notification);
    }


}
