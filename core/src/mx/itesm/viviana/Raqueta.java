package mx.itesm.viviana;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Raqueta extends Objeto {
    public Raqueta(Texture textura, float x, float y){
        super(textura, x, y);
    }

    public void dibujar(SpriteBatch batch){
        sprite.draw(batch);
    }

    public void seguirPelota(Pelota pelota){
        sprite.setY(pelota.sprite.getY()-sprite.getHeight()/2+pelota.sprite.getHeight()/2);
    }

}
