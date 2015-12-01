package com.mdwikuntobayu.androidbasic;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;

import java.util.ArrayList;

public class TenActivity extends AppCompatActivity {
    ListView main_list;
    private String[] users = {
            "name","address","age","city","state",
            "name","address","age","city","state",
            "name","address","age","city","state",
            "name","address","age","city","state",
            "name","address","age","city","state",
            "name","address","age","city","state",
            "name","address","age","city","state",
            "name","address","age","city","state",
            "name","address","age","city","state",
            "name","address","age","city","state",
            "name","address","age","city","state",
            "name","address","age","city","state",
            "name","address","age","city","state",
            "name","address","age","city","state",
            "name","address","age","city","state",
            "name","address","age","city","state",
            "name","address","age","city","state"
    };
    ProgressBar progress_bar;
    AddArrayToListView arr_to_list_view;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ten);
        //add progress bar
        progress_bar = (ProgressBar)findViewById(R.id.progressBar);
        progress_bar.setVisibility(View.VISIBLE);
        //setup adapter for populate data to listview
        main_list = (ListView)findViewById(R.id.listView);
        main_list.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, new ArrayList<String>()));
        //process adapter with asynctask
        arr_to_list_view = new AddArrayToListView();
        arr_to_list_view.execute();
        //this will check asynctack status (but not automaticly execute when asyncfinish
        //must manual check like with on click button
        //PENDING, RUNNING, FINISHED
        if(arr_to_list_view.getStatus() == AsyncTask.Status.FINISHED){
            Sc.alert(this, "all done");
        }
    }

    @Override
    public void onBackPressed() {
        arr_to_list_view.cancel(true);
        super.onBackPressed();
    }

    class AddArrayToListView extends AsyncTask<Void, String, Void> {
        private ArrayAdapter<String> adapter;
        private int counter = 0;
        //overlay use for create unseen layer so user cannot click display during loading list
        Dialog unseen_dialog = new Dialog(TenActivity.this, android.R.style.Theme_Panel);
        //handle loading progress with dialog
        ProgressDialog progress_dialog = new ProgressDialog(TenActivity.this);

        @Override
        protected void onPreExecute() {
            adapter = (ArrayAdapter<String>) main_list.getAdapter();
            unseen_dialog.setCancelable(false);
            unseen_dialog.show();
            //this for init progress dialog
//            progress_dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            progress_dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progress_dialog.setTitle("On Progress ....");
            progress_dialog.setCancelable(false);
            progress_dialog.setProgress(0);
            //this will handle cacle asynctack when click cancle button
            progress_dialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancel Process", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    //this will cancle current asynctask
//                    cancel(true);
                    arr_to_list_view.cancel(true);
                    //remove top progressbar
                    progress_bar.setVisibility(View.INVISIBLE);
                    //remove unseen layer
                    unseen_dialog.dismiss();
                    //remove progress dialog
                    progress_dialog.dismiss();
                    dialog.dismiss();
                }
            });
            progress_dialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {
            for(String item: users) {
                publishProgress(item);
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if(isCancelled()) {
                    arr_to_list_view.cancel(true);
                }
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(String... values) {
            adapter.add(values[0]);
            counter ++;
            Integer current_status = (int) ((counter / (float) users.length) * 100);
            progress_bar.setProgress(current_status);
            //set progress only working for horizontal loading
            progress_dialog.setProgress(current_status);
            //setmessage will not working when using horizontal loading
            progress_dialog.setMessage(String.valueOf(current_status)+ "%");
            Sc.debug(String.valueOf(current_status));
        }

        @Override
        protected void onPostExecute(Void result) {
            //hide top progress bar
            progress_bar.setVisibility(View.INVISIBLE);
            //remove unseen layer
            unseen_dialog.dismiss();
            //remove progress dialog
            progress_dialog.dismiss();
            Sc.alert(TenActivity.this, "Users array success add to list view");
        }

    }

}
