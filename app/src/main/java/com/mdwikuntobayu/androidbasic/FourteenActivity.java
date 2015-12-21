package com.mdwikuntobayu.androidbasic;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FourteenActivity extends AppCompatActivity {

    GridView gl_picasso;
    List<String> image_urls = new ArrayList<>(
            Arrays.asList(
                    "http://square.github.io/picasso/static/sample.png",
                    "http://square.github.io/picasso/static/debug.png",
                    "http://thesource.com/wp-content/uploads/2015/02/Pablo_Picasso1.jpg",
                    "https://upload.wikimedia.org/wikipedia/en/2/23/Pablo_Picasso,_1901-02,_Femme_au_caf%C3%A9_%28Absinthe_Drinker%29,_oil_on_canvas,_73_x_54_cm,_Hermitage_Museum,_Saint_Petersburg,_Russia.jpg",
                    "https://upload.wikimedia.org/wikipedia/en/7/74/PicassoGuernica.jpg",
                    "http://i01.i.aliimg.com/wsphoto/v0/810502873/Diy-digital-oil-painting-diy-30-40-font-b-picasso-b-font-abstract-music-painting-by.jpg"
            )
    );
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fourteen);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        gl_picasso = (GridView)findViewById(R.id.lv_picasso);
        //way "1", this code for access PicassoAdapter (uncomment bellow code if you use this way)
//        gl_picasso.setAdapter(new PicassoAdapter(FourteenActivity.this, image_urls));
        //way "2", this code do same action with above code
        PicassoAdapter adapter = new PicassoAdapter(getApplicationContext(), R.layout.picasso_img, image_urls);
        gl_picasso.setAdapter(adapter);
    }

    public class PicassoAdapter extends ArrayAdapter {
        private LayoutInflater inflater;
        private int resource;
        private String url;
        private Context context;
        private List<String> image_urls;
        private ImageView iv_picasso;

        //this code for way "1" (uncomment bellow code if you use this way)
//        public PicassoAdapter(FourteenActivity fourteenActivity, List<String> image_urls) {
//            super(fourteenActivity, R.layout.picasso_img, image_urls);
//            this.context = fourteenActivity;
//            this.image_urls = image_urls;
//            inflater = LayoutInflater.from(context);
//        }

        //this code for way "2"
        public PicassoAdapter(Context context, int resource, List<String> image_urls) {
            super(context, resource, image_urls);
            this.resource = resource;
            this.context = context;
            inflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            inflater = LayoutInflater.from(context);
            this.image_urls = image_urls;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            ViewHolder holder;
            if(convertView == null) {
                convertView = inflater.inflate(R.layout.picasso_img, parent, false);
                holder = new ViewHolder();
                holder.iv_picasso = (ImageView)convertView.findViewById(R.id.iv_picasso);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder)convertView.getTag();
            }

            final ProgressBar pb_picasso = (ProgressBar)convertView.findViewById(R.id.pb_picasso);
            //this use Picasso services for process images
            //1. picasso have long timeout request that will make sure image loaded all
            //2. progressbar will disappear when finish load image (but sometime not all progressbar is clear)
//            Picasso.with(context)
//                    .load(image_urls.get(position))
//                    .placeholder(R.drawable.ic_action_name)
//                    .error(R.drawable.ic_action_name2)
//                    .fit()
//                    .into(holder.iv_picasso, new Callback() {
//                        @Override
//                        public void onSuccess() {
//                            pb_picasso.setVisibility(View.GONE);
//                        }
//
//                        @Override
//                        public void onError() {
//                            pb_picasso.setVisibility(View.GONE);
//                        }
//                    });

            //this use Glide services for process images
            //1. Glide have short timeout request and need combine with okHttp for increate timeout
            //https://github.com/bumptech/glide/issues/513
            //2. progressbar will disappear when finish load image
            Glide.with(context)
                    .load(image_urls.get(position))
                    .asBitmap()
                    .placeholder(R.drawable.dashinfinity)
                    .error(R.drawable.ic_action_name2)
                    .listener(new RequestListener<String, Bitmap>() {
                        @Override
                        public boolean onException(Exception e, String model, Target<Bitmap> target, boolean isFirstResource) {
                            pb_picasso.setVisibility(View.GONE);
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Bitmap resource, String model, Target<Bitmap> target, boolean isFromMemoryCache, boolean isFirstResource) {
                            pb_picasso.setVisibility(View.GONE);
                            return false;
                        }
                    })
                    .into(holder.iv_picasso);

            return convertView;
        }

        class ViewHolder {
            private ImageView iv_picasso;
        }
    }

    //this need for load menu in toolbar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    //this for handle toolbar on click action
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            //mi_cycle : will refresh page intent
            //refresh page will use for Glide because short timeout request
            case R.id.mi_cycle:
                if (Build.VERSION.SDK_INT >= 11) {
                    recreate();
                } else {
                    Intent intent = getIntent();
                    intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    finish();
                    startActivity(intent);
                }
                break;
            case R.id.mi_hazard:
                Toast.makeText(FourteenActivity.this, "Hazard Carefull", Toast.LENGTH_SHORT).show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
