package com.bryansoria.socialappv4.Model;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.bryansoria.socialappv4.ChatActivity;
import com.bryansoria.socialappv4.R;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class MyFirebaseMessagingService extends FirebaseMessagingService{


    //Dentro de este metodo crearemos la notificacion
    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        String title = remoteMessage.getNotification().getTitle();
        String body = remoteMessage.getNotification().getBody();

        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(),"CHAT");
        builder.setContentTitle(title);
        builder.setContentText(body);
        builder.setSmallIcon(R.drawable.logo);

        Intent intent = null;
        if (remoteMessage.getData().get("type").equalsIgnoreCase("sms")){
            intent = new Intent(this, ChatActivity.class);
            intent.putExtra("OtherUserID",remoteMessage.getData().get("userID"));
        }
        PendingIntent pendingIntent = PendingIntent.getActivity(this,101, intent,PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(pendingIntent);

        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        manager.notify(123,builder.build());

    }
}
