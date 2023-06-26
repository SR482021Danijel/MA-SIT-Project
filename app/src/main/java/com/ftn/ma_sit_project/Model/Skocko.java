package com.ftn.ma_sit_project.Model;

import com.ftn.ma_sit_project.R;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Skocko {

    public static Map<String, Integer> options = new HashMap<>();
    static {
        Map<String, Integer> temp = new HashMap<>();
        temp.put("1", R.drawable.jump_transparent);
        temp.put("2", R.drawable.baseline_rectangle_24);
        temp.put("3", R.drawable.baseline_circle_24);
        temp.put("4", R.drawable.baseline_heart_broken_24);
        temp.put("5", R.drawable.baseline_change_history_24);
        temp.put("6", R.drawable.baseline_star_24);
        options = Collections.unmodifiableMap(temp);
    }

    public static Map<String, Integer> getOptions() {
        return options;
    }

    public static Integer getImage(CharSequence id){

        return getOptions().get(id);
    }
}
