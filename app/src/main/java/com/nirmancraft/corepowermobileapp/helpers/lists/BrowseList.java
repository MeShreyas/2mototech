package com.nirmancraft.corepowermobileapp.helpers.lists;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nirmancraft.corepower.R;
import com.nirmancraft.corepowermobileapp.objects.DBRecord;

import java.util.List;

/**
 * Created by Shreyas on 7/12/2014.
 */
public class BrowseList extends ArrayAdapter<DBRecord> {
    private final Activity context;
    private List<DBRecord> records;

    public BrowseList(Activity context, List<DBRecord> records) {
        super(context, R.layout.list_single, records);
        this.context=context;
        this.records = records;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView= inflater.inflate(R.layout.list_single, null, true);
        TextView txtTitle = (TextView) rowView.findViewById(R.id.txt);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.img);
        DBRecord dbrec = records.get(position);
        txtTitle.setText(dbrec.getTitle());
        String imageName = dbrec.getImage().substring(0,dbrec.getImage().indexOf("."));
        imageView.setImageResource(context.getResources().getIdentifier(imageName,"drawable",context.getPackageName()));
        rowView.setId(dbrec.get_id());
        return rowView;
    }



}
