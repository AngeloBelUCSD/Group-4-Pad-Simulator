package com.kesden.b250.groupfour.group4padsim;


import android.content.Context;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.widget.ImageView;

public class OrbView extends ImageView{

    public static int RED_ORB = 101;
    public static int BLUE_ORB = 201;
    public static int GREEN_ORB = 301;
    public static int LIGHT_ORB = 401;
    public static int DARK_ORB = 501;
    public static int HEAL_ORB = 601;

    private int id;

    /**
     * @param context
     */
    public OrbView(Context context)
    {
        super(context);
        // TODO Auto-generated constructor stub
    }

    /**
     * @param context
     * @param attrs
     */
    public OrbView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        // TODO Auto-generated constructor stub
    }

    /**
     * @param context
     * @param attrs
     * @param defStyle
     */
    public OrbView(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
        // TODO Auto-generated constructor stub
    }

    public void setID(int id)
    {
        this.id = id;
    }

    public int getID()
    {
        return id;
    }

    public void setImage(Bitmap bitmap)
    {
        setImageBitmap(bitmap);
    }

    public void setOrb(Bitmap bitmap, int id)
    {
        setID(id);
        setImage(bitmap);
    }
}
