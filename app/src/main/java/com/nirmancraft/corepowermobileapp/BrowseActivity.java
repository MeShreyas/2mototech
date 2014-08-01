package com.nirmancraft.corepowermobileapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.nirmancraft.corepower.R;
import com.nirmancraft.corepowermobileapp.helpers.lists.BrowseList;
import com.nirmancraft.corepowermobileapp.helpers.lists.CustomList;
import com.nirmancraft.corepowermobileapp.objects.DBRecord;
import com.nirmancraft.corepowermobileapp.utils.db.DataBaseHelper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class BrowseActivity extends Activity {

    private List<DBRecord> recordList= new ArrayList<DBRecord>();
    ListView listView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browse);

        DataBaseHelper helper = new DataBaseHelper(BrowseActivity.this);
        try {
            helper.createDataBase();
            helper.openDataBase();
            recordList = helper.getAllRecords();
            helper.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        BrowseList adapter = new
                BrowseList(BrowseActivity.this,recordList);
        listView = (ListView) findViewById(R.id.browseOptions);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                // Pass the id to the detail activity. That will then call the db to get the details
                // and populate the view
                Intent browseIntent = new Intent(BrowseActivity.this,DetailsActivity.class);
                browseIntent.putExtra("DB_ID",String.valueOf(view.getId()));
                startActivity(browseIntent);
            }
        });


    }
}
