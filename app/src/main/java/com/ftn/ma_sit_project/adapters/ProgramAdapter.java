package com.ftn.ma_sit_project.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.ftn.ma_sit_project.Model.Data;
import com.ftn.ma_sit_project.Model.User;
import com.ftn.ma_sit_project.R;
import com.ftn.ma_sit_project.fragments.LoadingScreenFragment;
import com.ftn.ma_sit_project.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;

public class ProgramAdapter extends ArrayAdapter<String> {

    AppCompatActivity activity;
    Context context;
    String[] title;
    String[] description;

    String[] title1;
    String[] description1;
    LayoutInflater layoutInflater;

    List<User> users = new ArrayList<>();
    public ProgramAdapter(AppCompatActivity activity, Context context, String[] title, String[] description) {
        super(context, R.layout.single_item, R.id.titleId, title);
        this.activity = activity;
        this.context = context;
        this.title = title;
        this.description = description;
    }

    public ProgramAdapter(AppCompatActivity activity, Context context, String[] title, String[] description, boolean bool) {
        super(context, R.layout.single_item, R.id.rank_title_id, title);
        this.activity = activity;
        this.context = context;
        this.title1 = title;
        this.description1 = description;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View singleitem = convertView;

        if(singleitem == null){
            layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            singleitem = layoutInflater.inflate(R.layout.single_item, null);
            Log.d("Adapter", "Inflated single_item layout");
        }

        if(title != null && description != null){
            TextView title = singleitem.findViewById(R.id.titleId);
            TextView description = singleitem.findViewById(R.id.descriptionId);
            TextView playButton = singleitem.findViewById(R.id.rank_friendly_1v1);


            title.setText(this.title[position]);
            description.setText(this.description[position]);
            playButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (Data.loggedInUser == null) {
                        Toast.makeText(context, "Please log in/register", Toast.LENGTH_SHORT).show();
                    } else {
                        activity.getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.fragment_container, new LoadingScreenFragment())
                                .commit();
                    }
                }
            });
        }
        else{
            TextView title = singleitem.findViewById(R.id.titleId);
            TextView description = singleitem.findViewById(R.id.descriptionId);

            title.setText(this.title1[position]);
            description.setText(this.description1[position]);
        }


        return singleitem;
    }
}
