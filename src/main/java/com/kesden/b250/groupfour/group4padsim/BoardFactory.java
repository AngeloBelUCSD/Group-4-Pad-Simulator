package com.kesden.b250.groupfour.group4padsim;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.Random;

public class BoardFactory {

    Random rand;
    ArrayList<Integer> colorList;
    MainActivity activity;
    Point point;

    public BoardFactory(MainActivity activity, Point point) {
        rand = new Random();

        colorList = new ArrayList<>(6);
        colorList.add(0,R.drawable.redorb);
        colorList.add(1,R.drawable.dark);
        colorList.add(2,R.drawable.heart);
        colorList.add(3,R.drawable.light);
        colorList.add(4,R.drawable.water);
        colorList.add(5,R.drawable.wood);

        this.activity = activity;
        this.point = point;

    }

    public void populateBoard(ArrayList<ImageView> orbList) {
        for(int i = 0; i<orbList.size();i++) {
            ImageView orb = orbList.get(i);
            Bitmap bitmap = BitmapFactory.decodeResource(activity.getResources(), colorList.get(rand.nextInt(6)));
            Bitmap newBitmap = Bitmap.createScaledBitmap(bitmap, point.x / 7, point.x / 7, true);
            orb.setImageBitmap(newBitmap);
            orb.setOnTouchListener(activity);
            orb.setOnDragListener(activity);
        }
    }
}
