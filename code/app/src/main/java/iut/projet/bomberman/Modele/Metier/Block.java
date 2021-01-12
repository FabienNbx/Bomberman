package iut.projet.bomberman.Modele.Metier;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Picture;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.content.res.ResourcesCompat;

import iut.projet.bomberman.GameActivity;
import iut.projet.bomberman.R;

public class Block {
    public final static  int widthProportion = GameActivity.tailleEcranX/15;
    public final static  int heightProportion = GameActivity.tailleEcranY/9;
    private double x;
    private double y;
    protected int height;
    protected int width;
    protected Context context;
    protected BitmapDrawable imgBlock=null;

    public BitmapDrawable getImgBlock(){
        return imgBlock;
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

    public Block(Context c,double x, double y){
        context=c;
        setX(x);
        setY(y);
    }

    public void resize() {
        width=widthProportion;
        height=heightProportion;
        imgBlock = setImage(context, R.mipmap.bloc_incassable,width,height);
    }

    public void draw(Canvas c){
        if (imgBlock==null) return;
        Paint p = new Paint();
        c.drawBitmap(imgBlock.getBitmap(),(int)x,(int)y,p);
    }

    public BitmapDrawable setImage(final Context c, final int ressource, final int w, final int h)
    {
        Drawable dr = ResourcesCompat.getDrawable(c.getResources(), ressource, null);//c.getResources().getDrawable(ressource);
        Bitmap bitmap = ((BitmapDrawable) dr).getBitmap();
        return new BitmapDrawable(c.getResources(), Bitmap.createScaledBitmap(bitmap, w, h, true));
    }
}
