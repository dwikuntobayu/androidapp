package com.mdwikuntobayu.androidbasic;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class NineActivity extends AppCompatActivity {
    TextView tv_name, tv_city, tv_province;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nine);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        tv_name = (TextView)findViewById(R.id.tv_name_nine);
        tv_city = (TextView)findViewById(R.id.tv_city_nine);
        tv_province = (TextView)findViewById(R.id.tv_province_nine);

        //first way create json object
        JSONObject student_json = new JSONObject();
        JSONObject address_json = new JSONObject();
        JSONArray score_json = new JSONArray();
        try {
            student_json.put("name", "mdwikuntobayu");
            address_json.put("city", "bandung");
            address_json.put("province", "west java");
            student_json.put("address", address_json);
            score_json.put(10);
            score_json.put(25);
            student_json.put("score", score_json);

            tv_name.setText("Name : " + student_json.getString("name"));
            JSONObject str_json_address = (JSONObject) student_json.get("address");
            tv_city.setText("City : " + str_json_address.getString("city"));
            tv_province.setText("Province : " + str_json_address.getString("province"));

        } catch (JSONException e) {
            Log.i("Info", String.valueOf(e));
        }

        //second way create json object from string
//        String student_str = "{'name': 'mdwikuntobayu', 'address': {'city':'bandung', 'province':'west java'}}";
//        try {
//            JSONObject second_json = new JSONObject(student_str);
//            Log.i("json_result", second_json.toString());
//            Log.i("json_result", second_json.getString("name"));
//            JSONObject address = (JSONObject) second_json.get("address");
//            Log.i("json_result", address.getString("city"));
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }

    }

}
