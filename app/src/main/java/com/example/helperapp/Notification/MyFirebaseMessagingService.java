package com.example.helperapp.Notification;

import android.util.Log;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = "MyFirebaseMsgService";

    @Override
    public void onNewToken(String token) {
        super.onNewToken(token);
        sendTokenToServer(token);
    }

    private void sendTokenToServer(String token) {
        // 서버로 토큰 전송하는 로직을 구현합니다.
        Log.d(TAG, "FCM Token: " + token);
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        // 알림 수신 처리 로직을 구현합니다.
        if (remoteMessage.getNotification() != null) {
            String title = remoteMessage.getNotification().getTitle();
            String body = remoteMessage.getNotification().getBody();
            Log.d(TAG, "Received Notification - Title: " + title + ", Body: " + body);
        }
    }
}

