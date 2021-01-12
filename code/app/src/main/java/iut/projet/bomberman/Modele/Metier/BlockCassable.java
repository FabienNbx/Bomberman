package iut.projet.bomberman.Modele.Metier;

import android.content.Context;

import iut.projet.bomberman.R;

public class BlockCassable extends Block {

    public BlockCassable(Context c, double x, double y){
        super(c,x,y);
    }

    @Override
    public void resize(){
        super.width=widthProportion;
        super.height=heightProportion;
        imgBlock = setImage(context, R.mipmap.bloc_cassable,width,height);
    }
}
