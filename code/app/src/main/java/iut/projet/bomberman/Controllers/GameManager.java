package iut.projet.bomberman.Controllers;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Rect;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.util.Log;
import android.view.Surface;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import iut.projet.bomberman.GameActivity;
import iut.projet.bomberman.Modele.Metier.Block;
import iut.projet.bomberman.Modele.Metier.BlockCassable;
import iut.projet.bomberman.Modele.Metier.Bombe;
import iut.projet.bomberman.Modele.Metier.Bonus;
import iut.projet.bomberman.Modele.Metier.Personnage;
import iut.projet.bomberman.Modele.Metier.RectExplosion;
import iut.projet.bomberman.R;
import iut.projet.bomberman.View.GameView;

import static java.lang.Math.abs;


/*
29 mars à 23h59, dernier commit
le code
l'APK
les preuves (des critères remplis) dans un pdf
appli publiée dans le playstore
indiquer notre % de travail
pas le buid

 */


public class GameManager implements OnSensorValueChangedListener {

    private Personnage personnage;
    private SensorManager mSensorManager;
    private Sensor mGyroSensor;
    private SensorController sensorController;
    private GameView gv;
    private GameLoop gameLoop;
    private float inclinaisonX;
    private float inclinaisonY;
    private GameActivity ga;
    private ArrayList<Block> blockList;  //Pour connaitre tous les blocs
    private ArrayList<BlockCassable> blockCassableList;
    private ArrayList<Bombe> bombeList;
    private final static int UP = 0;
    private final static int DOWN = 1;
    private final static int RIGHT = 2;
    private final static int LEFT = 3;
    private ArrayList<RectExplosion> listeRectExplosion;
    private Bonus b;
    private BlockCassable blockBonus;
    int win = 0;
    private long time;

    public GameActivity getGa() {
        return ga;
    }

