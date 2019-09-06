package com.example.noifydemo;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.NotificationChannel;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;

import android.graphics.drawable.Icon;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    NotificationManager notificationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        createNotficationChannel("com.example.notifydemo.news", "Notify Demo News", "Example News Channel");

        Button button = findViewById(R.id.sendNotification);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendNotification();
            }
        });
    }

    protected void createNotficationChannel(String id, String name, String description){
        int importance = notificationManager.IMPORTANCE_LOW;

        NotificationChannel channel = new NotificationChannel(id, name, importance);

        channel.setDescription(description);
        channel.enableLights(true);
        channel.setLightColor(Color.RED);
        channel.enableVibration(true);
        channel.setVibrationPattern(
                new long[]{100,200,300,400,500,400,300,200,400}
        );
        notificationManager.createNotificationChannel(channel);
    }

    protected void sendNotification(){
        String channelID = "com.example.notifydemo.news";
        int notificationID = 101;

        Intent resultIntent = new Intent(this, ResultActivity.class);

        PendingIntent pendingIntent = PendingIntent.getActivity(
                this,
                0,
                resultIntent,
                PendingIntent.FLAG_UPDATE_CURRENT
        );

        final Icon icon = Icon.createWithResource(MainActivity.this, android.R.drawable.ic_dialog_info);

        Notification.Action action = new Notification.Action.Builder(icon, "Open", pendingIntent).build();

        Notification notification = new Notification.Builder(MainActivity.this, channelID)
                .setContentTitle("Example Notfication")
                .setContentText("This is an example notifcation")
                .setSmallIcon(android.R.drawable.ic_dialog_info)
                .setChannelId(channelID)
                .setNumber(10)
                .setContentIntent(pendingIntent)
                .setActions(action)
                .build();

        notificationManager.notify(notificationID, notification);
    }
}
