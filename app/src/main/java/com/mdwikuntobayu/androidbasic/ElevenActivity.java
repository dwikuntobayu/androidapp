package com.mdwikuntobayu.androidbasic;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Iterator;

public class ElevenActivity extends AppCompatActivity {
    Button btn_request;
    TextView lbl_http_connection;
    HttpURLConnection connection = null;
    BufferedReader reader = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eleven);

        btn_request = (Button)findViewById(R.id.btn_request);
        lbl_http_connection = (TextView)findViewById(R.id.lbl_http_connection);
        //button click will trigger http connection
        btn_request.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //localhost or 127.0.0.1 , is refer to emulator device it self
                //use 10.0.2.2, for access local server
                new ApiConnect().execute("http://10.0.2.2:3000/api/v1/auth");
            }
        });
    }

    //this method for handle http connection
    public String get_data(String url_target) {
        String line = "";
        try {
            URL url = new URL(url_target);
            connection = (HttpURLConnection) url.openConnection();
            connection.connect();
            InputStream stream = connection.getInputStream();
            reader = new BufferedReader(new InputStreamReader(stream));
            StringBuffer buffer = new StringBuffer();
            while ((line = reader.readLine()) != null) {
                buffer.append(line);
            }
            //this will return to onPostExecute when doInBackground finished
            return buffer.toString();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(connection != null) connection.disconnect();
            try {
                if(reader != null) reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    //this method for handel json parse
    public void process_json(String json_str) throws JSONException {
        try {
            lbl_http_connection.setText("");
            JSONObject api_json = new JSONObject(json_str);
            JSONArray users = api_json.getJSONArray("users");
            for(int i=0; i<users.length(); i++) {
                JSONObject user = users.getJSONObject(i);
                lbl_http_connection.append(
                    "Id = " + String.valueOf(user.getInt("id")) +
                    System.getProperty("line.separator") +
                    "Email = " + user.getString("email") +
                    System.getProperty("line.separator") +
                    "Password = " + user.getString("password") +
                    System.getProperty("line.separator") +
                    "Token Auth = " + user.getString("token_auth") +
                    System.getProperty("line.separator") +
                    "Created at = " + user.getString("created_at") +
                    System.getProperty("line.separator") +
                    "Updated at = " + user.getString("updated_at") +
                    System.getProperty("line.separator") +
                    System.getProperty("line.separator")
                );
            }
            //this technique for loop json object base on keys object
//            Iterator<String> iterator = api_json.keys();
//            while (iterator.hasNext()) {
//                String key = iterator.next();
//                Object value = api_json.get(key);
//                Sc.debug(String.valueOf(value);
//            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    //asynctask method will process http connection in background
    //http connection will not working in UI thread
    class ApiConnect extends AsyncTask<String, String, String>{

        @Override
        protected String doInBackground(String... params) {
            return get_data(params[0]);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            //getClass will check class of object
            Sc.debug(String.valueOf(s.getClass()));
            try {
                process_json(s);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

}
