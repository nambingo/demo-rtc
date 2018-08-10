package com.giahan.app.vietskindoctor.services.firebase;

import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.text.TextUtils;
import com.giahan.app.vietskindoctor.R;
import com.giahan.app.vietskindoctor.VietSkinApplication;
import com.giahan.app.vietskindoctor.activity.ControlActivity;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

@SuppressLint("Registered")
public class FMService extends FirebaseMessagingService {

    public static final int NOTIFY_SHOW_NOTIFICATION = 2;
    private Bitmap icon;

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        if (remoteMessage.getData() == null) return;
        if (remoteMessage.getNotification() == null) return;
        if (icon == null || icon.isRecycled()) {
            icon = BitmapFactory.decodeResource(VietSkinApplication.getInstance().getResources(),
                    R.mipmap.ic_launcher);
        }
        if (TextUtils.isEmpty(remoteMessage.getNotification().getBody())) return;
        buildNotification(new Intent(this, ControlActivity.class), remoteMessage
                        .getNotification()
                .getBody(),
                NOTIFY_SHOW_NOTIFICATION);
    }

    private void buildNotification(Intent intent, String message, int notifyID) {
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT);
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        setupNotification(message, defaultSoundUri, pendingIntent, notifyID);
    }

    private void setupNotification(String message, Uri defaultSoundUri, PendingIntent
            pendingIntent, int notifyID){
        NotificationManager notificationManager = (NotificationManager) getApplicationContext().getSystemService
                (Context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder builder = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel
                    notificationChannel = new NotificationChannel("ID", "Name", importance);
            notificationManager.createNotificationChannel(notificationChannel);
        }
        builder = new NotificationCompat.Builder(getApplicationContext());
        builder = builder
                .setSmallIcon(R.mipmap
                        .ic_launcher)
                .setLargeIcon(icon)
                .setContentTitle(this.getString(R.string.app_name))
                .setContentText(message)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(message))
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);
        notificationManager.notify(notifyID, builder.build());
    }
}
