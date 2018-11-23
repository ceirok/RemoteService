package com.example.ceiro.remoteservice;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

public class RemoteService extends Service {
    String dateMsg;
    IBinder mBinder = new RemoteBinder();
    Random rnd = new Random();

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    public class RemoteBinder extends Binder {
        public RemoteService getServerInstance() {
            return RemoteService.this;
        }
    }

    public String getDateMsg() {
        this.dateMsg = new SimpleDateFormat("dd.MM.yyyy - HH.mm", Locale.getDefault()).format(new Date());
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                delayedMsg(dateMsg);
            }
        }, 5000);
        return this.dateMsg;
    }

    private void delayedMsg(String dateMsg) {
        Toast.makeText(this, dateMsg, Toast.LENGTH_SHORT).show();
    }
}
