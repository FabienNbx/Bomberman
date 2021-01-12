package iut.projet.bomberman.Controllers;

import android.os.CountDownTimer;
import android.util.Log;

public class GameLoop extends Thread { //implementation de la boucle de jeu
    private boolean isRunning = false;
    private GameManager gameLoopManager;
    private CountDownTimer timer;

    public GameLoop(GameManager gm){
        gameLoopManager = gm;
        timer=new CountDownTimer(60000,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                gameLoopManager.afficherTimer(millisUntilFinished/1000);
            }

            @Override
            public void onFinish() {
                gameLoopManager.finTimer();
            }
        }.start();
    }

    public void setRunning(boolean run) {
        isRunning = run;
    }

    public void stopTimer(){
        timer.cancel();
    }

    @Override
    public void run(){
        while(isRunning){
            //lancer la boucle de jeu
            //mise à jour de la position du personnage
            beep();
            try{
                Thread.sleep(25);
            }
            catch (Exception e){ }
        }
    }

    private void beep(){
        gameLoopManager.update();
    }
}
