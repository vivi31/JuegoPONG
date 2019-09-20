package mx.itesm.viviana;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class Objeto {
    //Sprites que representa graficamente el objeto
    protected Sprite sprite;

    public Objeto(Texture textura, float x, float y){
        sprite = new Sprite(textura);
        sprite.setPosition(x,y);
    }
}
