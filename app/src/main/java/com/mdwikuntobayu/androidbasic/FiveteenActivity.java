package com.mdwikuntobayu.androidbasic;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SeekBar;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.net.FileNameMap;
import java.util.ArrayList;
import java.util.List;

public class FiveteenActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String SD_PATH = new String("/sdcard/Music/saintsaya/");
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
}

class Songs implements FilenameFilter {

    @Override
    public boolean accept(File dir, String filename) {
        return (filename.endsWith(".mp3"));
    }
}
