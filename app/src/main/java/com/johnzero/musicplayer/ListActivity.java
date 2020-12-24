package com.johnzero.musicplayer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class ListActivity extends AppCompatActivity {
    Context mContext;
    StringAdapter mAdapter;
    ListView listView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        mContext=this;
        listView=findViewById(R.id.listView);
        mAdapter=new StringAdapter(mContext,Utils.getMusicList());
        listView.setAdapter(mAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                setResult(i,new Intent(mContext,MainActivity.class));
                finish();
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        //修改后退按钮
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
            setResult(-1,new Intent(mContext,MainActivity.class));
        }
        return super.onKeyDown(keyCode, event);
    }
}