package iut.projet.bomberman;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity {

    private static final String PREFS_NAME = "MyPrefsFile" ;
    private int choix = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if(savedInstanceState!=null){
            SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
            choix = settings.getInt("choix", choix);
            ImageButton bomberman = (ImageButton)findViewById(R.id.bomberman);
            ImageButton bombermanVert = (ImageButton)findViewById(R.id.bombermanVert);
            if(choix==0){
                bomberman.setBackgroundColor(Color.RED);
                bombermanVert.setBackgroundColor(Color.LTGRAY);
            }
            else if(choix==1){
                bombermanVert.setBackgroundColor(Color.RED);
                bomberman.setBackgroundColor(Color.LTGRAY);
            }
        }
    }


    @Override
    protected void onStart(){
        super.onStart();
    }

    @Override
    protected void onStop(){
        super.onStop();
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putInt("choix", choix);
        editor.commit();
    }


    @Override
    protected void onDestroy(){
        super.onDestroy();
    }

    @Override
    protected void onResume(){
        super.onResume();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle inState){
        super.onRestoreInstanceState(inState);
    }

    public void onClickStart(View view) {
        if(choix==-1){
            choix=0;
        }
        Intent intent = new Intent(this, GameActivity.class);
        String message = Integer.toString(choix);
        intent.putExtra("CHOIX", message);
        startActivity(intent);
    }

    public void clickbomberman(View view) {
        choix=0;
        ImageButton bomberman = (ImageButton)findViewById(R.id.bomberman);
        ImageButton bombermanVert = (ImageButton)findViewById(R.id.bombermanVert);
        bomberman.setBackgroundColor(Color.RED);
        bombermanVert.setBackgroundColor(Color.LTGRAY);
    }

    public void clickBombermanVert(View view) {
        choix=1;
        ImageButton bomberman = (ImageButton)findViewById(R.id.bomberman);
        ImageButton bombermanVert = (ImageButton)findViewById(R.id.bombermanVert);
        bombermanVert.setBackgroundColor(Color.RED);
        bomberman.setBackgroundColor(Color.LTGRAY);
    }

    public void onReglesClick(View view) {
        startActivity(new Intent(this,PopRegles.class));
    }
}
