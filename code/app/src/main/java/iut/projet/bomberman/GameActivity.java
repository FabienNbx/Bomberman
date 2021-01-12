package iut.projet.bomberman;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Point;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;

import iut.projet.bomberman.Controllers.GameListener;
import iut.projet.bomberman.Controllers.GameManager;

public class GameActivity extends AppCompatActivity implements GameListener {
    private GameManager gameManager;
    public static int tailleEcranX;
    public static int tailleEcranY;
    public static int rotation;
    public static int choix;
    private Display d;
    private Point p;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT < 16) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
        else{
            View decorView = getWindow().getDecorView();
            int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
            decorView.setSystemUiVisibility(uiOptions);
            getSupportActionBar().hide();
        }
        setContentView(R.layout.jeu);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        getScreen();
        Intent intent = getIntent();
        String c = intent.getStringExtra("CHOIX");
        choix=Integer.parseInt(c);
        gameManager = new GameManager(this);
    }

    public void getScreen(){
        d= getWindowManager().getDefaultDisplay();
        p = new Point();
        d.getSize(p);
        rotation=d.getRotation();
        tailleEcranX=p.x;
        tailleEcranY=p.y;
    }


    @Override
    protected void onStart(){
        super.onStart();
        gameManager.start();
    }

    @Override
    protected void onStop(){
        gameManager.stop();
        super.onStop();
    }

    @Override
    public void onBackPressed(){
        gameManager.stop();
        this.finish();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    public void onDestroy(){
        gameManager.stop();
        super.onDestroy();
    }

    @Override
    public void onGameOver() {
        gameManager.stop();
        this.finish();
        Intent intent = new Intent(this, GameOver.class);
        startActivity(intent);
    }

    @Override
    public void onWin() {
        gameManager.stop();
        this.finish();
        Intent intent = new Intent(this, Win.class);
        startActivity(intent);
    }
}
