package mx.itesm.viviana;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Pelota extends Objeto {
    //Desplazamiento
    private float DX = 4;
    private float DY = 4;

    public Pelota(Texture textura, float x, float y){
        super(textura, x,y);
    }
    //Se dibuja el objeto pelota
    public void dibujar(SpriteBatch batch){
        sprite.draw(batch);
    }
     //Actualiza la posicion de la pelota
    public void mover(Raqueta raqueta){
        float xp = sprite.getX();
        float yp = sprite.getY();

        //Limites derecha iz
        if(xp >= PantallaJuego.ANCHO-sprite.getWidth()){
            DX =-DX;
        }

        //colision
        float xr = raqueta.sprite.getX();
        float yr = raqueta.sprite.getY();
        if(xp>= xr && xp<=xr+raqueta.sprite.getWidth()&& yp>=yr && yp <= (yr+raqueta.sprite.getHeight())){
            DX = -DX;
        }

        if(yp>= PantallaJuego.ALTO-sprite.getHeight()|| yp<=0){
            DY=-DY;
        }
        //Mueve pelota a la nueva posicion
        sprite.setPosition(xp+ DX,yp+DY);
    }
}
