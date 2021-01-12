package iut.projet.bomberman;

import android.app.Activity;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;



public class PopRegles extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_pop_regles);
        Display d= getWindowManager().getDefaultDisplay();
        Point p = new Point();
        d.getSize(p);
        int ecranX=p.x;
        int ecranY=p.y;
        getWindow().setLayout((int) (ecranX*0.9),(int) (ecranY*0.5));
    }

}
