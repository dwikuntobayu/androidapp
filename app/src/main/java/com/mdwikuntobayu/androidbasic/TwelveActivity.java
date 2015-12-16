package com.mdwikuntobayu.androidbasic;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mdwikuntobayu.androidbasic.models.User;
import com.mdwikuntobayu.androidbasic.models.Users;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

public class TwelveActivity extends AppCompatActivity {
    TextView tv_respond, tv_result_api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_twelve);
        tv_respond = (TextView)findViewById(R.id.tv_respond);
        tv_result_api = (TextView)findViewById(R.id.tv_result_api);

        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://private-2bc04-androidapi4.apiary-mock.com")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        UserApi user_api = retrofit.create(UserApi.class);
        // This code will implement interface of UserApi
        // // implement for add data user
//        User user = new User("akasyah@mail.com", "123ASDqwe", "saf23984n8(*dsjf");
//        Call<User> call = user_api.saveUser(user);
        // // implement for get all user
        Call<Users> call = user_api.getUsers();
        // // implement for delete
//        Call<User> call = user_api.deleteUser("saf23984n8(*dsjf");
        // // implement for show a user
//        Call<User> call = user_api.getUser("saf23984n8(*dsjf");
        // // implement for update a user
//        Call<User> call = user_api.updateUser("saf23984n8(*dsjf", new User("", "12345667", ""));
        call.enqueue(new Callback<Users>() {
            @Override
            public void onResponse(Response<Users> response, Retrofit retrofit) {
                int status = response.code();
                tv_respond.setText(String.valueOf(status));
                //this will get first id data from users
                Sc.debug(String.valueOf(response.body().getUsers().get(0).getId()));
                //this extract data from retrofit with iterator
                Iterator<Users.UserItem> iterator = response.body().getUsers().iterator();
                while(iterator.hasNext()){
                    Sc.debug(String.valueOf(iterator.next().getEmail()));
                }
                //this extract data from retrofit with for() loop
                for(Users.UserItem user : response.body().getUsers()) {
                    Sc.debug(String.valueOf(user.getPassword()));
                    tv_result_api.append(
                        "Id = " + String.valueOf(user.getId()) +
                        System.getProperty("line.separator") +
                        "Email = " + user.getEmail() +
                        System.getProperty("line.separator") +
                        "Password = " + user.getPassword() +
                        System.getProperty("line.separator") +
                        "Token Auth = " + user.getToken_auth() +
                        System.getProperty("line.separator") +
                        "Created at = " + user.getCreated_at() +
                        System.getProperty("line.separator") +
                        "Updated at = " + user.getUpdated_at() +
                        System.getProperty("line.separator") +
                        System.getProperty("line.separator")
                    );
                }

            }

            @Override
            public void onFailure(Throwable t) {
                tv_respond.setText(String.valueOf(t));
            }
        });
//        call.enqueue(new Callback<User>() {
//            @Override
//            public void onResponse(Response<User> response, Retrofit retrofit) {
//                int status = response.code();
//                tv_respond.setText(String.valueOf(status));
//                tv_result_api.setText(String.valueOf(response.body()));
//            }
//
//            @Override
//            public void onFailure(Throwable t) {
//                tv_respond.setText(String.valueOf(t));
//            }
//        });
    }

}
