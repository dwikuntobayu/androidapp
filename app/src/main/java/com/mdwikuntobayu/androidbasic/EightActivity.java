package com.mdwikuntobayu.androidbasic;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

public class EightActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eight);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ViewPager view_pager = (ViewPager)findViewById(R.id.view_pager_container);
        view_pager.setAdapter(new InstanceFragmentPA(getSupportFragmentManager()));
    }


}
