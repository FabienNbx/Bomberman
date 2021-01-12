package iut.projet.bomberman.Modele.Metier;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.content.res.ResourcesCompat;

import iut.projet.bomberman.GameActivity;
import iut.projet.bomberman.R;

public class RectExplosion {
    private int nblife = 30;
    private Context context;
    private Rect r;



    public Context getContext(){
        return context;
    }


    public RectExplosion(Context c,Rect r){
        context=c;
        this.r=r;
    }

    public int getNblife() {
        return nblife;
    }

    public void setNblife(int nblife) {
        this.nblife = nblife;
    }

    public Rect getR() {
        return r;
    }
}
