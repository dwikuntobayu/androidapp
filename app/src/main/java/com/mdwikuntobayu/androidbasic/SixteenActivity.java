package com.mdwikuntobayu.androidbasic;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

public class SixteenActivity extends AppCompatActivity {
    private LineChart lc_realtime;
    private PieChart pc_realtime;
    private float[] y_data = {44, 31, 9, 7, 23, 19, 55};
    private String[] x_data = {"yumuhu", "hundu", "puncer", "furd", "siziku", "nw", "parcel"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sixteen);
        //config & customize line chart
        lc_realtime = (LineChart) findViewById(R.id.lc_realtime);
        layout_lc_realtime();
        pc_realtime = (PieChart) findViewById(R.id.pc_realtime);
        layout_pc_realtime();

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
                            add_entry_lc();
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

    private void layout_pc_realtime() {
        pc_realtime.setDescription("Card Statistic");
        pc_realtime.setUsePercentValues(true);
        //enable hole and configure
        pc_realtime.setDrawHoleEnabled(true);
        pc_realtime.setHoleColorTransparent(true);
        pc_realtime.setHoleRadius(9);
        pc_realtime.setTransparentCircleRadius(10);
        //enable rotation
        pc_realtime.setRotationEnabled(true);
        pc_realtime.setRotationAngle(0);
        //add listener when rotate
        pc_realtime.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, int dataSetIndex, Highlight h) {
                if (e.equals(null)) return;
                Toast.makeText(SixteenActivity.this, x_data[e.getXIndex()] + " = " + e.getVal() + "%", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected() {

            }
        });
        add_entry_pc();
        //custom legend
        Legend legend = pc_realtime.getLegend();
        legend.setPosition(Legend.LegendPosition.RIGHT_OF_CHART);
        legend.setXEntrySpace(9);
        legend.setYEntrySpace(7);
    }

    private void add_entry_pc() {
        ArrayList<Entry> y_value = new ArrayList<Entry>();
        for (int i = 0; i < y_data.length; i++) {
            y_value.add(new Entry(y_data[i], i));
        }
        ArrayList<String> x_value = new ArrayList<String>();
        for (int i = 0; i < x_data.length; i++) {
            x_value.add(x_data[i]);
        }
        //create pie data
        PieDataSet data_set = new PieDataSet(y_value, "Market Share");
        data_set.setSliceSpace(3);
        data_set.setSelectionShift(5);
        //add many colors
        ArrayList<Integer> colors = new ArrayList<Integer>();
        for (int c : ColorTemplate.VORDIPLOM_COLORS) {
            colors.add(c);
        }
        for (int c : ColorTemplate.JOYFUL_COLORS) {
            colors.add(c);
        }
        for (int c : ColorTemplate.COLORFUL_COLORS) {
            colors.add(c);
        }
        for (int c : ColorTemplate.LIBERTY_COLORS) {
            colors.add(c);
        }
        for (int c : ColorTemplate.PASTEL_COLORS) {
            colors.add(c);
        }
        colors.add(ColorTemplate.getHoloBlue());
        data_set.setColors(colors);

        //instantiate pie data object
        PieData data = new PieData(x_value, data_set);
        data.setValueFormatter(new PercentFormatter());
        data.setValueTextSize(11f);
        data.setValueTextColor(Color.GRAY);
        pc_realtime.setData(data);
        pc_realtime.highlightValue(null);
        pc_realtime.invalidate();
    }

    private void layout_lc_realtime() {
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

    private void add_entry_lc() {
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
