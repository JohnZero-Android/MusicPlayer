package com.johnzero.musicplayer;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;

public class MusicService extends Service {
    MediaPlayer mediaPlayer;
    Context mContext;
    int[] music = new int[7];

    public MusicService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
        music[0] = R.raw.take_me_hand;
        music[1] = R.raw.all_time_low;
        music[2] = R.raw.fascination;
        music[3] = R.raw.give_me_your_love;
        music[4] = R.raw.on_my_way;
        music[5] = R.raw.shape_of_you;
        music[6] = R.raw.we_dont_talk_anymore;
        mediaPlayer = MediaPlayer.create(mContext, music[0]);
        mediaPlayer.setLooping(true);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        int index = intent.getIntExtra("musicId", 0);
        int command=intent.getIntExtra("command",0);
        if (command ==0) {
            mediaPlayer.stop();
            mediaPlayer = MediaPlayer.create(mContext, music[index]);
            mediaPlayer.setLooping(true);
            mediaPlayer.start();
        }
        if (command == 1) mediaPlayer.pause();
        if(command==2) mediaPlayer.start();
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }
}
