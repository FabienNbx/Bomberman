package iut.projet.bomberman.Modele.Metier;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.content.res.ResourcesCompat;

import iut.projet.bomberman.R;

public class Bonus {
    private double x;
    private double y;
    protected int height;
    protected int width;
    protected Context context;
    protected BitmapDrawable imgBonus=null;

    public BitmapDrawable getImgBonus(){
        return imgBonus;
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

    public Bonus(Context c,double x, double y){
        context=c;
        setX(x);
        setY(y);
    }

    public void resize() {
        width=Block.widthProportion;
        height=Block.heightProportion;
        imgBonus = setImage(context,R.mipmap.bombe_or,width,height);
    }

    public void draw(Canvas c){
        if (imgBonus==null) return;
        Paint p = new Paint();
        c.drawBitmap(imgBonus.getBitmap(),(int)x,(int)y,p);
    }

    public BitmapDrawable setImage(final Context c, final int ressource, final int w, final int h)
    {
        Drawable dr = ResourcesCompat.getDrawable(c.getResources(), ressource, null);
        Bitmap bitmap = ((BitmapDrawable) dr).getBitmap();
        return new BitmapDrawable(c.getResources(), Bitmap.createScaledBitmap(bitmap, w, h, true));
    }
}
