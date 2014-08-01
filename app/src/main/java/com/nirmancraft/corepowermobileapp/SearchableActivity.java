package com.nirmancraft.corepowermobileapp;

import android.app.Activity;
import android.app.ListActivity;
import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.nirmancraft.corepower.R;
import com.nirmancraft.corepowermobileapp.helpers.lists.BrowseList;
import com.nirmancraft.corepowermobileapp.objects.DBRecord;
import com.nirmancraft.corepowermobileapp.utils.db.DataBaseHelper;

import java.util.List;

public class SearchableActivity extends Activity {

    List<DBRecord> recordList;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searchable);

        Intent intent = getIntent();
        if(Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            recordList = searchData(query);
            BrowseList adapter = new
                    BrowseList(SearchableActivity.this,recordList);
            listView = (ListView) findViewById(R.id.browseOptions);
            listView.setAdapter(adapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                    // Pass the id to the detail activity. That will then call the db to get the details
                    // and populate the view
                    Intent browseIntent = new Intent(SearchableActivity.this, DetailsActivity.class);
                    browseIntent.putExtra("DB_ID", String.valueOf(view.getId()));
                    startActivity(browseIntent);
                }
            });
        }
    }

    private List<DBRecord> searchData(String query) {
        DataBaseHelper helper = new DataBaseHelper(getApplicationContext());
        helper.openDataBase();
        List<DBRecord> records = helper.searchRecords(query);
        helper.close();
        return records;
    }


}
