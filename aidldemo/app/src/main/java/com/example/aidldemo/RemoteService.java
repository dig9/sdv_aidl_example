package com.example.aidldemo;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

public class RemoteService extends Service {
    private final String TAG = "RemoteService";
    @Override
    public IBinder onBind(Intent intent) {
        Log.w(TAG, "Client try to connect to jichoi");
        return new IRemoteService.Stub() {
            @Override
            public int add(int a, int b) throws RemoteException {
                return a + b;
            }

        };
    }
}
