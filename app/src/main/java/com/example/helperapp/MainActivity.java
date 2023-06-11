package com.example.helperapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.example.helperapp.User.UserAccount;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    private static final String CHANNEL_ID = "my_channel_id";
    private static final int NOTIFICATION_ID = 1;
    private BottomNavigationView mBottomNV;

    private FragmentPage1 fragmentPage1;
    private MapFragment mapFragment;
    private PopupFragment popupFragment;
    private FragmentPage4 fragmentPage4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        createNotificationChannel();

        mBottomNV = findViewById(R.id.nav_view);
        mBottomNV.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                BottomNavigate(menuItem.getItemId());
                return true;
            }
        });
        mBottomNV.setSelectedItemId(R.id.navigation_1);

        fragmentPage1 = new FragmentPage1();
        mapFragment = new MapFragment();
        popupFragment = new PopupFragment();
        fragmentPage4 = new FragmentPage4();

        // 초기 실행 시 FragmentPage1을 표시
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.content_layout, fragmentPage1, String.valueOf(R.id.navigation_1));
        fragmentTransaction.commit();

        handleIntent(getIntent());
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.channel_name);
            String description = getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            channel.enableLights(true);
            channel.setLightColor(Color.RED);
            channel.enableVibration(true);
            NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.createNotificationChannel(channel);
        }
    }

    private void handleIntent(Intent intent) {
        if (intent != null && intent.getAction() != null) {
            if (intent.getAction().equals("CONFIRM_ACTION")) {
                showNotification2();
            }
        }
    }

    private void showNotification2() {
        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference().child("FirebaseEmailAccount/userAccount").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    UserAccount user = dataSnapshot.getValue(UserAccount.class);
                    if (user != null) {
                        String userName = user.getName();
                        String teleNum = user.getTeleNum();
                        if (userName != null) {
                            String title = "도움 요청을 수락했어요!";
                            String content = userName + "님이 지금 도움 요청을 수락하였어요! \n" + teleNum + "이 번호로 "
                                    + userName + "님에게 전화를 하여 위치와 필요한 도움에 대해 설명해주세요! ";

                            NotificationCompat.Builder builder = new NotificationCompat.Builder(MainActivity.this, CHANNEL_ID)
                                    .setSmallIcon(R.drawable.notification_icon)
                                    .setContentTitle(title)
                                    .setStyle(new NotificationCompat.BigTextStyle().bigText(content))
                                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                                    .setAutoCancel(true);
                            NotificationManagerCompat notificationManager = NotificationManagerCompat.from(MainActivity.this);
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

    private void BottomNavigate(int id) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        Fragment currentFragment = fragmentManager.getPrimaryNavigationFragment();
        if (currentFragment != null) {
            fragmentTransaction.hide(currentFragment);
        }

        Fragment fragment;
        if (id == R.id.navigation_1) {
            fragment = fragmentPage1;
        } else if (id == R.id.navigation_2) {
            fragment = mapFragment;
        } else if (id == R.id.navigation_3) {
            fragment = popupFragment;
        } else {
            fragment = fragmentPage4;
        }

        if (fragment != null) {
            Fragment existingFragment = fragmentManager.findFragmentByTag(fragment.getClass().getSimpleName());
            if (existingFragment == null) {
                fragmentTransaction.replace(R.id.content_layout, fragment, fragment.getClass().getSimpleName());
            } else {
                fragmentTransaction.show(existingFragment);
            }

            fragmentTransaction.setPrimaryNavigationFragment(fragment);
            fragmentTransaction.setReorderingAllowed(true);
            fragmentTransaction.commit();

            if (fragment instanceof MapFragment) {
                if (currentFragment != null) {
                    fragmentManager.putFragment(new Bundle(), "currentFragment", currentFragment);
                }

                Intent intent = new Intent(this, MapActivity.class);
                startActivity(intent);
            }
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        handleIntent(intent);
    }
}