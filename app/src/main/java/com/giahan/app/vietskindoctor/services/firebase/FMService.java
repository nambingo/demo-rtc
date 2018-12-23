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
import android.os.Build;
import android.support.v4.app.NotificationCompat;
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
    private Bitmap icon;
    private NotificationContent notice;

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

        if(remoteMessage.getData().containsKey("description")){
            EventBus.getDefault().post(new MessageEvent());
        }else {
            EventBus.getDefault().post(new MessageEvent());
        }

        buildNotification(MainActivity.getIntent(this, notice.getmSessionID()), notice.getmNotificationMessage(),
                (int)System.currentTimeMillis());
    }

    private void buildNotification(Intent intent, String message, int notifyID) {
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, notifyID, intent,
                0);
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
        EventBus.getDefault().post(new MessageEvent());
        builder = new NotificationCompat.Builder(getApplicationContext());
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Log.e("FMService", "setupNotification: 1 -----> ");
            builder = builder
                    .setSmallIcon(R.mipmap.logo_app)
                    .setColor(getResources().getColor(R.color.vietskin_color))
                    .setLargeIcon(icon)
                    .setContentTitle(this.getString(R.string.app_name))
                    .setContentText(message)
                    .setStyle(new NotificationCompat.BigTextStyle().bigText(message))
                    .setAutoCancel(true)
                    .setSound(defaultSoundUri)
                    .setContentIntent(pendingIntent);
        } else {
            Log.e("FMService", "setupNotification: 2 -----> ");
            builder = builder
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setLargeIcon(icon)
                    .setContentTitle(this.getString(R.string.app_name))
                    .setContentText(message)
                    .setStyle(new NotificationCompat.BigTextStyle().bigText(message))
                    .setAutoCancel(true)
                    .setSound(defaultSoundUri)
                    .setContentIntent(pendingIntent);
        }
        notificationManager.notify(notifyID, builder.build());
    }
}
