package com.mdwikuntobayu.androidbasic;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

public class ThridActivity extends AppCompatActivity {
    ListView name_lv;
    String[] NAMES = new String[] {"Mase", "Dae", "Ung", "Alse", "Akabon", "Inoe", "Polej"};
    SeekBar try_sb;
    TextView try_sb_txt;
    Button go_url_btn;
    EditText go_url_txt;
    WebView go_url_wv;
    ExpandableListView elv_obj;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thrid);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

//        name_lv = (ListView)findViewById(R.id.name_lv);
        populate_lv();
        get_val_seek_bar();
        browse_web();
    }

    public void populate_lv () {
        name_lv = (ListView)findViewById(R.id.name_lv);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.name_list, NAMES);
        name_lv.setAdapter(adapter);
        name_lv.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        String value = (String) name_lv.getItemAtPosition(position);
                        Toast.makeText(ThridActivity.this, "Position : " + position + " Value : " + value, Toast.LENGTH_LONG).show();
                    }
                }
        );
        elv_obj = (ExpandableListView)findViewById(R.id.expandableListView);
    }

    public void get_val_seek_bar() {
        try_sb = (SeekBar)findViewById(R.id.try_sb);
        try_sb_txt = (TextView)findViewById(R.id.try_sb_txt);
        try_sb_txt.setText("Value : " + try_sb.getProgress() + " / " + try_sb.getMax());
        try_sb.setOnSeekBarChangeListener(
                new SeekBar.OnSeekBarChangeListener() {
                    int progress_value;

                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        progress_value = progress;
                        try_sb_txt.setText("Value : " + progress + " / " + try_sb.getMax());
                        Toast.makeText(ThridActivity.this, "SeekBar in progress", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {
                        Toast.makeText(ThridActivity.this, "SeekBar in StartTracking", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {
                        try_sb_txt.setText("Value : " + progress_value + " / " + try_sb.getMax());
                        Toast.makeText(ThridActivity.this, "SeekBar in StopTracking", Toast.LENGTH_SHORT).show();
                    }
                }
        );
    }

    public void browse_web() {
        go_url_btn = (Button)findViewById(R.id.url_go_btn);
        go_url_txt = (EditText)findViewById(R.id.url_go_txt);
        go_url_wv = (WebView)findViewById(R.id.go_url_wv);
        go_url_btn.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String url = go_url_txt.getText().toString();
                        go_url_wv.getSettings().setLoadsImagesAutomatically(true);
                        go_url_wv.getSettings().setJavaScriptEnabled(true);
                        go_url_wv.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
                        go_url_wv.loadUrl(url);
                    }
                }
        );

    }
}
