package com.ftn.ma_sit_project.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;

import com.ftn.ma_sit_project.R;

public class ProgramAdapter extends ArrayAdapter<String> {

    Context context;
    String[] title;
    String[] description;
    String[] title1;
    String[] description1;
    LayoutInflater layoutInflater;
    public ProgramAdapter(Context context, String[] title, String[] description) {
        super(context, R.layout.single_item, R.id.titleId, title);
        this.context = context;
        this.title = title;
        this.description = description;
    }

    public ProgramAdapter(Context context, String[] title, String[] description, boolean bool) {
        super(context, R.layout.single_item, R.id.rank_title_id, title);
        this.context = context;
        this.title1 = title;
        this.description1 = description;
    }

    @SuppressLint("SuspiciousIndentation")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View singleitem = convertView;

        if(singleitem == null) {
            layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            singleitem = layoutInflater.inflate(R.layout.single_item, null);
        }

        if(title != null && description != null)   {
            TextView title = singleitem.findViewById(R.id.titleId);
            TextView description = singleitem.findViewById(R.id.descriptionId);

            title.setText(this.title[position]);
            description.setText(this.description[position]);
        }
        else {
            TextView title = singleitem.findViewById(R.id.rank_title_id);
            TextView description = singleitem.findViewById(R.id.rank_description_id);

            title.setText(this.title1[position]);
            description.setText(this.description1[position]);
        }


        return singleitem;
    }
}
