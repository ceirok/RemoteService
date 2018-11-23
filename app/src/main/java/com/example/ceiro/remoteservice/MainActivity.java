package com.example.ceiro.remoteservice;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Color;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    public Button btnBg, btnMsg;
    public RemoteService remoteService;
    Random rnd = new Random();
    public boolean connected = false;
    View activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnBg = findViewById(R.id.btnBg);
        btnMsg = findViewById(R.id.btnMsg);
        activity = findViewById(R.id.activityBg);

        Intent mIntent = new Intent(this, RemoteService.class);
        bindService(mIntent, mConnection, BIND_AUTO_CREATE);
        connected = true;
    }

    public void requestBg(View view) {
        int color = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
        activity.setBackgroundColor(color);
    }

    public void requestDateTime(View view) {
        remoteService.getDateMsg();
    }

    ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceDisconnected(ComponentName name) {
            Toast.makeText(MainActivity.this, "Service is disconnected", Toast.LENGTH_SHORT).show();
            connected = false;
            remoteService = null;
        }

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Toast.makeText(MainActivity.this, "Service is connected", Toast.LENGTH_SHORT).show();
            connected = true;
            RemoteService.RemoteBinder mLocalBinder = (RemoteService.RemoteBinder)service;
            remoteService = mLocalBinder.getServerInstance();
        }
    };
}
