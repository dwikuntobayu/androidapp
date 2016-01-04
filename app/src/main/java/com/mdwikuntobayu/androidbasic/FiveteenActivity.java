package com.mdwikuntobayu.androidbasic;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SeekBar;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class FiveteenActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String SD_PATH = new String("/sdcard/Music/saintsaya/");
    private static final String SD_PATH_CAMERA = new String("/sdcard/DCIM/Camera/");
    private List<String> songs = new ArrayList<String>();
    private MediaPlayer media_player = new MediaPlayer();
    private Button btn_play, btn_stop, btn_paus;
    private ListView al_song;
    private int song_position = 0;
    private int song_position_new = 0;
    private boolean song_paused = false;
    private SeekBar sb_volume, sb_duration;
    private final static int MAX_VOL = 100;
    private float volume, current_progress;
    private static final int IMAGE_GALLERY_REQUEST = 20;
    private ImageView img_preview;
    List<String> image_urls = new ArrayList<>();
    private GridView gv_images;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fiveteen);
        get_songs();

        btn_play = (Button)findViewById(R.id.btn_play);
        btn_stop = (Button)findViewById(R.id.btn_stop);
        btn_paus = (Button)findViewById(R.id.btn_paus);
        sb_volume = (SeekBar)findViewById(R.id.sb_volume);
        sb_duration = (SeekBar)findViewById(R.id.sb_duration);

        btn_play.setOnClickListener(this);
        btn_stop.setOnClickListener(this);
        btn_paus.setOnClickListener(this);

        if (!media_player.isPlaying()) {
            btn_paus.setEnabled(false);
            btn_stop.setEnabled(false);
        }

        sb_volume.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                volume = (float) (1 - (Math.log(MAX_VOL - progress) / Math.log(MAX_VOL)));
                current_progress = progress;
                media_player.setVolume(volume, volume);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                media_player.setVolume(volume, volume);
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                media_player.setVolume(volume, volume);
            }
        });

        img_preview = (ImageView)findViewById(R.id.img_preview);
        File path_camera = new File(SD_PATH_CAMERA);
        if (path_camera.listFiles(new Images()).length > 0 ) {
            for (File file : path_camera.listFiles(new Images())) {
                image_urls.add(path_camera + "/" + file.getName());
            }
        }
//        gv_images = (GridView)findViewById(R.id.gv_images);
        ExpandableHeightGridView gv_images = (ExpandableHeightGridView) findViewById(R.id.gv_images);
        gv_images.setExpanded(true);
        PicassoAdapter adapter = new PicassoAdapter(getApplicationContext(), R.layout.picasso_img, image_urls);
        gv_images.setAdapter(adapter);
    }

    public class PicassoAdapter extends ArrayAdapter {
        private LayoutInflater inflater;
        private int resource;
        private Context context;
        private List<String> image_urls;

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
            Picasso.with(context)
                    .load(new File(image_urls.get(position)))
                    .placeholder(R.drawable.ic_action_name)
                    .error(R.drawable.ic_action_name2)
                    .fit()
                    .into(holder.iv_picasso, new Callback() {
                        @Override
                        public void onSuccess() {
                            pb_picasso.setVisibility(View.GONE);
                        }

                        @Override
                        public void onError() {
                            pb_picasso.setVisibility(View.GONE);
                        }
                    });

            return convertView;
        }

        class ViewHolder {
            private ImageView iv_picasso;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case(R.id.btn_play):
                if (!media_player.isPlaying()) {
                    if (song_paused) {
                        media_player.start();
                    } else {
                        try {
                            media_player.reset();
                            media_player.setDataSource(SD_PATH + songs.get(song_position));
                            media_player.prepare();
                            media_player.start();
                            btn_paus.setEnabled(true);
                            btn_stop.setEnabled(true);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
                break;
            case(R.id.btn_stop):
                stop_song();
                break;
            case(R.id.btn_paus):
                if (media_player.isPlaying()) {
                    media_player.pause();
                    song_paused = true;
                }
                break;
        }
    }

    private void stop_song() {
        if (media_player.isPlaying()) {
            media_player.stop();
            //reset(), will set media_player to start state of media_player
//            media_player.reset();
//            media_player.release();
            song_paused = false;
            btn_paus.setEnabled(false);
            btn_stop.setEnabled(false);
        }
    }

    private void get_songs(){
        File path = new File(SD_PATH);
        al_song = (ListView)findViewById(R.id.lv_songs);
        if (path.listFiles(new Songs()).length > 0 ) {
            for (File file : path.listFiles(new Songs())) {
                songs.add(file.getName());
                Sc.debug(String.valueOf(file.getPath()));
            }
            ArrayAdapter<String> song_list = new ArrayAdapter<String>(this, R.layout.name_list, songs);
            al_song.setAdapter(song_list);
            al_song.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                   song_position = position;
                }
            });
        }
    }

    //image processing method
    //this method for handle image gallery
    public void on_image_gallery_clicked(View v) {
        Intent photo_intent = new Intent(Intent.ACTION_PICK);
        File photo_dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        String photo_path = photo_dir.getPath();
        Uri data = Uri.parse(photo_path);
        photo_intent.setDataAndType(data, "image/*");
        startActivityForResult(photo_intent, IMAGE_GALLERY_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == IMAGE_GALLERY_REQUEST) {
                Uri image_uri = data.getData();
                InputStream input_stream;
                try {
                    input_stream = getContentResolver().openInputStream(image_uri);
                    Bitmap image = BitmapFactory.decodeStream(input_stream);
                    img_preview.setImageBitmap(image);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                    Sc.alert(this, "Cannot open image file ");
                }

            }
        }
    }
}

class Songs implements FilenameFilter {

    @Override
    public boolean accept(File dir, String filename) {
        return (filename.endsWith(".mp3"));
    }
}

class Images implements FilenameFilter {

    @Override
    public boolean accept(File dir, String filename) {
        return (filename.endsWith(".jpg"));
    }
}
