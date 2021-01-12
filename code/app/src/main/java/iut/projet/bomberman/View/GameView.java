package iut.projet.bomberman.View;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import iut.projet.bomberman.Controllers.GameManager;
import iut.projet.bomberman.GameActivity;
import iut.projet.bomberman.Modele.Metier.Block;
import iut.projet.bomberman.Modele.Metier.Bombe;
import iut.projet.bomberman.Modele.Metier.RectExplosion;
import iut.projet.bomberman.R;

public class GameView extends View{//SurfaceView implements SurfaceHolder.Callback {

    private GameManager gm;

    public GameView(final Context context, GameManager g){
        super(context);
        gm=g;
        this.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) { //DEBUT POSAGE DE BOMBE
                performClick();
                Bombe bombe = new Bombe(getContext(),gm.getPersonnage().getPosX(),gm.getPersonnage().getPosY());
                bombe.resize();
                gm.getBombeList().add(bombe);
                return false;
            }
        });
    }

    @Override
    public boolean performClick() {
        return super.performClick();
    }

    public void affichageMap() {
        postInvalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint p = new Paint();
        p.setColor(Color.WHITE);
        canvas.drawRect(new Rect(0, 0, GameActivity.tailleEcranX, GameActivity.tailleEcranY), p);
        gm.getPersonnage().draw(canvas);
        gm.getB().draw(canvas);
        for (Block b : gm.getBlockList()) {
            b.draw(canvas);
        }
        for (Block b : gm.getBlockCassableList()) {
            b.draw(canvas);
        }
        for (Bombe b : gm.getBombeList()) {
            b.draw(canvas);
        }
        for (RectExplosion r : gm.getListeRectExplosion()) {
            p.setColor(Color.RED);
            canvas.drawRect(r.getR(), p);
        }
        TextView tx = gm.getGa().findViewById(R.id.timeView);
        tx.draw(canvas);
    }

    public void move(){
        affichageMap();
    }


}
