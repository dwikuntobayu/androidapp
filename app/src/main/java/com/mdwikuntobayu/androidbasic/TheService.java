package com.mdwikuntobayu.androidbasic;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.widget.Toast;

import java.util.Random;

/**
 * Created by mdwikuntobayu on 07/11/15.
 * need declare in AndroidManifest.xml
 * <service android:name=".TheService" android:exported="false"></service>
 */
public class TheService extends Service {
    //this i_binder variable will use for return onBinde method
    private final IBinder i_binder = new LocalBinder();
    private final Random m_generator = new Random();

    final class TheThread implements Runnable {
        int service_id;
        //this constructor will use for get running service by startId
        TheThread(int s_id) {
            this.service_id = s_id;
        }

        @Override
        public void run() {
            synchronized (this) {
                try {
                    //this will pause activity aroud 10 second
                    //and if interupted the system will asking for close
                    //for handle that we need thread for process another action
                    wait(10000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                //after 10 second the service backgroud will destroy
                stopSelf(this.service_id);
            }
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Toast.makeText(TheService.this, "Service Started", Toast.LENGTH_SHORT).show();
        //this tread will handle service background
        Thread thread = new Thread(new TheThread(startId));
        thread.start();
        //start_sticky : will recreate service if service fails start
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        Toast.makeText(TheService.this, "Service Destroy", Toast.LENGTH_SHORT).show();
    }

    //this will use by serviceconnection when service connected
    public class LocalBinder extends Binder {
        TheService get_serivice() {
            return TheService.this;
        }
    }

    //binder need setup method that will return to activity
    //setup serviceconnection in activity
    //create intent for the service in method onCreate,
    //then bindService for intent
    //e.g : bindService(intent, service_connection, Context.BIND_AUTO_CREATE);
    //The Bound service allow components to bind service, request, or inter-process communication
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return i_binder;
    }

    public int get_random() {
        return m_generator.nextInt(200);
    }
}
