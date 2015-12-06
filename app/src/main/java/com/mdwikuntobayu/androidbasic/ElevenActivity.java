package com.mdwikuntobayu.androidbasic;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;

import com.google.gson.Gson;
import com.mdwikuntobayu.androidbasic.models.User;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

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
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ElevenActivity extends AppCompatActivity {
    Button btn_request;
    TextView lbl_http_connection;
    ListView lv_user;
    ProgressDialog pd_loading;

    HttpURLConnection connection = null;
    BufferedReader reader = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eleven);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        pd_loading = new ProgressDialog(this);
        pd_loading.setIndeterminate(true);
        pd_loading.setCancelable(false);
        pd_loading.setMessage("On Progress....");

        // Image will handle by :
        // https://github.com/nostra13/Android-Universal-Image-Loader
        // need setup permision for write external storage
        // and loaded in Gradle : build.gradle (Module: app)
        // Create default options which will be used for every
        //  displayImage(...) call if no options will be passed to this method
        DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
            .cacheInMemory(true)
            .cacheOnDisk(true)
            .build();
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getApplicationContext())
            .defaultDisplayImageOptions(defaultOptions)
            .build();
        ImageLoader.getInstance().init(config); // Do it on Application start

        btn_request = (Button)findViewById(R.id.btn_request);
        lbl_http_connection = (TextView)findViewById(R.id.lbl_http_connection);
        lv_user = (ListView)findViewById(R.id.lv_user);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.refresh) {
            new ApiConnect().execute("http://10.0.2.2:3000/api/v1/auth");
            return true;
        }

        return super.onOptionsItemSelected(item);
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

            //this object list with type user model will process in custom adapter
            List<User> user_model_list = new ArrayList<>();

            //gson use for replace manual assign data json to model user
            Gson gson = new Gson();
            for(int i=0; i<users.length(); i++) {
                JSONObject user = users.getJSONObject(i);
                User user_model = gson.fromJson(String.valueOf(user), User.class);
                // --- begin code for show data in textview ---
//                lbl_http_connection.append(
//                    "Id = " + String.valueOf(user.getInt("id")) +
//                    System.getProperty("line.separator") +
//                    "Email = " + user.getString("email") +
//                    System.getProperty("line.separator") +
//                    "Password = " + user.getString("password") +
//                    System.getProperty("line.separator") +
//                    "Token Auth = " + user.getString("token_auth") +
//                    System.getProperty("line.separator") +
//                    "Created at = " + user.getString("created_at") +
//                    System.getProperty("line.separator") +
//                    "Updated at = " + user.getString("updated_at") +
//                    System.getProperty("line.separator") +
//                    System.getProperty("line.separator")
//                );
                // --- end for show in textview ---

                // --- general usage User model & will replace with gson ---
//                User user_model = new User();
//                user_model.setId(user.getInt("id"));
//                user_model.setEmail(user.getString("email"));
//                user_model.setPassword(user.getString("password"));
//                user_model.setToken_auth(user.getString("token_auth"));

                user_model_list.add(user_model);

                //set value to listview from arrayadapter
                UserAdapter adapter = new UserAdapter(getApplicationContext(), R.layout.row, user_model_list);
                lv_user.setAdapter(adapter);
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
        protected void onPreExecute() {
            super.onPreExecute();
            pd_loading.show();
        }

        @Override
        protected String doInBackground(String... params) {
            return get_data(params[0]);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            //getClass will check class of object
            Sc.debug(String.valueOf(s.getClass()));
            pd_loading.dismiss();
            try {
                process_json(s);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    //this for handle show data to ui
    public class UserAdapter extends ArrayAdapter {
        private List<User> user_list;
        private int resource;
        private LayoutInflater inflater;

        public UserAdapter(Context context, int resource, List objects) {
            super(context, resource, objects);
            user_list = objects;
            this.resource = resource;
            inflater = (LayoutInflater)getSystemService(LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            //class ViewHolder created for handle repeated declaration of findViewById
            //this class will decrease memory consumption
            ViewHolder holder = null;
            //convertView is used for check if object item is first time created
            if(convertView == null) {
                convertView = inflater.inflate(resource, null);

                holder = new ViewHolder();
                holder.iv_user = (ImageView)convertView.findViewById(R.id.iv_user);
                holder.tv_id = (TextView)convertView.findViewById(R.id.tv_id);
                holder.tv_email = (TextView)convertView.findViewById(R.id.tv_email);
                holder.tv_password = (TextView)convertView.findViewById(R.id.tv_password);
                holder.tv_token_auth = (TextView)convertView.findViewById(R.id.tv_token_auth);
                holder.rb_user = (RatingBar)convertView.findViewById(R.id.rb_user);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            final ProgressBar pb_image = (ProgressBar)convertView.findViewById(R.id.pb_image);
            // image handle by android universal image loader
            ImageLoader.getInstance().displayImage("https://scontent.cdninstagram.com/hphotos-xaf1/t51.2885-15/e15/11326273_666716256793025_1811388597_n.jpg", holder.iv_user, new ImageLoadingListener() {
                @Override
                public void onLoadingStarted(String imageUri, View view) {
                    pb_image.setVisibility(View.VISIBLE);
                }

                @Override
                public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
                    pb_image.setVisibility(View.GONE);
                }

                @Override
                public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                    pb_image.setVisibility(View.GONE);
                }

                @Override
                public void onLoadingCancelled(String imageUri, View view) {
                    pb_image.setVisibility(View.GONE);
                }
            });

            holder.tv_id.setText("Id : " + user_list.get(position).getId());
            holder.tv_email.setText("Email : " + user_list.get(position).getEmail());
            holder.tv_password.setText("Password : " + user_list.get(position).getPassword());
            holder.tv_token_auth.setText("Token Auth : " + user_list.get(position).getToken_auth());
            holder.rb_user.setRating(3.5f);

            return convertView;
        }

        class ViewHolder {
            private ImageView iv_user;
            private TextView tv_id;
            private TextView tv_email;
            private TextView tv_password;
            private TextView tv_token_auth;
            private RatingBar rb_user;
        }
    }

}