    public GameManager(GameActivity ga){
        this.ga=ga;
        mSensorManager = (SensorManager) ga.getSystemService(Context.SENSOR_SERVICE);
        mGyroSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY);
        sensorController = new SensorController(this);
        gyroscopeEventListener();
        gameLoop = new GameLoop(this);
        blockList=new ArrayList<Block>();
        bombeList=new ArrayList<Bombe>();
        blockCassableList=new ArrayList<BlockCassable>();
        listeRectExplosion=new ArrayList<RectExplosion>();
        creationMap();
        personnage = new Personnage((Context) ga);
        personnage.resize();
        gv=new GameView((Context) ga,this);
        RelativeLayout layout = ga.findViewById(R.id.gridlayout);
        layout.addView(gv);
    }

    public ArrayList<BlockCassable> getBlockCassableList() {
        return blockCassableList;
    }

    public ArrayList<RectExplosion> getListeRectExplosion() {
        return listeRectExplosion;
    }

    public Personnage getPersonnage(){
        return personnage;
    }

    public void setPersonnage(Personnage personnage) {
        this.personnage = personnage;
    }

    public void setBlockList(ArrayList<Block> blockList) {
        this.blockList = blockList;
    }

    public ArrayList<Block> getBlockList() {
        return blockList;
    }

    public void start(){
        mSensorManager.registerListener(sensorController, mGyroSensor, SensorManager.SENSOR_DELAY_FASTEST); //remplacer le register par sensorControlleur
        //création du processus GameLoopThread si cela n'est pas fait
        if(gameLoop.getState()==Thread.State.TERMINATED) {
           gameLoop=new GameLoop(this);
        }
        gameLoop.setRunning(true);
        gameLoop.start();
    }

    @SuppressLint("SetTextI18n")
    public void afficherTimer(long time){
        this.time=time;
        TextView tps =  ga.findViewById(R.id.timeView);
        tps.setPadding(20,0,0,0);
        tps.setText(Long.toString(time));
    }

    public void finTimer(){
        ga.onGameOver();
    }

    private void creationMap() {
        int[][] gameMap = {{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
                           {1,0,0,2,0,2,0,0,0,2,0,2,0,0,1},
                           {1,0,1,2,1,2,1,0,1,0,1,2,1,0,1},
                           {1,2,2,2,0,0,0,2,0,2,0,2,2,2,1},
                           {1,0,1,0,1,2,1,0,1,0,1,0,1,0,1},
                           {1,2,2,2,0,0,2,0,0,0,0,2,2,2,1},
                           {1,0,1,2,1,2,1,0,1,2,1,2,1,0,1},
                           {1,0,0,2,0,0,2,0,2,0,0,2,0,0,1},
                           {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1}};
        for(int i=0;i<gameMap.length;i++){
            for(int j=0;j<gameMap[i].length;j++){
                if(gameMap[i][j]==1){
                    //créer un mur
                    Block b = new Block((Context) ga,j+Block.widthProportion*j,i+Block.heightProportion*i);
                    b.resize();
                    blockList.add(b);
                }
                else if(gameMap[i][j]==2){
                    BlockCassable b = new BlockCassable((Context) ga,j+Block.widthProportion*j,i+Block.heightProportion*i);
                    b.resize();
                    blockCassableList.add(b);
                }
            }
        }
        int rand =(int)(Math.random() * (blockCassableList.size()+ 1)); //nombre aléatoire entre 0 et le nombre de blocs total
        blockBonus = blockCassableList.get(rand);
        b = new Bonus(ga,blockCassableList.get(rand).getX(),blockCassableList.get(rand).getY());
        b.resize();
    }

    public void stop(){
        boolean retry = true;
        gameLoop.stopTimer();
        gameLoop.setRunning(false);
        while (retry) {
            try {
                gameLoop.join();
                retry = false;
            }
            catch (InterruptedException ignored) {}
        }
        mSensorManager.unregisterListener(sensorController);
    }

    protected void gyroscopeEventListener() { //A mettre dans une class Sensor Controlleur
        new SensorController(this);
    }


    @Override
    public void onSensorValueChanged(float x, float y) {
        inclinaisonX = x;
        inclinaisonY = y;
    }

    public void update(){
        ga.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                gestionVieBombes();
                gestionVieExplosion();
                gestionDeplacement();
                gv.move();
            }
        });
    }

    public void gestionVieExplosion(){
        for (int i=0;i<listeRectExplosion.size();i++) {
            RectExplosion rectExplosion = listeRectExplosion.get(i);
            rectExplosion.setNblife(rectExplosion.getNblife()-1);
            if(rectExplosion.getNblife()<=0){
                listeRectExplosion.remove(rectExplosion);
            }
        }
    }

    public void gestionVieBombes(){
        for (int i=0;i<bombeList.size();i++) {
            Bombe b = bombeList.get(i);
            b.setDureeDeVie(b.getDureeDeVie()-1);
            if(b.getDureeDeVie()<=0){
                explosionSimple(b);
                bombeList.remove(b);
            }
        }
    }

    public boolean verificationPasDeBlockIncassable(Rect rverif) {
        for (Block blinc:blockList) {
            Rect rb = new Rect((int) blinc.getX(),(int) blinc.getY(),(int) blinc.getX()+blinc.getWidth(),(int) blinc.getY()+blinc.getHeight());
            if(rverif.intersect(rb)){
                return false;
            }
        }
        return true;
    }
