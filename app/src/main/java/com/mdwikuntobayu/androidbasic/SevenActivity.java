package com.mdwikuntobayu.androidbasic;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class SevenActivity extends AppCompatActivity implements View.OnClickListener {
    Button sn_btn, start_service_btn, stop_service_btn, random_btn, read_btn, write_btn;
    int counter = 0;
    TheService the_service;
    boolean is_bound = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seven);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setIcon(R.drawable.android_round);
        //this will show arrow button in toolbar
        //the action handle by onOptionsItemSelected method
        //or can manually create meta-data in androidmanifest
        //e.g : <meta-data android:name="android.support.PARENT_ACTIVITY" android:value=".MainActivity"></meta-data>
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Page Seven");

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        sn_btn = (Button)findViewById(R.id.sn_btn);

        sn_btn.setOnClickListener(
            new View.OnClickListener() {
                @TargetApi(Build.VERSION_CODES.HONEYCOMB)
                @Override
                public void onClick(View v) {
                    Intent intent_sn = new Intent();
                    /*
                        Pending intent is toke that you give to another application
                        (e.g., notification manager, alarm manager, or other 3rd party application)
                        which allow this other application to use the permission of your application
                        to execute predefined piece of code.
                     */
                    PendingIntent pi_sn = PendingIntent.getActivities(SevenActivity.this, 0, new Intent[]{intent_sn}, 0);
                    Notification notif = null;
                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN) {
                        notif = new Notification.Builder(SevenActivity.this)
                                .setTicker("TickerTitle")
                                .setContentTitle("Content Title")
                                .setContentText("Content Text lorem ipsum lorem ipsum lorem ipsum lorem ipsum")
                                .setSmallIcon(R.drawable.android_round)
                                .addAction(R.drawable.android_round2, "Action 1", pi_sn)
                                .setContentIntent(pi_sn).getNotification();
                    } else {
                        notif = new Notification.Builder(SevenActivity.this)
                                .setTicker("TickerTitle")
                                .setContentTitle("Content Title")
                                .setContentText("Content Text lorem ipsum lorem ipsum lorem ipsum lorem ipsum")
                                .setSmallIcon(R.drawable.android_round)
                                .setContentIntent(pi_sn).getNotification();
                    }

                    notif.flags = Notification.FLAG_AUTO_CANCEL;
                    NotificationManager nm = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
                    nm.notify(0, notif);
                    //bellow code will cause multiple notification
//                    nm.notify(counter, notif);
//                    counter++;
                }
            }
        );

        start_service_btn = (Button)findViewById(R.id.start_service_btn);
        stop_service_btn = (Button)findViewById(R.id.stop_service_btn);
        random_btn = (Button)findViewById(R.id.random_btn);
        read_btn = (Button)findViewById(R.id.read_btn);
        write_btn = (Button)findViewById(R.id.write_btn);

        start_service_btn.setOnClickListener(this);
        stop_service_btn.setOnClickListener(this);
        random_btn.setOnClickListener(this);
        read_btn.setOnClickListener(this);
        write_btn.setOnClickListener(this);

        Intent intent = new Intent(this, TheService.class);
        bindService(intent, service_connection, Context.BIND_AUTO_CREATE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//        switch(item.getItemId()) {
//            case android.R.id.home:
//                onBackPressed();
////                return true;
//                break;
//            default:
//                break;
//        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.start_service_btn:
                //service will hadle by new class that declare
                //oncreate, onstartcommand, ondestroy
                //onstartcommand : will start the service process
                Intent intent_service_start = new Intent(this, TheService.class);
                startService(intent_service_start);
                break;

            case R.id.stop_service_btn:
                Intent intent_stop_service = new Intent(this, TheService.class);
                stopService(intent_stop_service);
                break;

            case R.id.random_btn:
                TextView random_txt = (TextView)findViewById(R.id.random_txt);
                random_txt.setText(Integer.toString(the_service.get_random()));
                break;

            case R.id.read_btn:
                TextView output_lbl = (TextView)findViewById(R.id.output_lbl);
                try {
                    FileInputStream file_input_stream = openFileInput("result.txt");
                    InputStreamReader input_stream = new InputStreamReader(file_input_stream);
                    BufferedReader buffer_reader = new BufferedReader(input_stream);
                    StringBuilder string_builder = new StringBuilder();
                    String lines;
                    while((lines=buffer_reader.readLine()) != null) {
                        string_builder.append(lines + "\n");
                    }
                    output_lbl.setText(string_builder.toString());
                    //this for check path of file result.txt
                    File path=new File(getFilesDir()+"result.txt");
                    Toast.makeText(SevenActivity.this, path.getPath(), Toast.LENGTH_SHORT).show();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;

            case R.id.write_btn:
                EditText input_txt = (EditText)findViewById(R.id.input_txt);
                String string_input = input_txt.getText().toString();
                try {
                    FileOutputStream file_stream = openFileOutput("result.txt", MODE_PRIVATE);
                    file_stream.write(string_input.getBytes());
                    file_stream.close();
                    Toast.makeText(SevenActivity.this, "Text Saved", Toast.LENGTH_SHORT).show();
                    input_txt.setText("");
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;

            default:
                break;
        }
    }

    //Interface for monitoring the state of an application service
    //that will process IBinder from TheService
    private ServiceConnection service_connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            TheService.LocalBinder binder = (TheService.LocalBinder) service;
            the_service = binder.get_serivice();
            is_bound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            is_bound = false;
        }
    };
}