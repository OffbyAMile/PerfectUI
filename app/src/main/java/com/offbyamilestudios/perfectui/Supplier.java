package com.offbyamilestudios.perfectui;

import android.app.Application;
import android.app.DownloadManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.AlertDialog;

import com.offbyamilestudios.perfectui.data.AuthorData;
import com.offbyamilestudios.perfectui.data.HeaderListData;
import com.offbyamilestudios.perfectui.data.WallData;

import java.util.ArrayList;



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

        String[] authorNames = context.getResources().getStringArray(R.array.people_names);
        TypedArray wallNames = context.getResources().obtainTypedArray(R.array.wp_names);
        TypedArray wallUrls = context.getResources().obtainTypedArray(R.array.wp_urls);

        for (int i = 0; i < wallNames.length(); i++) {
            String[] names = context.getResources().getStringArray(wallNames.getResourceId(i, -1));
            String[] urls = context.getResources().getStringArray(wallUrls.getResourceId(i, -1));

                for (int i2 = 0; i2 < names.length; i2++) {
                    // Specify all required data and or variables here
                    walls.add(new WallData(context, names[i2].replace("*", ""), urls[i2], authorNames[i], i, names[i2].endsWith("*")));
                }
        }
        wallNames.recycle();
        wallUrls.recycle();
    return walls;
    }

    public ArrayList<WallData> getWallpapers(Context context, int authorId) {
        ArrayList<WallData> walls = new ArrayList<>();

        TypedArray wallNames = context.getResources().obtainTypedArray(R.array.wp_names);
        TypedArray wallUrls = context.getResources().obtainTypedArray(R.array.wp_urls);

        String[] names = context.getResources().getStringArray(wallNames.getResourceId(authorId, -1));
        String[] urls = context.getResources().getStringArray(wallUrls.getResourceId(authorId, -1));
        String[] authors = context.getResources().getStringArray(R.array.people_names);

        for (int i = 0; i < names.length; i++) {
            walls.add(new WallData(context, names[i].replace("*", ""), urls[i], authors[authorId], authorId, names[i].endsWith("*")));
        }

        wallNames.recycle();
        wallUrls.recycle();

        return walls;
    }

    public ArrayList<HeaderListData> getAdditionalInfo(Context context) {
        ArrayList<HeaderListData> headers = new ArrayList<>();

        return headers;
    }

    public AlertDialog getCreditDialog(Context context, DialogInterface.OnClickListener onClickListener) {
        return new AlertDialog.Builder(context)
                .setTitle(R.string.credit_required)
                .setMessage(R.string.credit_required_msg)
                .setPositiveButton("OK", onClickListener)
                .create();
    }
    public void downloadWallpaper(Context context, WallData data) {
        DownloadManager.Request r = new DownloadManager.Request(Uri.parse(data.url));
        r.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, data.name + ".png");
        r.allowScanningByMediaScanner();
        r.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        DownloadManager dm = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
        dm.enqueue(r);
    }

    public AlertDialog getDownloadedDialog(Context context, DialogInterface.OnClickListener onClickListener) {
        return new AlertDialog.Builder(context).setTitle(R.string.download_complete).setMessage(R.string.download_complete_msg).setPositiveButton("View", onClickListener).create();
    }

    public void shareWallpaper(Context context, WallData data) {
        Intent shareAction = new Intent(Intent.ACTION_SEND);
        shareAction.setType("image/*");
        shareAction.putExtra(Intent.EXTRA_STREAM, String.valueOf(Uri.parse(data.url)));
        context.startActivity(shareAction);
    }
}
