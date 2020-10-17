package com.example.chooser.booms;

import android.graphics.drawable.Drawable;

public class BoomMenuPiece {

    private int color;
    private int fingersMode;
    private Drawable numberImg;

   public BoomMenuPiece(int color, int fingersMode, Drawable numberImg){
       this.color=color;
       this.fingersMode=fingersMode;
       this.numberImg=numberImg;
   }

    public Drawable getNumberImg() {
        return numberImg;
    }

    public int getColor() {
        return color;
    }

    public int getFingersMode() {
        return fingersMode;
    }
}
