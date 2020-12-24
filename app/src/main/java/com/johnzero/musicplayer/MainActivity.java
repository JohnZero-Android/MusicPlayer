package com.johnzero.musicplayer;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    Context mContext;
    final static String TAG="JohnZero";
    TextView tv_musicName;
    Button btn_music_previous;
    Button btn_music;
    Button btn_music_next;
    ImageView iv_disk;

    boolean isPlay = false;
    ArrayList<String> musicList = new ArrayList<>();
    int LISTACTIVITY = 101;
    static int index=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG,"onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo .SCREEN_ORIENTATION_PORTRAIT);//竖屏
        mContext = this;
        tv_musicName = findViewById(R.id.tv_musicName);
        btn_music_previous = findViewById(R.id.btn_music_previous);
        btn_music = findViewById(R.id.btn_music);
        btn_music_next = findViewById(R.id.btn_music_next);
        iv_disk = findViewById(R.id.iv_disk);
        btn_music_previous.setOnClickListener(this);
        btn_music.setOnClickListener(this);
        btn_music_next.setOnClickListener(this);
        iv_disk.setOnClickListener(this);
        musicList=Utils.getMusicList();
        btn_music.setBackgroundResource(R.mipmap.music_stop);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_music_previous:
                Toast.makeText(mContext, "上一首", Toast.LENGTH_SHORT).show();
                index=index-1<0?6:index-1;
                playMusic(index);
                break;
            case R.id.btn_music:
                String str = "";
                if (isPlay) {
                    str = "已暂停";
                    stopMusic();
                } else {
                    str = "正在播放";
                    playMusic(index,2);
                }
                Toast.makeText(mContext, str, Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_music_next:
                Toast.makeText(mContext, "下一首", Toast.LENGTH_SHORT).show();
                index=(++index)%7;
                playMusic(index);
                break;
            case R.id.iv_disk:
                Toast.makeText(mContext, "歌曲列表", Toast.LENGTH_SHORT).show();
                startActivityForResult(new Intent(mContext, ListActivity.class), LISTACTIVITY);
                break;
        }
    }

    @Override
    protected void onDestroy() {
        Log.d(TAG,"onDestroy");
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        Log.d(TAG,"onPause");
        super.onPause();
    }

    @Override
    protected void onResume() {
        Log.d(TAG,"onResume");
        super.onResume();
}

    @Override
    protected void onActivityResult(final int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == LISTACTIVITY) {
            if(resultCode<0) playMusic(index,2);
            else{
                index=resultCode;
                playMusic(index);
            }
        }
    }

    void playMusic(int index){
        playMusic(index,0);
    }

    void playMusic(int index,int command){
        Log.d(TAG,"index="+index);
        Intent intent=new Intent(mContext,MusicService.class);
        intent.putExtra("musicId",index);
        intent.putExtra("command",command);
        startService(intent);
        isPlay = true;
        btn_music.setBackgroundResource(R.mipmap.music_on);
        if(index>=0) tv_musicName.setText(musicList.get(index));
    }

    void stopMusic(){
        Intent intent=new Intent(mContext,MusicService.class);
        intent.putExtra("musicId",index);
        intent.putExtra("command",1);
        startService(intent);
        isPlay = false;
        btn_music.setBackgroundResource(R.mipmap.music_stop);
    }
}