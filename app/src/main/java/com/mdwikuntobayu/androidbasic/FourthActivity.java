package com.mdwikuntobayu.androidbasic;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import static android.view.GestureDetector.OnDoubleTapListener;
import static android.view.GestureDetector.OnGestureListener;

public class FourthActivity extends AppCompatActivity implements OnGestureListener, OnDoubleTapListener {
    private static TextView gesture_txt  ;
    private GestureDetectorCompat gesture_detect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fourth);
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

        gesture_txt = (TextView)findViewById(R.id.gesture_txt);
        gesture_detect = new GestureDetectorCompat(this, this);
        gesture_detect.setOnDoubleTapListener(this);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        gesture_detect.onTouchEvent(event);
        return super.onTouchEvent(event);
    }

    @Override
    public boolean onSingleTapConfirmed(MotionEvent e) {
        gesture_txt.setText("onSingleTapConfirmed " + e.toString());
        return false;
    }

    @Override
    public boolean onDoubleTap(MotionEvent e) {
        gesture_txt.setText("onDoubleTap " + e.toString());
        return false;
    }

    @Override
    public boolean onDoubleTapEvent(MotionEvent e) {
        gesture_txt.setText("onDoubleTapEvent " + e.toString());
        return false;
    }

    @Override
    public boolean onDown(MotionEvent e) {
        gesture_txt.setText("onDown " + e.toString());
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {
        gesture_txt.setText("onShowPress " + e.toString());
    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        gesture_txt.setText("onSingleTapUp " + e.toString());
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        gesture_txt.setText("onScroll " + e1.toString() + " " + e2.toString());
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {
        gesture_txt.setText("onLongPress " + e.toString());
    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        gesture_txt.setText("onFling " + e1.toString() + " " + e2.toString());
        return false;
    }
}
