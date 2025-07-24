package com.example.aidlclient;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.aidldemo.IRemoteService;

import java.util.Locale;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private final String TAG = "MainActivity";
    private IRemoteService remoteService;
    private boolean isBound = false;

    TextView tvInput1, tvInput2, tvResult;

    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.i(TAG, "onServiceConnected to jichoi");
            remoteService = IRemoteService.Stub.asInterface(service);
            isBound = true;
            try {
                int a, b;
                Random random = new Random();
                a = random.nextInt(100);
                b = random.nextInt(100);
                tvInput1.setText(String.valueOf(a));
                tvInput2.setText(String.valueOf(b));
                int result = remoteService.add(a, b);
                tvResult.setText(String.valueOf(result));
                Log.i("AIDLClient", "Result from service: " + result);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.i(TAG, "onServiceDisConnected");
            remoteService = null;
            isBound = false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvInput1 = findViewById(R.id.tvInput1);
        tvInput2 = findViewById(R.id.tvInput2);
        tvResult = findViewById(R.id.tvResult);

        Intent intent = new Intent();
        intent.setClassName("com.example.aidldemo", "com.example.aidldemo.RemoteService");
        boolean bindOk = bindService(intent, connection, Context.BIND_AUTO_CREATE);
        Log.i(TAG, "bindOk = " + bindOk);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (isBound) {
            unbindService(connection);
            isBound = false;
        }
    }
}
