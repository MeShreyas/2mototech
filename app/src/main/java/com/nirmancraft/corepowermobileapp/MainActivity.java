package com.nirmancraft.corepowermobileapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.nirmancraft.corepower.R;
import com.nirmancraft.corepowermobileapp.helpers.lists.CustomList;
import com.nirmancraft.corepowermobileapp.license.LicenseManager;
import com.nirmancraft.corepowermobileapp.objects.DBRecord;
import com.nirmancraft.corepowermobileapp.utils.db.DataBaseHelper;

import java.io.IOException;

public class MainActivity extends Activity {

    private static final int SEARCH = 0;
    private static final int BROWSE = 1;
    private static final int CALL = 2;
    ListView listView;
    String[] options = {
            "Search for Solution",
            "Browse all Solutions",
            "Contact Us"
    } ;
    Integer[] optionImages = {
            R.drawable.icon_search,
            R.drawable.icon_browse,
            R.drawable.icon_contact
    };

    @Override
    public boolean onSearchRequested() {

        return super.onSearchRequested();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        CustomList adapter = new
                CustomList(MainActivity.this, options, optionImages);

            listView = (ListView) findViewById(R.id.mainMenuOptions);
            listView.setAdapter(adapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){

                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                    switch(position) {
                        case SEARCH:
                            onSearchRequested();
                            break;
                        case BROWSE:
                            // Need to call a listing Activity here to load all the DB entries
                            Intent browseIntent = new Intent(MainActivity.this,BrowseActivity.class);
                            startActivity(browseIntent);
                            break;
                        case CALL:
                            Toast.makeText(MainActivity.this,"Mail us at this@here.com",Toast.LENGTH_LONG).show();
                            break;
                    }
                }
            });

    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setMessage("Are you sure you want to exit?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        MainActivity.this.finish();
                    }
                })
                .setNegativeButton("No", null)
                .show();
    }
}
