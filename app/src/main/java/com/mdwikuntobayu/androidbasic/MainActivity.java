package com.mdwikuntobayu.androidbasic;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    Button calculate_btn, see_btn, meat_btn, rating_btn, next_second_btn,
            fourth_btn, five_btn, six_btn, seven_btn, eight_btn, nine_btn,
            ten_btn, eleven_btn, twelve_btn, thirteen_btn, fourteen_btn,
            fiveteen_btn, sixteen_btn;
    CheckBox inu, neko;
    RadioGroup meat_rb_g;
    RadioButton meat_rb;
    RatingBar star_rb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getIntent().getBooleanExtra("STATUS_EXIT", false)) {
            finish();
        }
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
//        toolbar.setLogo(R.drawable.android_round);
        getSupportActionBar().setIcon(R.drawable.android_round);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setDisplayShowHomeEnabled(true);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        Log.i("ok", "onCreate");

        // Capture our button from layout
        // below example best practice handle on click
        calculate_btn = (Button)findViewById(R.id.calculate_btn);
        see_btn = (Button)findViewById(R.id.see_btn);
        inu = (CheckBox)findViewById(R.id.inu_ck);
        neko = (CheckBox)findViewById(R.id.neko_ck);
        meat_btn = (Button)findViewById(R.id.meat_btn);
        rating_btn = (Button)findViewById(R.id.rating_btn);
        next_second_btn = (Button)findViewById(R.id.next_second_btn);
        fourth_btn = (Button)findViewById(R.id.fourth_btn);
        five_btn = (Button)findViewById(R.id.five_btn);
        six_btn = (Button)findViewById(R.id.six_btn);
        seven_btn = (Button)findViewById(R.id.seven_btn);
        eight_btn = (Button)findViewById(R.id.btn_eight);
        nine_btn = (Button)findViewById(R.id.btn_nine);
        ten_btn = (Button)findViewById(R.id.btn_ten);
        eleven_btn = (Button)findViewById(R.id.btn_eleven);
        twelve_btn = (Button)findViewById(R.id.btn_twelve);
        thirteen_btn = (Button)findViewById(R.id.btn_thirteen);
        fourteen_btn = (Button)findViewById(R.id.btn_fourteen);
        fiveteen_btn = (Button)findViewById(R.id.btn_fiveteen);
        sixteen_btn = (Button)findViewById(R.id.btn_sixteen);

        // Register the onClick listener with the implementation above
        calculate_btn.setOnClickListener(this);
        see_btn.setOnClickListener(this);
        inu.setOnClickListener(this);
        neko.setOnClickListener(this);
        meat_btn.setOnClickListener(this);
        rating_btn.setOnClickListener(this);
        next_second_btn.setOnClickListener(this);
        fourth_btn.setOnClickListener(this);
        five_btn.setOnClickListener(this);
        six_btn.setOnClickListener(this);
        seven_btn.setOnClickListener(this);
        eight_btn.setOnClickListener(this);
        nine_btn.setOnClickListener(this);
        ten_btn.setOnClickListener(this);
        eleven_btn.setOnClickListener(this);
        twelve_btn.setOnClickListener(this);
        thirteen_btn.setOnClickListener(this);
        fourteen_btn.setOnClickListener(this);
        fiveteen_btn.setOnClickListener(this);
        sixteen_btn.setOnClickListener(this);

        check_star();
    }

    // this action onclick for handle onclicklistener
    @Override
    public void onClick(View v) {
        // do something when the button is clicked
        // Yes we will handle click here but which button clicked??? We don't know

        // So we will make
        switch (v.getId() /*to get clicked view id**/) {
            case R.id.calculate_btn:
                EditText first_txt = (EditText)findViewById(R.id.first_txt);
                EditText second_txt = (EditText)findViewById(R.id.second_txt);
                try {
                    Integer total = Integer.parseInt(first_txt.getText().toString()) + Integer.parseInt(second_txt.getText().toString());
                    TextView result_lbl = (TextView)findViewById(R.id.result_lbl);
                    result_lbl.setText(Integer.toString(total));
                    AlertDialog.Builder alert = new AlertDialog.Builder(this);
                    alert.setCancelable(false)
                            .setTitle("Result Calculation")
                            .setMessage(Integer.toString(total) + " \n Do you wana exit ?")
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    finish();
                                }
                            })
                            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            });
                    alert.show();
                } catch (Exception e) {
                    Toast.makeText(MainActivity.this, "Please Fill the textbox first", Toast.LENGTH_SHORT).show();
                    first_txt.setHint("Fill me first");
                }
                break;

            case R.id.see_btn:
                inu = (CheckBox)findViewById(R.id.inu_ck);
                neko = (CheckBox)findViewById(R.id.neko_ck);
                StringBuffer list_animal = new StringBuffer();
                list_animal.append("inu : ").append(inu.isChecked()).append("\n");
                list_animal.append("neko : ").append(neko.isChecked());
                Toast.makeText(MainActivity.this, list_animal.toString(), Toast.LENGTH_LONG).show();
                break;

            case R.id.inu_ck:
                if (inu.isChecked())
                    Toast.makeText(MainActivity.this, "Inu is checked ", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(MainActivity.this, "Inu unchecked ", Toast.LENGTH_SHORT).show();
                break;

            case R.id.neko_ck:
                if (neko.isChecked())
                    Toast.makeText(MainActivity.this, "Neko is checked", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(MainActivity.this, "Neko unchecked", Toast.LENGTH_SHORT).show();
                break;

            case R.id.meat_btn:
                meat_rb_g = (RadioGroup)findViewById(R.id.meat_rbg);
                int check_rb = meat_rb_g.getCheckedRadioButtonId();
                meat_rb = (RadioButton)findViewById(check_rb);
                try {
                    Toast.makeText(MainActivity.this, meat_rb.getText().toString(), Toast.LENGTH_SHORT).show();
                } catch (Exception e){
                    Toast.makeText(MainActivity.this, "Please choose the meat first", Toast.LENGTH_SHORT).show();
                }

                break;

            case R.id.rating_btn:
                star_rb = (RatingBar)findViewById(R.id.start_rb);
                Toast.makeText(MainActivity.this, String.valueOf(star_rb.getRating()), Toast.LENGTH_SHORT).show();
                break;

            case R.id.next_second_btn:
                Intent next_second = new Intent("com.mdwikuntobayu.androidbasic.SecondActivity");
                startActivity(next_second);
                break;

            case R.id.fourth_btn:
                Intent next_fourth = new Intent("com.mdwikuntobayu.androidbasic.FourthActivity");
                startActivity(next_fourth);
                break;

            case R.id.five_btn:
                Intent next_five = new Intent("com.mdwikuntobayu.androidbasic.FiveActivity");
                startActivity(next_five);
                break;

            case R.id.six_btn:
                Intent next_six = new Intent("com.mdwikuntobayu.androidbasic.SixActivity");
                startActivity(next_six);
                break;

            case R.id.seven_btn:
//                Intent next_seven = new Intent(this, SevenActivity.class);
                Intent next_seven = new Intent("com.mdwikuntobayu.androidbasic.SevenActivity");
                startActivity(next_seven);
                break;

            case R.id.btn_eight:
                Intent next_eight = new Intent("com.mdwikuntobayu.androidbasic.EightActivity");
                startActivity(next_eight);
                break;

            case R.id.btn_nine:
                Intent next_nine = new Intent("com.mdwikuntobayu.androidbasic.NineActivity");
                startActivity(next_nine);
                break;

            case R.id.btn_ten:
                Intent next_ten = new Intent("com.mdwikuntobayu.androidbasic.TenActivity");
                startActivity(next_ten);
                break;

            case R.id.btn_eleven:
                Intent next_eleven = new Intent("com.mdwikuntobayu.androidbasic.ElevenActivity");
                startActivity(next_eleven);
                break;

            case R.id.btn_twelve:
                Intent next_twelve = new Intent("com.mdwikuntobayu.androidbasic.TwelveActivity");
                startActivity(next_twelve);
                break;

            case R.id.btn_thirteen:
                Intent next_thirteen = new Intent("com.mdwikuntobayu.androidbasic.ThirteenActivity");
                startActivity(next_thirteen);
                break;

            case R.id.btn_fourteen:
                Intent next_fourteen = new Intent("com.mdwikuntobayu.androidbasic.FourteenActivity");
                startActivity(next_fourteen);
                break;

            case R.id.btn_fiveteen:
                Intent next_fiveteen = new Intent("com.mdwikuntobayu.androidbasic.FiveteenActivity");
                startActivity(next_fiveteen);
                break;

            case R.id.btn_sixteen:
                Intent next_sixteen = new Intent("com.mdwikuntobayu.androidbasic.SixteenActivity");
                startActivity(next_sixteen);
                break;

            default:
                break;
        }
    }

    public void check_star() {
        star_rb = (RatingBar)findViewById(R.id.start_rb);
        star_rb.setOnRatingBarChangeListener(
                new RatingBar.OnRatingBarChangeListener() {
                    @Override
                    public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                        Toast.makeText(MainActivity.this, String.valueOf(rating), Toast.LENGTH_SHORT).show();
                    }
                }
        );
    }


    public void calculate() {
        Toast.makeText(MainActivity.this, "Ok Calculate", Toast.LENGTH_LONG).show();
    }
}
