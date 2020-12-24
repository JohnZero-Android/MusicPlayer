package com.johnzero.musicplayer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * @author: JohnZero
 * @date: 2020-09-03
 **/
public class StringAdapter extends BaseAdapter {
    private ArrayList<String> musicList;
    private Context mContext;

    public StringAdapter(Context mContext, ArrayList<String> musicList){
        this.mContext=mContext;
        this.musicList=musicList;
    }

    @Override
    public int getCount() {
        return musicList.size();
    }

    @Override
    public Object getItem(int i) {
        return musicList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder=null;
        if(view==null){
            holder=new ViewHolder();
            view= LayoutInflater.from(mContext).inflate(R.layout.listview_item_layout,viewGroup,false);
            holder.appName=(TextView)view.findViewById(R.id.musicName);
            view.setTag(holder);
        }else{
            holder=(ViewHolder)view.getTag();
        }
        holder.appName.setText(musicList.get(i));
        return view;
    }

    static class ViewHolder{
        TextView appName;
    }
}
