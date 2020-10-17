package com.example.chooser.modes;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import androidx.annotation.RequiresApi;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.interpolator.view.animation.FastOutSlowInInterpolator;
import androidx.transition.Fade;
import androidx.transition.TransitionManager;
import androidx.transition.TransitionSet;

import com.example.chooser.R;
import com.transitionseverywhere.extra.Scale;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public abstract class GameMode {

    protected int modeNumber;
    protected Map<Integer, ImageView> imageViewsByPointers;
    protected ConstraintLayout myLayout;
    protected List<Drawable> drawableList;
    protected Map<Drawable,Integer> colorMap;
    protected Random random;
    protected TransitionSet animationSet;


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public GameMode(ConstraintLayout layout, int modeNumber) {

        this.myLayout = layout;
        random=new Random();
        imageViewsByPointers = new HashMap<>();

        drawableList = new ArrayList<>();
        initializeDrawableList();

        colorMap=new HashMap<>();
        initializeColorMap();

        this.modeNumber = modeNumber;

        // for animation
        animationSet = new TransitionSet()
                .addTransition(new Fade())
                .addTransition(new Scale())
                .setInterpolator(new FastOutSlowInInterpolator());

    }


    public void onDown(float tapLocationX, float tapLocationY, int pointerId) { // when new finger touching screen

        int viewWidth=300, viewHeight=300;
        ImageView iv = new ImageView(myLayout.getContext());
        iv.setLayoutParams(new FrameLayout.LayoutParams(viewWidth, viewHeight));
        colorsControl(iv);
        myLayout.addView(iv);
        iv.setVisibility(View.GONE);
        iv.setX(tapLocationX - viewWidth/2);
        iv.setY(tapLocationY - viewHeight/2);
        TransitionManager.beginDelayedTransition((ViewGroup) myLayout, animationSet);
        iv.setVisibility(View.VISIBLE);
        imageViewsByPointers.put(pointerId, iv);
    }

    public abstract void onUp(int pointerId);

    public abstract void endScene();

    protected abstract void colorsControl(ImageView iv);

    public abstract void cancelAndReset();


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    protected void initializeDrawableList() {

        drawableList.add(myLayout.getContext().getDrawable(R.drawable.vd_vector1));
        drawableList.add(myLayout.getContext().getDrawable(R.drawable.vd_vector2));
        drawableList.add(myLayout.getContext().getDrawable(R.drawable.vd_vector3));
        drawableList.add(myLayout.getContext().getDrawable(R.drawable.vd_vector4));
        drawableList.add(myLayout.getContext().getDrawable(R.drawable.vd_vector5));
        drawableList.add(myLayout.getContext().getDrawable(R.drawable.vd_vector6));
    }


    protected void initializeColorMap() {

        colorMap.put(drawableList.get(0), Color.parseColor("#e99e9e"));
        colorMap.put(drawableList.get(1), Color.parseColor("#cdf5ee"));
        colorMap.put(drawableList.get(2), Color.parseColor("#8690fa"));
        colorMap.put(drawableList.get(3), Color.parseColor("#fff087"));
        colorMap.put(drawableList.get(4), Color.parseColor("#ff9a00"));
        colorMap.put(drawableList.get(5), Color.parseColor("#b7ffa8"));
    }

    public int getModeNumber(){
        return this.modeNumber;
    }

    public Map<Integer,ImageView> getImageViewsByPointersCopy(){

        Map<Integer,ImageView> copy=new HashMap<>();
        copy.putAll(imageViewsByPointers);
        return copy;

    }




}
