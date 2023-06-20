package com.ftn.ma_sit_project.commonUtils;

import android.content.ClipData;
import android.content.ClipDescription;
import android.util.Log;
import android.view.DragEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.ftn.ma_sit_project.Model.Skocko;

public class Draggable {

    public static void makeDraggable(ImageView imageView, String tag){

        imageView.setTag(tag);

        imageView.setOnLongClickListener(v -> {

            ClipData.Item item = new ClipData.Item((CharSequence) v.getTag());

            ClipData dragData = new ClipData(
                    (CharSequence) v.getTag(),
                    new String[] { ClipDescription.MIMETYPE_TEXT_PLAIN },
                    item);

            View.DragShadowBuilder myShadow = new View.DragShadowBuilder(imageView);

            v.startDragAndDrop(dragData, myShadow, null,0);

            return true;
        });
    }

    public static void makeTarget(ImageView imageView){

        imageView.setOnDragListener((v, e) ->{

            switch(e.getAction()){

                case DragEvent.ACTION_DRAG_STARTED:
                    return e.getClipDescription().hasMimeType(ClipDescription.MIMETYPE_TEXT_PLAIN);

                case DragEvent.ACTION_DRAG_ENTERED:
                    v.invalidate();
                    return true;

                case DragEvent.ACTION_DRAG_LOCATION:
                    return true;

                case DragEvent.ACTION_DROP:

                    ClipData.Item item = e.getClipData().getItemAt(0);
                    CharSequence dragData = item.getText();
//                    Toast.makeText(v.getContext(), "Dragged data is " + dragData, Toast.LENGTH_LONG).show();
                    Integer image = Skocko.getImage(dragData);
                    ((ImageView)v).setImageResource(image);
                    v.setTag((String) dragData);
//                    Toast.makeText(v.getContext(), "tag is " + v.getTag().toString(), Toast.LENGTH_SHORT).show();
                    v.invalidate();
                    return true;

                case DragEvent.ACTION_DRAG_ENDED:

                    ((ImageView)v).clearColorFilter();

                    v.invalidate();

                    if (e.getResult()) {
//                        Toast.makeText(v.getContext(), "The drop was handled.", Toast.LENGTH_LONG).show();
                    } else {
//                        Toast.makeText(v.getContext(), "The drop didn't work.", Toast.LENGTH_LONG).show();
                    }

                    return true;

                default:
                    Log.e("DragDrop Example","Unknown action type received by View.OnDragListener.");
                    break;
            }

            return false;
        });
    }

    public static void removeTarget(ImageView imageView){

        imageView.setOnDragListener(null);
    }
}
