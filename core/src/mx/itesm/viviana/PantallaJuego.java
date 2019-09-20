package mx.itesm.viviana;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.ui.TextTooltip;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;


class PantallaJuego implements Screen {

    private final Pong pong;
    //Tamaño del mundo
    public static final float ANCHO = 1200;
    public static final float ALTO =  800;

    //Cámara
    private OrthographicCamera camara;
    private Viewport vista;

    //Administrador de trazos sobre la pantalla
    private SpriteBatch batch;

    //Texturas
    private Texture texturaBarraHorisontal;
    private Texture texturaRaqueta;
    private Texture texturaCuadro;

    //objetos
    private Pelota pelota;
    private Raqueta raquetaComputdora;
    private Raqueta raquetaJugador;

    //Marcador
    private int puntosJugador = 0;
    private int puntosMaquina = 0;
    private Texto texto; //Muestra los calores en la pantalla

    //Estado  del juegoo
    private Estado estado = Estado.JUGANDO;


    public PantallaJuego(Pong pong) {
        this.pong = pong;
    }

    @Override
    public void show() {
        //crear la camara
        camara= new OrthographicCamera(ANCHO,ALTO);
        camara.position.set(ANCHO/2,ALTO/2,0);
        camara.update();

        vista = new StretchViewport(ANCHO, ALTO, camara);
        batch = new SpriteBatch();

        //cargar texturas
        cargarTexturass();
        //Crear todos los objetos
        crearObjetos();
        //Indica quien escucha y atiende los eventos
        Gdx.input.setInputProcessor(new ProcesadorEntrada());

    }

    private void crearObjetos() {
        pelota = new Pelota(texturaCuadro,ANCHO/2,ALTO/2);
        raquetaComputdora = new Raqueta(texturaRaqueta, ANCHO-texturaRaqueta.getWidth(),ALTO/2);
        raquetaJugador = new Raqueta(texturaRaqueta, 0,ALTO/2);

        //Objetos que dibuja texto
        texto = new Texto();
    }

    private void cargarTexturass() {
        texturaBarraHorisontal = new Texture("barraHorizontal.png");
        texturaCuadro = new Texture("cuadrito.png");
        texturaRaqueta = new Texture("raqueta.png");
    }

    @Override
    public void render(float delta) {
        //Actualizar objetos de la pantalla
        actualizarObjetos();

        if(estado == Estado.JUGANDO && pelota.sprite.getX()<0){
            puntosMaquina++;
            if(puntosMaquina>=5){
                //Pierde el jugador
                estado = Estado.PIERDE;
            }
            //Teinicie la partida
            pelota.sprite.setPosition(ANCHO/2,ALTO/2);
        }
        raquetaComputdora.seguirPelota(pelota);

        // borrar pantalla
        Gdx.gl.glClearColor(0,0,0,0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.setProjectionMatrix(camara.combined);

        batch.begin();
        batch.draw(texturaBarraHorisontal, 0,0);
        batch.draw(texturaBarraHorisontal, 0, ALTO -texturaBarraHorisontal.getHeight());
    //linea central
        for(float y = 0 ; y<ALTO; y+= 2*texturaCuadro.getHeight()) {
            batch.draw(texturaCuadro , ANCHO/2 , y);
        }
    //raquetas
       // batch.draw(texturaRaqueta, 0 , ALTO/2);
        //batch.draw(texturaRaqueta, ANCHO - texturaRaqueta.getWidth() , ALTO/2);
        raquetaJugador.dibujar(batch);
        raquetaComputdora.dibujar(batch);


        //Dibujar la pelota dentro de batch
        pelota.dibujar(batch);


        //Dibujar el marcador
        texto.mostrarMensaje(batch, Integer.toString(puntosJugador), ANCHO/2-ANCHO/6, 3*ALTO/4);
        texto.mostrarMensaje(batch,Integer.toString(puntosMaquina), ANCHO/2+ANCHO/6, 3*ALTO/4);

        //Pierdemostrar mensaje
        if(estado == Estado.PIERDE){
            texto.mostrarMensaje(batch, "Lo siento, perdiste :(", ANCHO / 2 - ANCHO / 6, 3 * ALTO / 4);
            texto.mostrarMensaje(batch, "Tap para continuar", ANCHO / 2 + ANCHO / 6, 3 * ALTO / 6);

        }

        batch.end();

    }

    private void actualizarObjetos() {
        if(estado == Estado.JUGANDO) {
            pelota.mover(raquetaJugador);
        }
    }

    @Override
    public void resize(int width, int height) {
        vista.update(width, height);


    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }

    //  Escucha y atiende eventos de touch
    class ProcesadorEntrada implements InputProcessor{

        @Override
        public boolean keyDown(int keycode) {
            return false;
        }

        @Override
        public boolean keyUp(int keycode) {
            return false;
        }

        @Override
        public boolean keyTyped(char character) {
            return false;
        }

        @Override
        public boolean touchDown(int screenX, int screenY, int pointer, int button) {
            if(estado == Estado.PIERDE){
                puntosJugador = 0;
                puntosMaquina = 0;
                pelota.sprite.setPosition(ANCHO/2,ALTO/2);
                estado = Estado.JUGANDO;

            }
            return true;
        }

        @Override
        public boolean touchUp(int screenX, int screenY, int pointer, int button) {
            return false;
        }

        @Override
        public boolean touchDragged(int screenX, int screenY, int pointer) {
            Vector3 v = new Vector3(screenX,screenY,0);
            camara.unproject(v);
            raquetaJugador.sprite.setY(v.y);
            return true;
        }

        @Override
        public boolean mouseMoved(int screenX, int screenY) {
            return false;
        }

        @Override
        public boolean scrolled(int amount) {
            return false;
        }
    }
}
