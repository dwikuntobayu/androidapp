package com.mdwikuntobayu.androidbasic;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.github.clans.fab.FloatingActionButton;

public class SeventeenActivity extends AppCompatActivity implements View.OnClickListener{
    FloatingActionButton fab_mi_1, fab_mi_2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seventeen);
        fab_mi_1 = (FloatingActionButton)findViewById(R.id.fab_mi_1);
        fab_mi_2 = (FloatingActionButton)findViewById(R.id.fab_mi_2);
        fab_mi_1.setOnClickListener(this);
        fab_mi_2.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fab_mi_1:
                Toast.makeText(SeventeenActivity.this, "Bycycle", Toast.LENGTH_SHORT).show();
                break;
            case R.id.fab_mi_2:
                Toast.makeText(SeventeenActivity.this, "Hazard", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
