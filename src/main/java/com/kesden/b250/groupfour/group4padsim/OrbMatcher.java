package com.kesden.b250.groupfour.group4padsim;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import static java.util.Arrays.*;


public class OrbMatcher {
    public int total;
    public int[] threeList;
    private BoardFactory factory;
    private Random rand;
    private ArrayList<ArrayList<Integer>> comboList;
    private ArrayList<Integer> redCombo;
    private ArrayList<Integer> darkCombo;
    private ArrayList<Integer> healCombo;
    private ArrayList<Integer> lightCombo;
    private ArrayList<Integer> blueCombo;
    private ArrayList<Integer> greenCombo;

    private static final String TAG = MainActivity.class.getSimpleName();

    MainActivity activity;

    public OrbMatcher(BoardFactory bFactory){
        rand = new Random();
        this.factory = bFactory;
        threeList = new int[30];
        fill(threeList, -1);
        activity = bFactory.activity;

        //instantiation of combo lists
        comboList = new ArrayList<ArrayList<Integer>>();
        redCombo = new ArrayList<Integer>();
        darkCombo = new ArrayList<Integer>();
        healCombo = new ArrayList<Integer>();
        lightCombo = new ArrayList<Integer>();
        blueCombo = new ArrayList<Integer>();
        greenCombo = new ArrayList<Integer>();
        comboList.add(0,redCombo);
        comboList.add(1,darkCombo);
        comboList.add(2, healCombo);
        comboList.add(3, lightCombo);
        comboList.add(4, blueCombo);
        comboList.add(5,greenCombo);

    }


    public void threeSort(){
        ArrayList<OrbView> orbs = factory.orbList;
        for(int i = 2; i<orbs.size();i++) {
            OrbView orb = orbs.get(i);
            if (i >= 2) {
                if (!(i % 6 == 0) || !(i % 6 == 1)) {
                    if (orbs.get(i - 2).getID() == (orbs.get(i - 1).getID())
                            && orbs.get(i - 1).getID() == orb.getID()) {
                        Log.d(TAG, "horizontal check");
                        //tag horizontal 3s
                        int check = i;
                        int add = 0;
                        int type = orb.getID();
                        while (check % 6 != 5 && orbs.get(check).getID() == orbs.get(check + 1).getID()) {
                            check++;
                            add++;
                        }

                        for (int x = i - 2; x < i + add + 1; x++) {
                            threeList[x] = type;
                            int curr = x;
                            while (curr - 6 >= 0) {
                                OrbView topOrb = orbs.get(curr - 6);
                                OrbView currOrb = orbs.get(curr);
                                Bitmap swap = ((BitmapDrawable) topOrb.getDrawable()).getBitmap();
                                currOrb.setOrb(swap, topOrb.getID());
                                curr = curr - 6;
                            }
                            int orbID = rand.nextInt(6);
                            Bitmap bitmap = BitmapFactory.decodeResource(activity.getResources(), factory.colorList.get(orbID));
                            Bitmap newBitmap = Bitmap.createScaledBitmap(bitmap, factory.point.x / 6, factory.point.y / 10, true);
                            orbs.get(curr).setOrb(newBitmap, orbID);
                        }
                        total = 300+100*add;
                    }
                }
                if (i > 11) {
                    if (orbs.get(i - 12).getID() == (orbs.get(i - 6).getID())
                            && orbs.get(i - 6).getID() == orb.getID()) {
                        Log.d(TAG, "vertical check");
                        //tag vertical 3s
                        int curr = i;
                        int add = 0;
                        int type = orb.getID();
                        while (curr + 6 < 30 && orbs.get(curr).getID() == orbs.get(curr + 6).getID()) {
                            curr += 6;
                            add += 6;
                        }
                        for (int x = i - 12; x < curr + 1; x += 6) {
                            threeList[x] = type;
                        }
                        while (curr - 18 - add >= 0) {
                            OrbView topOrb = orbs.get(curr - 18 - add);
                            OrbView currOrb = orbs.get(curr);
                            Bitmap swap = ((BitmapDrawable) topOrb.getDrawable()).getBitmap();
                            currOrb.setOrb(swap, topOrb.getID());
                            curr = curr - 6;
                        }
                        while (curr >= 0) {
                            int orbID = rand.nextInt(6);
                            Bitmap bitmap = BitmapFactory.decodeResource(activity.getResources(), factory.colorList.get(orbID));
                            Bitmap newBitmap = Bitmap.createScaledBitmap(bitmap, factory.point.x / 6, factory.point.y / 10, true);
                            orbs.get(curr).setOrb(newBitmap, orbID);
                            curr = curr - 6;
                        }
                        total = 300+100*add;
                    }
                }
            }
        }
    }


    ///////////////////////////TEST CODE/////////////////////////////

    /*
    public void sort()
    {
        threeSort();
        comboCheck(threeList);
        replaceOrbs();
        resetLists();
    }
    public void comboCheck(int[] threeList)
    {
        for(int i = 0; i<threeList.length;i++)
        {
            if(threeList[i]>-1) {
                ArrayList<Integer> adj = new ArrayList<Integer>();
                adj.add(i);
                int type = threeList[i];
                while (!adj.isEmpty()) {
                    int curr = adj.get(0);
                    adj.remove(0);
                    comboList.get(type).add(curr);
                    if(curr%6!=0 && threeList[curr-1]==type)
                    {
                        adj.add(curr-1);
                    }
                    if(curr%6!=5 && threeList[curr+1]==type)
                    {
                        adj.add(curr+1);
                    }
                    if(curr>=6 && threeList[curr-6]==type)
                    {
                        adj.add(curr-6);
                    }
                    if(curr<24 && threeList[curr+6]==type)
                    {
                        adj.add(curr+6);
                    }
                    threeList[curr]=-1;
                }
            }
        }
    }
    public void replaceOrbs()
    {
        int test = 0;
        for(ArrayList<Integer> x:comboList)
        {

            if(!x.isEmpty())
            {
                Log.d(TAG, "Combo for type " + test + " is size " + x.size());
                for(int location:x)
                {
                    OrbView newOrb = factory.orbList.get(location);
                    int currID = newOrb.getID();
                    int orbID = rand.nextInt(6);
                    while(currID==orbID) {
                        orbID = rand.nextInt(6);
                    }
                    Bitmap bitmap = BitmapFactory.decodeResource(activity.getResources(), factory.colorList.get(orbID));
                    Bitmap newBitmap = Bitmap.createScaledBitmap(bitmap, factory.point.x / 6, factory.point.y / 10, true);
                    newOrb.setOrb(newBitmap, orbID);
                }
            }
            test++;
        }
    }
    public void resetLists()
    {
        threeList = new int[30];
        fill(threeList, -1);
        for(ArrayList<Integer> x:comboList)
        {
            x.clear();
        }
    }*/

}
