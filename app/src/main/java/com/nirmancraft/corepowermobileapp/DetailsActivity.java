package com.nirmancraft.corepowermobileapp;

import android.app.Activity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.nirmancraft.corepower.R;
import com.nirmancraft.corepowermobileapp.objects.DBRecord;
import com.nirmancraft.corepowermobileapp.utils.db.DataBaseHelper;

public class DetailsActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_layout);

        Bundle extras = getIntent().getExtras();
        String id = extras.getString("DB_ID");
        DataBaseHelper helper = new DataBaseHelper(DetailsActivity.this);
        helper.openDataBase();
        DBRecord rec = helper.getRecordById(Integer.valueOf(id));
        helper.close();
        if(null != rec) {
            TextView titleView = (TextView) findViewById(R.id.detailTitle);
            titleView.setText(rec.getTitle());
            String imageName = rec.getImage().substring(0,rec.getImage().indexOf("."));
            ImageView img = (ImageView) findViewById(R.id.detailImage);
            img.setImageResource(this.getResources().getIdentifier(imageName, "drawable", this.getPackageName()));
            TextView txtView = (TextView) findViewById(R.id.detailText);
            txtView.setMovementMethod(new ScrollingMovementMethod());
            txtView.setText(rec.getBody());
        }
    }



}
