package com.mdwikuntobayu.androidbasic;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
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

import com.google.gson.Gson;
import com.mdwikuntobayu.androidbasic.models.User;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class ThirteenActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    TextView tv_shared_preference, tv_internal_storage;
    NavigationView nv_left;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thirteen);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        tv_shared_preference = (TextView)findViewById(R.id.tv_shared_preference);
        tv_internal_storage = (TextView)findViewById(R.id.tv_internal_storage);

        //this for handle base fragment
        Fragment base_fragment = new FragmentOne();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_place, base_fragment);
        transaction.addToBackStack(null);
        transaction.commit();

        //----Example Usage of Share Preferences----
        //set value from shared preferences
        //shared preferences can be access from different activity
        SharedPreferences set_shared_preference = getSharedPreferences("authentication", MODE_PRIVATE);
        SharedPreferences.Editor sp_editor = set_shared_preference.edit();
//        sp_editor.putString("token_authentication", "fd@3jfD83#dfaksdfweqoru#LEWlkj");

        //set object user to share preference
        User user = new User("dwikunto.bayu@kiranatama.com","1234%^&*","sdf*#998323kHKJH23kjh(*");
        user.setId(1);
        Gson gson = new Gson();
        String user_gson = gson.toJson(user);
        sp_editor.putString("user", user_gson);
        sp_editor.commit();

        //this for retrieve value from shared preferences
        SharedPreferences get_shared_preference = getSharedPreferences("authentication", MODE_PRIVATE);
//        tv_shared_preference.setText(get_shared_preference.getString("token_authentication", ""));

        //retrieve value from object user in share preference
        String get_set = get_shared_preference.getString("user", null);
        try {
            JSONObject jo_user = new JSONObject(get_set);
            Gson r_user = new Gson();
            User obj_user = r_user.fromJson(String.valueOf(jo_user), User.class);
            //this will check if auth token is null or not
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
                Sc.debug(String.valueOf(obj_user.getToken_auth().isEmpty()));
            }
            tv_shared_preference.setText(obj_user.getToken_auth().toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        //----End Usage of Shared Preferences-----

        nv_left = (NavigationView)findViewById(R.id.navigation);
        nv_left.setNavigationItemSelectedListener(this);

        //set value from internal storage
        //internal storage can be access from different activity
        try {
            FileOutputStream set_internal_storage = openFileOutput("authentication", Context.MODE_PRIVATE);
            set_internal_storage.write("342&jknKJ389*234lkj)Qkljkjalk".getBytes());
            set_internal_storage.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //get value from internal storage
        try {
            FileInputStream get_internal_storage = openFileInput("authentication");
//            get_internal_storage.read();
            InputStreamReader input_stream_reader = new InputStreamReader(get_internal_storage);
            BufferedReader buffered_reader = new BufferedReader(input_stream_reader);
            StringBuilder string_builder = new StringBuilder();
            String line;
            while ((line = buffered_reader.readLine()) != null) {
                string_builder.append(line);
            }
            tv_internal_storage.setText(String.valueOf(string_builder));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        Fragment fragment;
        FragmentManager fm = getSupportFragmentManager();
        //this for clean stack of fragment
        fm.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
//        for(int i = 0; i < fm.getBackStackEntryCount(); ++i) {
//            fm.popBackStack();
//        }
        FragmentTransaction ft = fm.beginTransaction();
        switch (item.getItemId()) {
            case R.id.navigation_item_1 :
                fragment = new FragmentOne();
                ft.replace(R.id.fragment_place,  new FragmentOne());
                ft.commit();
                break;
            case R.id.navigation_item_2 :
                fragment = new FragmentOne();
                ft.replace(R.id.fragment_place,  new FragmentTwo());
                ft.commit();
                break;
            case R.id.navigation_item_3 :
                fragment = new FragmentOne();
                ft.replace(R.id.fragment_place,  new ThreeFragment());
                ft.commit();
                break;
        }
        return false;
    }
}
