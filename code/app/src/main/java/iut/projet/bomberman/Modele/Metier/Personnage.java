package iut.projet.bomberman.Modele.Metier;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.content.res.ResourcesCompat;

import java.util.ArrayList;
import java.util.List;

import iut.projet.bomberman.GameActivity;
import iut.projet.bomberman.R;

public class Personnage { // pas le droit de mettre d'ImageView dans le model
    private double posX;
    private double posY;
    private int height;
    private int width;
    private int health;
    private Context context;
    private BitmapDrawable imgPerso=null;
    private int vitesseDeplacement;
    private List<Bonus> bonuses;

    public BitmapDrawable getImgPerso(){
        return imgPerso;
    }

    public Context getContext(){
        return context;
    }

    public double getPosX() {
        return posX;
    }

    public void setPosX(double posX) {
        this.posX = posX;
    }

    public double getPosY() {
        return posY;
    }

    public void setPosY(double posY) {
        this.posY = posY;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getVitesseDeplacement() {
        return vitesseDeplacement;
    }

    public void setVitesseDeplacement(int vitesseDeplacement) {
        this.vitesseDeplacement = vitesseDeplacement;
    }

    public Personnage(Context c){
        this(Block.widthProportion,Block.heightProportion,15, 3);
        context=c;
    }

    private Personnage(double posX, double posY, int vitesseDeplacement, int health){
        this.posX=posX;
        this.posY=posY;
        this.vitesseDeplacement=vitesseDeplacement;
        bonuses = new ArrayList<Bonus>();
    }

    public BitmapDrawable setImage(final Context c, final int ressource, final int w, final int h)
    {
        Drawable dr = ResourcesCompat.getDrawable(c.getResources(), ressource, null);
        Bitmap bitmap = ((BitmapDrawable) dr).getBitmap();
        return new BitmapDrawable(c.getResources(), Bitmap.createScaledBitmap(bitmap, w, h, true));
    }

    public void resize() {
        width= GameActivity.tailleEcranX /20;
        height=GameActivity.tailleEcranY/13;
        if(GameActivity.choix==0)
            imgPerso = setImage(context,R.mipmap.bomberman,width,height);
        else
            imgPerso = setImage(context,R.mipmap.bomberman_vert,width,height);
    }

    public void draw(Canvas c){
        if (imgPerso==null) return;
        Paint p = new Paint();
        c.drawBitmap(imgPerso.getBitmap(),(int)posX,(int)posY,p);
    }

    public void moveRight(){
        posX+=vitesseDeplacement;
    }
    public void moveLeft(){
        posX-=vitesseDeplacement;
    }
    public void moveUp(){
        posY-=vitesseDeplacement;
    }
    public void moveDown(){
        posY+=vitesseDeplacement;
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

}
