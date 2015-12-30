package com.mdwikuntobayu.androidbasic;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.RelativeLayout;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

public class SixteenActivity extends AppCompatActivity {
    private RelativeLayout rl_sixteen;
    private LineChart lc_realtime;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sixteen);
        rl_sixteen = (RelativeLayout) findViewById(R.id.rl_sixteen);
        //config & customize line chart
        lc_realtime = (LineChart) findViewById(R.id.lc_realtime);
        lc_realtime.setDescription("");
        lc_realtime.setNoDataTextDescription("no data now");
        //enable highlighting
        lc_realtime.setHighlightPerDragEnabled(true);
        //enable scaling and dragging
        lc_realtime.setTouchEnabled(true);
        lc_realtime.setScaleEnabled(true);
        lc_realtime.setDrawGridBackground(false);
        //enable pinch zoom to avoid separate x & y scaling
        lc_realtime.setPinchZoom(true);
        lc_realtime.setBackgroundColor(Color.LTGRAY);

        //Setup data for linechart
        LineData lc_data = new LineData();
        lc_data.setValueTextColor(Color.WHITE);
        lc_realtime.setData(lc_data);

        //get legend object & customize
        Legend legend = lc_realtime.getLegend();
        legend.setForm(Legend.LegendForm.LINE);
        legend.setTextColor(Color.WHITE);

        XAxis x1 = lc_realtime.getXAxis();
        x1.setTextColor(Color.WHITE);
        x1.setDrawGridLines(false);
        x1.setAvoidFirstLastClipping(true);

        YAxis y1 = lc_realtime.getAxisLeft();
        y1.setTextColor(Color.WHITE);
        y1.setAxisMaxValue(180f);
        y1.setDrawGridLines(true);

        YAxis y12 = lc_realtime.getAxisRight();
        y12.setEnabled(false);

    }

    @Override
    protected void onResume() {
        super.onResume();
        //update periodically data lc_realtime
        new Thread(new Runnable() {
            @Override
            public void run() {
                //add 100 entries
                for (int i = 0; i < 100; i++) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            add_entry();
                        }
                    });
                    //use sleep for paus add entries
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    private void add_entry() {
        LineData data = lc_realtime.getData();
        if (data != null) {
            LineDataSet data_set = data.getDataSetByIndex(0);
            if (data_set == null) {
                //create data set if null
                data_set = linear_data_set();
                data.addDataSet(data_set);
            }
            //add new value
            data.addXValue("");
            data.addEntry(new Entry((float)(Math.random() * 120) + 60f, data_set.getEntryCount()), 0);
            // check if data was changed
            lc_realtime.notifyDataSetChanged();
            //limit visible entry
            lc_realtime.setVisibleXRange(6, 12);
            //scroll to the last entry
            lc_realtime.moveViewToX(data.getXValCount() - 7);

        }
    }

    //method for create set
    private LineDataSet linear_data_set() {
        LineDataSet data_set = new LineDataSet(null, "SPL Db");
        data_set.setDrawCubic(true);
        data_set.setCubicIntensity(0.2f);
        data_set.setAxisDependency(YAxis.AxisDependency.LEFT);
        data_set.setColor(ColorTemplate.getHoloBlue());
        data_set.setCircleColor(ColorTemplate.getHoloBlue());
        data_set.setLineWidth(2f);
        data_set.setCircleSize(4f);
        data_set.setFillAlpha(65);
        data_set.setFillColor(ColorTemplate.getHoloBlue());
        data_set.setHighLightColor(Color.rgb(244, 117, 177));
        data_set.setValueTextColor(Color.WHITE);
        data_set.setValueTextSize(10f);
        return data_set;
    }

}
