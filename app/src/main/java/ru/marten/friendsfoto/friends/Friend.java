package ru.marten.friendsfoto.friends;

import android.graphics.Bitmap;

/**
 * Created by marten on 16.08.16.
 */
public class Friend {

    private String name;
    private int online;
    private String status;
    private String icon;

    public Friend(String name, int online, String status, String icon) {
        this.name = name;
        this.online = online;
        this.status = status;
        this.icon = icon;

    }

    public String getName() {
        return name;
    }

    public int getStatus() {
        return online;
    }

    public String getInfo() {
        return status;
    }

    public String getIcon() {
        return icon;
    }
}
