package com.mdwikuntobayu.androidbasic;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class ThirteenActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    TextView tv_shared_preference;
    NavigationView nv_left;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thirteen);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        tv_shared_preference = (TextView)findViewById(R.id.tv_shared_preference);

        //set value from shared preferences
        //shared preferences can be access from different activity
        SharedPreferences set_shared_preference = getSharedPreferences("authentication", MODE_PRIVATE);
        SharedPreferences.Editor sp_editor = set_shared_preference.edit();
        sp_editor.putString("token_authentication", "fd@3jfD83#dfaksdfweqoru#LEWlkj");
        sp_editor.commit();

        //this for retrieve value from shared preferences
        SharedPreferences get_shared_preference = getSharedPreferences("authentication", MODE_PRIVATE);
        tv_shared_preference.setText(get_shared_preference.getString("token_authentication", ""));

        nv_left = (NavigationView)findViewById(R.id.navigation);
        nv_left.setNavigationItemSelectedListener(this);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        Fragment fragment;
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        switch (item.getItemId()) {
            case R.id.navigation_item_1 :
                Toast.makeText(ThirteenActivity.this, String.valueOf(item.getTitle()), Toast.LENGTH_SHORT).show();
                fragment = new FragmentOne();
                ft.replace(R.id.fragment_place,  new FragmentOne());
                ft.commit();
                break;
            case R.id.navigation_item_2 :
                Toast.makeText(ThirteenActivity.this, String.valueOf(item.getTitle()), Toast.LENGTH_SHORT).show();
                fragment = new FragmentOne();
                ft.replace(R.id.fragment_place,  new FragmentTwo());
                ft.commit();
                break;
        }
        return false;
    }
}
