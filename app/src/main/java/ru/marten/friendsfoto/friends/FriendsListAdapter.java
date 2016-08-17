package ru.marten.friendsfoto.friends;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import ru.marten.friendsfoto.gui.R;


import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by marten on 16.08.16.
 */
public class FriendsListAdapter extends BaseAdapter{

    private ArrayList<Friend> friendsList;

    public FriendsListAdapter(ArrayList<Friend> friendsList) {
        this.friendsList = friendsList;
    }

    @Override
    public int getCount() {
        return friendsList.size();
    }

    @Override
    public Object getItem(int i) {
        return friendsList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_friend, viewGroup, false);
        }

       final Friend item_friend = (Friend) getItem(i);

        TextView name = (TextView) view.findViewById(R.id.item_friend_name);
        name.setText(item_friend.getName());
        TextView status = (TextView) view.findViewById(R.id.item_friend_status);
        if(item_friend.getStatus() == 0) {
            status.setText(R.string.friend_status_offline);
        } else {
            status.setText(R.string.friend_status_online);
        }
        TextView info = (TextView) view.findViewById(R.id.item_friend_info);
        info.setText(item_friend.getInfo());
        final ImageView icon = (ImageView) view.findViewById(R.id.item_friend_icon);

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    final Bitmap bitmap = BitmapFactory.decodeStream((InputStream)new URL(item_friend.getIcon()).getContent());
                    icon.post(new Runnable() {
                        @Override
                        public void run() {

                            icon.setImageBitmap(bitmap);
                        }
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        return view;
    }
}
