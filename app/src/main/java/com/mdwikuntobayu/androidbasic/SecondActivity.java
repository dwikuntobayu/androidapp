package com.mdwikuntobayu.androidbasic;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class SecondActivity extends AppCompatActivity implements View.OnClickListener{
    Button login_btn, next_img_btn, exit_second_btn, next_thrid_btn;
    int attempt_counter = 3;
    int[] images = {R.drawable.android_round, R.drawable.android_round2};
    int current_image;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
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

        login_btn = (Button)findViewById(R.id.login_btn);
        next_img_btn = (Button)findViewById(R.id.next_img_btn);
        exit_second_btn = (Button)findViewById(R.id.exit_second_btn);
        next_thrid_btn = (Button)findViewById(R.id.next_thrid_btn);

        login_btn.setOnClickListener(this);
        next_img_btn.setOnClickListener(this);
        exit_second_btn.setOnClickListener(this);
        next_thrid_btn.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login_btn:
                EditText username_txt = (EditText)findViewById(R.id.username_txt);
                EditText password_txt = (EditText)findViewById(R.id.password_txt);
                TextView attempt_counter_lbl = (TextView)findViewById(R.id.attempt_counter_lbl);
                if (username_txt.getText().toString().equals("mdwikuntobayu") &&
                        password_txt.getText().toString().equals("123qweQWE")) {
                    Toast.makeText(SecondActivity.this, "Success Login", Toast.LENGTH_SHORT).show();
                    Intent goto_main = new Intent(this, MainActivity.class);
                    goto_main.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(goto_main);

                } else {
                    Toast.makeText(SecondActivity.this, "Fails Login", Toast.LENGTH_SHORT).show();
                    attempt_counter --;
                    attempt_counter_lbl.setText(Integer.toString(attempt_counter));
                    if (attempt_counter == 0) {
                        login_btn.setEnabled(false);
                    }
                }
                break;

            case R.id.next_img_btn:
                ImageView andros_iv = (ImageView)findViewById(R.id.andros_iv);
                current_image++;
                current_image = current_image % images.length;
                andros_iv.setImageResource(images[current_image]);
                break;

            case R.id.exit_second_btn:
                Intent exit = new Intent(this, MainActivity.class);
                exit.putExtra("STATUS_EXIT", true);
                exit.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(exit);
                break;

            case R.id.next_thrid_btn:
                Intent next_thrid = new Intent("com.mdwikuntobayu.androidbasic.ThridActivity");
                startActivity(next_thrid);
                break;

            default:
                break;
        }
    }
}