// 1 a droite = ok
    // 0 droite et gauche ok
    // 1 bas --> bas ok mais bas gauche non
    //1gauche ok
    //1 haut ok
    public void exploser(Bombe b){  //BEUGSS
        //Point centre = new Point((int)(b.getX()+b.getWidth())/2,(int)(b.getY()+b.getHeight())/2);
        Rect rbombehaut = new Rect((int)b.getX(),(int)b.getY()-3*Block.heightProportion,(int)b.getX()+b.getWidth(),(int)b.getY());

        Rect rbombebas = new Rect((int)b.getX(),(int)b.getY()/*+b.getHeight()*/,(int)b.getX()+b.getWidth(),(int)b.getY()/*+b.getHeight()*/+3*Block.heightProportion);

        Rect rbombedroit = new Rect((int)b.getX()+b.getWidth(),(int)b.getY(),(int)b.getX()+b.getWidth()+3*Block.widthProportion,(int)b.getY()+b.getHeight());

        Rect rbombegauche = new Rect((int)b.getX()-3*Block.widthProportion,(int)b.getY(),(int)b.getX(),(int)b.getY()+b.getHeight());

        for (int i=0;i<blockCassableList.size();i++) {

            BlockCassable bl = blockCassableList.get(i);

            Rect rblock = new Rect((int) b.getX(),(int) b.getY(),(int) b.getX()+b.getWidth(),(int) b.getY()+b.getHeight());

            if(rbombehaut.intersect(rblock)){
                Log.d("EXPLOSION","haut");
                Rect rverif = new Rect((int)b.getX(),(int)bl.getY()+bl.getHeight(),(int)b.getX()+b.getWidth(),(int)b.getY());
                if(verificationPasDeBlockIncassable(rverif)) {
                    blockCassableList.remove(bl);
                }
            }
            else if(rbombebas.intersect(rblock)){
                Log.d("EXPLOSION","bas");
                Rect rverif = new Rect((int)b.getX(),(int)b.getY()+b.getHeight(),(int)b.getX()+b.getWidth(),(int)bl.getY());
                if(verificationPasDeBlockIncassable(rverif)) {
                    blockCassableList.remove(bl);
                }
            }
            else if(rbombedroit.intersect(rblock)){
                Log.d("EXPLOSION","droite");
                Rect rverif = new Rect((int)b.getX()+b.getWidth(),(int)b.getY(),(int)bl.getX(),(int)b.getY()+b.getHeight());
                if(verificationPasDeBlockIncassable(rverif)) {
                    blockCassableList.remove(bl);
                }
            }
            else if(rbombegauche.intersect(rblock)){
                Log.d("EXPLOSION","gauche");
                Rect rverif = new Rect((int)bl.getX()+bl.getWidth(),(int)b.getY(),(int)b.getX(),(int)b.getY()+b.getHeight());
                if(verificationPasDeBlockIncassable(rverif)) {
                    blockCassableList.remove(bl);
                }
            }
        }

    }

    public void explosionSimple(Bombe bombe){
        //Point centre = new Point((int)(b.getX()+b.getWidth())/2,(int)(b.getY()+b.getHeight())/2);
        Rect rBombe = new Rect((int)bombe.getX()-Block.widthProportion,(int)bombe.getY()-Block.heightProportion,(int)bombe.getX()+bombe.getWidth()+Block.widthProportion,(int)bombe.getY()+bombe.getHeight()+Block.heightProportion);
        listeRectExplosion.add(new RectExplosion(ga,rBombe));
        Rect rPerso = new Rect((int) personnage.getPosX(),(int) personnage.getPosY(),(int) personnage.getPosX()+personnage.getWidth(),(int) personnage.getPosY()+personnage.getHeight());
        if(rPerso.intersect(rBombe)) { // verification mort du perso
            double verify = abs(bombe.getY() - personnage.getPosY());
            if (verify > Block.heightProportion && (bombe.getX() + bombe.getWidth() - Block.widthProportion) > personnage.getPosX()) { //coin haut gauche
            } else if (verify > Block.heightProportion - bombe.getHeight() / 2 && bombe.getX() + bombe.getWidth() / 2 < personnage.getPosX()) { //coin haut droite
            } else if (bombe.getY() + bombe.getHeight() / 2 <= personnage.getPosY() && (bombe.getX() + bombe.getWidth() - Block.widthProportion) > personnage.getPosX()) { //coin bas gauche
            } else if (bombe.getY() + bombe.getHeight() / 2 <= personnage.getPosY() && bombe.getX() + bombe.getWidth() / 2 < personnage.getPosX()) { //coin bas droite
            }
            else {
                ga.onGameOver();
            }
        }

        for (int i=0;i<blockCassableList.size();i++) { //verification destruction des blocs

            BlockCassable bl = blockCassableList.get(i);

            Rect rblock = new Rect((int) bl.getX(),(int) bl.getY(),(int) bl.getX()+bl.getWidth(),(int) bl.getY()+bl.getHeight());

            if(rblock.intersect(rBombe)){
                double verify = abs( bombe.getY()-bl.getY());
                if(verify>Block.heightProportion && (bombe.getX()+bombe.getWidth()-Block.widthProportion)>bl.getX()){ //coin haut gauche

                }
                else if(verify>Block.heightProportion-bombe.getHeight()/2 && bombe.getX()+bombe.getWidth()/2<bl.getX()){ //coin haut droite
                }
                else if(bombe.getY()+bombe.getHeight()/2<=bl.getY() && (bombe.getX()+bombe.getWidth()-Block.widthProportion)>bl.getX()){ //coin bas gauche
                }
                else if(bombe.getY()+bombe.getHeight()/2<=bl.getY() && bombe.getX()+bombe.getWidth()/2<bl.getX()){ //coin bas droite
                }
                else{
                    blockCassableList.remove(bl);
                    if (bl==blockBonus){
                        win=1;
                    }
                }
            }
        }
    }

    public void gestionDeplacement(){
        if(playerCollision(0)==2){
            ga.onWin();
        }
        if (GameActivity.rotation == Surface.ROTATION_0) {
            float tmp = -inclinaisonX;
            inclinaisonX=inclinaisonY;
            inclinaisonY=tmp;
        }
        //if((int)inclinaisonX>= 1  && inclinaisonY<tailleEcranY){   //descendre
        if((int)inclinaisonX>= 1  && personnage.getPosY()+personnage.getHeight()<GameActivity.tailleEcranY){
            personnage.moveDown();
            if(playerCollision(DOWN)==1){ //Vérification des collisions avant d'update la vue
                personnage.moveUp();
            }
        }
        //if((int)inclinaisonX<= -1 && inclinaisonY>0){ //monter
        if((int)inclinaisonX<= -1 && personnage.getPosY()>0){ //monter
            personnage.moveUp();
            if(playerCollision(UP)==1){
                personnage.moveDown();
            }
        }

        //if((int)inclinaisonY<=-1 && inclinaisonX>0){ //gauche
        if((int)inclinaisonY<=-1 && personnage.getPosX()>0){ //gauche
            personnage.moveLeft();
            if(playerCollision(LEFT)==1){
                personnage.moveRight();
            }
        }
        //if((int)inclinaisonY>=1 && inclinaisonX<tailleEcranX ){ //droite
        if((int)inclinaisonY>=1 && personnage.getPosX()+personnage.getWidth()<GameActivity.tailleEcranX ) { //droite
            personnage.moveRight();
            if (playerCollision(RIGHT)==1) {
                personnage.moveLeft();
            }
        }
    }



    public int playerCollision(int direction){
        Rect rp = new Rect((int) personnage.getPosX(),(int) personnage.getPosY(),(int) personnage.getPosX()+personnage.getWidth(),(int) personnage.getPosY()+personnage.getHeight());

        for (Block b : blockList) {
            Rect rb = new Rect((int) b.getX(),(int) b.getY(),(int) b.getX()+b.getWidth(),(int) b.getY()+b.getHeight());
            if(rp.intersect(rb)){
                return 1;
            }
            //Faire les collision avec les murs lorsque la vue ne depend plus du model
        }
        for (Block b : blockCassableList) {
            Rect rb = new Rect((int) b.getX(),(int) b.getY(),(int) b.getX()+b.getWidth(),(int) b.getY()+b.getHeight());
            if(rp.intersect(rb)){
                return 1;
            }
            //Faire les collision avec les murs lorsque la vue ne depend plus du model
        }
        if(win==1){
            Rect rb = new Rect((int) this.b.getX(),(int) this.b.getY(),(int) this.b.getX()+this.b.getWidth(),(int) this.b.getY()+this.b.getHeight());
            if(rp.intersect(rb)){
                return 2;
            }
        }
        return 0;
    }

    public ArrayList<Bombe> getBombeList() {
        return bombeList;
    }

    public void gameOver(){
        //terminer Activite ici grace a la l'interface GameListener
    }

    public void setBombeList(ArrayList<Bombe> bombeList) {
        this.bombeList = bombeList;
    }


    public Bonus getB() {
        return b;
    }

    public void setB(Bonus b) {
        this.b = b;
    }

    public long getTime() {
        return time;
    }
}
