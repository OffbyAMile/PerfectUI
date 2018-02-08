package com.offbyamilestudios.perfectui;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toolbar;

import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;

public class HomeActivity extends AppCompatActivity {

    android.support.v7.widget.Toolbar toolbar;
    View fragmentView;
    Fragment fragment;
    Drawer drawer;
    DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        final SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        if (prefs.getInt("version", 0) != BuildConfig.VERSION_CODE) {
            new AlertDialog.Builder(this).setTitle("Changelog").setMessage(getString(R.string.changelog))
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            prefs.edit().putInt("version", BuildConfig.VERSION_CODE).apply();
                            dialog.dismiss();
                        }
                    }).create().show();
        }

        toolbar = findViewById(R.id.toolbar);
        fragmentView = findViewById(R.id.fragment);
        drawerLayout = findViewById(R.id.drawer_layout);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.drawer_toggle);

        drawer = new DrawerBuilder()
                .withActivity(this)
                .withFullscreen(true)
                .withToolbar(toolbar)
                .withAccountHeader(new AccountHeaderBuilder()
                    .withActivity(this)
                    .withCompactStyle(true)
                    .withHeaderBackground(R.mipmap.ic_launcher_round)
                    .withProfileImagesClickable(false)
                    .withSelectionListEnabledForSingleProfile(false)
                    .addProfiles(
                            new ProfileDrawerItem().withName(getString(R.string.app_name)).withEmail("Version" + BuildConfig.VERSION_NAME).withIcon(ContextCompat.getDrawable(this, R.mipmap.ic_launcher))
                    )
                .build())
                .withSelectedItem(0)
                .addDrawerItems(
                        new SecondaryDrawerItem().withName(getString(R.string.title_home))
                        .withIdentifier(1).withIcon(R.drawable.ic_home),
                )
    }
}
