package com.mdwikuntobayu.androidbasic;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

//import android.app.Fragment;
//import android.app.FragmentManager;
//import android.app.FragmentTransaction;


public class FiveActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_five);
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
    }

    public void change_fragment(View view) {
        Fragment fragment;
        if(view == findViewById(R.id.fragment_one_btn)) {
            fragment = new FragmentOne();
            android.support.v4.app.FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            //this for send value to fragment
            //handle via Bundle
            Bundle bundle = new Bundle();
            bundle.putString("title_one", "Me from Activity class");
            fragment.setArguments(bundle);
            //end bundle
            ft.replace(R.id.fragment_place, fragment);
            ft.commit();
        }
        if(view == findViewById(R.id.fragment_two_btn)) {
            fragment = new FragmentTwo();
            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            ft.replace(R.id.fragment_place, fragment);
            ft.commit();
        }
    }

}
