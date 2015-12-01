package com.mdwikuntobayu.androidbasic;

import android.app.IntentService;
import android.content.Intent;
import android.widget.Toast;

/**
 * Created by mdwikuntobayu on 07/11/15.
 */
public class TheServiceIntent extends IntentService {
    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    public TheServiceIntent() {
        super("the_service_intent");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Toast.makeText(TheServiceIntent.this, "Service Started", Toast.LENGTH_SHORT).show();
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        Toast.makeText(TheServiceIntent.this, "Service Stoped", Toast.LENGTH_SHORT).show();
        super.onDestroy();
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        synchronized (this) {
            try {
                wait(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
