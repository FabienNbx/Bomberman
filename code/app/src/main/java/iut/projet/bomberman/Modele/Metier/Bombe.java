package iut.projet.bomberman.Modele.Metier;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.content.res.ResourcesCompat;

import iut.projet.bomberman.GameActivity;
import iut.projet.bomberman.R;

public class Bombe {
    private double x;
    private double y;
    private int height;
    private int width;
    private Context context;
    private BitmapDrawable imgBombe=null;
    private int dureeDeVie = 100;


    public BitmapDrawable getImgBombe(){
        return imgBombe;
    }

    public Context getContext(){
        return context;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public Bombe(Context c,double x, double y){
        context=c;
        setX(x);
        setY(y);
    }

    // redimensionnement de l'image selon la largeur/hauteur de l'écran passés en paramètre
    public void resize() {
        width= GameActivity.tailleEcranX/20;
        height=GameActivity.tailleEcranY/13;
        imgBombe = setImage(context, R.mipmap.bombe,width,height);
    }

    public void draw(Canvas c){
        if (imgBombe==null) return;
        Paint p = new Paint();
        c.drawBitmap(imgBombe.getBitmap(),(int)x,(int)y,p);
    }

    public BitmapDrawable setImage(final Context c, final int ressource, final int w, final int h)
    {
        Drawable dr = ResourcesCompat.getDrawable(c.getResources(), ressource, null);
        Bitmap bitmap = ((BitmapDrawable) dr).getBitmap();
        return new BitmapDrawable(c.getResources(), Bitmap.createScaledBitmap(bitmap, w, h, true));
    }

    public int getDureeDeVie() {
        return dureeDeVie;
    }

    public void setDureeDeVie(int dureeDeVie) {
        this.dureeDeVie = dureeDeVie;
    }
}
