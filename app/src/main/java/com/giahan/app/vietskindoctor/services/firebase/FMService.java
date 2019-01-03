package com.giahan.app.vietskindoctor.services.firebase;

import static android.support.v4.app.NotificationCompat.GROUP_ALERT_SUMMARY;
import static android.support.v4.app.NotificationCompat.VISIBILITY_PRIVATE;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationCompat.Builder;
import android.text.TextUtils;
import android.util.Log;
import com.giahan.app.vietskindoctor.R;
import com.giahan.app.vietskindoctor.VietSkinDoctorApplication;
import com.giahan.app.vietskindoctor.activity.MainActivity;
import com.giahan.app.vietskindoctor.activity.SplashActivity;
import com.giahan.app.vietskindoctor.domains.NotificationContent;
import com.giahan.app.vietskindoctor.model.event.MessageEvent;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.google.gson.Gson;

import java.util.Map;
import java.util.Random;
import org.greenrobot.eventbus.EventBus;

@SuppressLint("Registered")
public class FMService extends FirebaseMessagingService {

    public static final int NOTIFY_SHOW_NOTIFICATION = 2;
    private static String GROUP_KEY_NOTIFICATION = "com.vietskindoctor.notification_group";

    public static final String CHANNEL_ID = "notify_vietskindoctor_id";

    public static int NOTIFY_SUMMARY = 100;

    private Bitmap icon;
    private NotificationContent notice;

    @Override
    public void onNewToken(final String s) {
        super.onNewToken(s);
        Log.e("FMService", "onNewToken:  -----> "+s);
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        if (remoteMessage.getData() == null) return;
        Map<String, String> notiData = remoteMessage.getData();

        String jsonData = new Gson().toJson(notiData);
        if(!TextUtils.isEmpty(jsonData)){
            notice = new Gson().fromJson(jsonData, NotificationContent.class);
        }

        if (icon == null || icon.isRecycled()) {
            icon = BitmapFactory.decodeResource(VietSkinDoctorApplication.getInstance().getResources(),
                    R.mipmap.ic_launcher);
        }

//        if(remoteMessage.getData().containsKey("description")){
//            EventBus.getDefault().post(new MessageEvent());
//        }else {
//            EventBus.getDefault().post(new MessageEvent());
//        }

        //bui Notification to go to specific activity
        if(notice != null) {
            if (notice.getNotiType() != null) {
                if(notice.getNotiType().equals("2")){
                    buildNotification(MainActivity.getIntent(this, notice.getmSessionID(), "2"), notice.getmNotificationMessage(),
                            (int) System.currentTimeMillis());
                }else if(notice.getNotiType().equals("1")){
                    buildNotification(MainActivity.getIntent(this, notice.getmSessionID(), "1"), notice.getmNotificationMessage(),
                            (int) System.currentTimeMillis());
                }
            }else {
                buildNotification(MainActivity.getIntent(this, notice.getmSessionID(), "0"), notice.getmNotificationMessage(),
                        (int) System.currentTimeMillis());
            }
        }
    }

    private void buildNotification(Intent intent, String message, int notifyID) {
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, notifyID, intent,
                0);
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        setupNotification(message, defaultSoundUri, pendingIntent, notifyID);
    }

    public void setupNotification(String message, Uri defaultSoundUri, PendingIntent
            pendingIntent, int notifyID) {
        NotificationCompat.Builder mBuilderGroup =
                new Builder(getApplicationContext(), CHANNEL_ID);
        mBuilderGroup.setSmallIcon(getNotificationIcon())
                .setLargeIcon(icon)
                .setContentTitle(this.getString(R.string.app_name))
                .setContentText(message)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setGroupSummary(true)
                .setGroup(GROUP_KEY_NOTIFICATION)
                .setGroupSummary(true)
                .setGroupAlertBehavior(GROUP_ALERT_SUMMARY);

        NotificationCompat.Builder mBuilder =
                new Builder(getApplicationContext(), CHANNEL_ID).
                        setSmallIcon(getNotificationIcon())
                        .setLargeIcon(icon)
                        .setAutoCancel(true)
                        .setContentTitle(this.getString(R.string.app_name))
                        .setContentText(message)
                        .setGroup(GROUP_KEY_NOTIFICATION)
                        .setStyle(new NotificationCompat.BigTextStyle().bigText(message))
                        .setContentIntent(pendingIntent);

        if (VERSION.SDK_INT >= VERSION_CODES.M) {
            mBuilderGroup.setColor(this.getColor(R.color.vietskin_color));
            mBuilder.setColor(this.getColor(R.color.vietskin_color));
        }
        NotificationManager mNotificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID,
                    "VietSkin Doctor Title",
                    NotificationManager.IMPORTANCE_DEFAULT);
            mNotificationManager.createNotificationChannel(channel);
            mBuilder.setChannelId(CHANNEL_ID);
        }
        if (VERSION.SDK_INT >= VERSION_CODES.N) {
            mNotificationManager.notify(NOTIFY_SUMMARY, mBuilderGroup.build());
        }
        mNotificationManager.notify(notifyID, mBuilder.build());
    }

    private int getNotificationIcon() {
        boolean useWhiteIcon = (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP);
        return useWhiteIcon ? R.mipmap.logo_app : R.mipmap.ic_launcher;
    }

    public static void cancelAllNotification() {
        NotificationManager mNotificationManager =
                (NotificationManager) VietSkinDoctorApplication.getInstance()
                        .getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.cancelAll();
    }
}
