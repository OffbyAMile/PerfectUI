package com.offbyamilestudios.perfectui;

import android.app.Application;
import android.content.Context;
import android.content.res.Resources;

import com.offbyamilestudios.perfectui.data.AuthorData;
import com.offbyamilestudios.perfectui.data.WallData;

import java.util.ArrayList;

import Application;

public class Supplier extends Application {

    public boolean getNetworkResources() {
        return true;
    }

    public ArrayList<AuthorData> getAuthors(Context context) {
        ArrayList<AuthorData> authors = new ArrayList<>();
        String[] authorNames = context.getResources().getStringArray(R.array.people_names);
        String[] authorIcons = context.getResources().getStringArray(R.array.people_icons);
        String[] authorDescs = context.getResources().getStringArray(R.array.people_desc);
        String[] authorUrls = null;

        try {
            authorUrls = context.getResources().getStringArray(R.array.people_urls);
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < authorNames.length; i++) {
            authors.add(new AuthorData(authorNames[i], authorIcons[i], authorDescs[i], i, authorUrls == null ? null : authorUrls[i]));
        }
        return authors;
    }

    public ArrayList<WallData> getWallpapers(Context context) {
        ArrayList<WallData> walls = new ArrayList<>();
    }
}
