package com.example.helperapp;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.Fragment;

import com.example.helperapp.User.UserAccount;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class PopupFragment extends Fragment {
    private Button notificationButton;
    private static final String CHANNEL_ID = "my_channel_id";
    private static final int NOTIFICATION_ID = 1;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        View view = inflater.inflate(R.layout.foodinfo, container, false);
        notificationButton = view.findViewById(R.id.notificationButton); // 버튼 초기화
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        createNotificationChannel();

        notificationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showNotification();
            }
        });
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.channel_name);
            String description = getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            NotificationManager notificationManager = requireContext().getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    private void showNotification() {
        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference().child("FirebaseEmailAccount/userAccount").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    UserAccount user = dataSnapshot.getValue(UserAccount.class);
                    if (user != null) {
                        String userName = user.getName();
                        if (userName != null) {
                            String title = "도움이 필요해요!";
                            String content = userName + "님이 지금 도움 요청을 하였어요!";

                            Intent cancelIntent = new Intent(getActivity(), MainActivity.class);
                            cancelIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            PendingIntent cancelPendingIntent = PendingIntent.getActivity(requireContext(), 0, cancelIntent, PendingIntent.FLAG_CANCEL_CURRENT | PendingIntent.FLAG_IMMUTABLE);

                            // 확인 버튼을 눌렀을 때 호출되는 인텐트
                            Intent confirmIntent = new Intent(getActivity(), MainActivity.class);
                            confirmIntent.setAction("CONFIRM_ACTION");
                            PendingIntent confirmPendingIntent = PendingIntent.getActivity(requireContext(), 0, confirmIntent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);

                            // 알림을 생성하는 빌더
                            NotificationCompat.Builder builder = new NotificationCompat.Builder(requireContext(), CHANNEL_ID)
                                    .setSmallIcon(R.drawable.notification_icon)
                                    .setContentTitle(title)
                                    .setContentText(content)
                                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                                    .addAction(R.drawable.confirm_icon, "수락", confirmPendingIntent)
                                    .addAction(R.drawable.cancel_icon, "거절", cancelPendingIntent)
                                    .setAutoCancel(true);

                            NotificationManagerCompat notificationManager = NotificationManagerCompat.from(requireContext());
                            notificationManager.notify(NOTIFICATION_ID, builder.build());
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("Firebase", "Error retrieving user data: " + databaseError.getMessage());
            }
        });
    }
}