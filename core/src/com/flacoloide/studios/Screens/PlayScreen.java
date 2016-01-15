package com.flacoloide.studios.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.flacoloide.studios.MegaCatastrofeNuclear;
import com.flacoloide.studios.Scenes.Hud;
import com.flacoloide.studios.Sprites.Enemies.Enemy;
import com.flacoloide.studios.Sprites.Enemies.Gato;
import com.flacoloide.studios.Sprites.Items.Objeto;
import com.flacoloide.studios.Personajes.Personaje;
import com.flacoloide.studios.Tools.B2WorldCreator;
import com.flacoloide.studios.Tools.WorldContactListener;

import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by Coke on 20-11-2015.
 */
public class PlayScreen implements Screen{
    public static MegaCatastrofeNuclear game;
    private TextureAtlas atlas;
    private float opx;
    private WorldContactListener contact;
    public static boolean murio;
    public static boolean nextLevel;
    public Objeto item;
    public static float velocidad;
    
    //Camara
    public  OrthographicCamera gamecam;
    private Viewport gamePort;
    public static Hud hud;

    //Tiled Map Varibles
    private TmxMapLoader maploader;
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;

    //Box2D Variables
    private World world;
    private Box2DDebugRenderer b2dr;
    private B2WorldCreator creator;

    //sprites
    public static Personaje player;
    private Music music;
    private Sound soundObjeto;
    private Sound gatoSound;
    private static int contLevel=1;
    private static int esperar=0;
    public static int cont=1;
    public static boolean presiono;
    private Animation anim;




    public PlayScreen(MegaCatastrofeNuclear game, String level){
        atlas = new TextureAtlas("Mario_and_Enemies.pack");
        this.game = game;
        //creado para seguir al personaje al rededor del mundo
        gamecam = new OrthographicCamera();
        esperar = 0;
        //crea FitViewport para mantener la relacion de aspecto
        gamePort = new FitViewport(MegaCatastrofeNuclear.V_WIDTH / MegaCatastrofeNuclear.PPM , MegaCatastrofeNuclear.V_HEIGHT / MegaCatastrofeNuclear.PPM ,gamecam);

        //crea un hud  de score/tiempo/level info

        hud = new Hud(game.batch);


        //Carga el mapa y inicializa nuestro render del mapa
        maploader = new TmxMapLoader();
        map = maploader.load(level);
        renderer = new OrthogonalTiledMapRenderer(map, 1 / MegaCatastrofeNuclear.PPM);


        //Inicializa la gamecam en el centro del mapa
        gamecam.position.set(gamePort.getWorldWidth() / 2, gamePort.getWorldHeight() / 2, 0);

        world = new World(new Vector2(0,-18), true);
        b2dr = new Box2DDebugRenderer();

        creator = new B2WorldCreator(this);

        //crea al payer en el mundo
        player = new Personaje(this);
        world.setContactListener(new WorldContactListener());

        murio=false;

        music = MegaCatastrofeNuclear.manager.get("audio/music/mario_music.ogg", Music.class);
        music.setLooping(true);
        

        gatoSound = MegaCatastrofeNuclear.manager.get("audio/sounds/gato.mp3", Sound.class);
    }




    public TextureAtlas getAtlas(){
        return atlas;
    }

    @Override
    public void show() {

    }
    public void handleInput(float dt){

        if (Gdx.input.isKeyJustPressed(Input.Keys.W) && player.b2body.getLinearVelocity().y == 0 && !murio|| Gdx.input.isTouched() && player.b2body.getLinearVelocity().y == 0 && !murio) {
            player.b2body.applyLinearImpulse(new Vector2(0, 5.5f), player.b2body.getWorldCenter(), true);
        }
        if(Gdx.input.isKeyPressed(Input.Keys.P)) {
            game.setScreen(new PlayScreen(game, "Level1/Level2.tmx"));
        }
        if(Gdx.input.isButtonPressed(Input.Buttons.BACK)){
            System.exit(0);
        }
    }
    public void update(float dt){




        handleInput(dt);

        //takes 1 step in physics simulator(60 times per second)
        world.step(1 / 60f, 6, 2);



        player.update(dt);

     for (Enemy enemy : creator.getObjetos()) {
            enemy.update(dt);
            enemy.b2body.setActive(true);
        }
     for (Enemy enemy : creator.getGatos()) {
            enemy.update(dt);
            if(enemy.getX() < player.getX()+3.5f)
            enemy.b2body.setActive(true);
            if(enemy.getX() < player.getX()+3 && enemy.getX() > player.getX()+2.9f)
            gatoSound.play();
        }

     



        hud.update(dt);


        //attach our gamecam to our players.x coordinate

        if(player.b2body.getPosition().x>2 && !murio){
            gamecam.position.x=player.b2body.getPosition().x+0.5f;
            if(player.b2body.getLinearVelocity().x>2){

                velocidad = player.b2body.getLinearVelocity().x;
                player.b2body.getLinearVelocity().x=velocidad;

            }else{
                if(velocidad!=0){
                    player.b2body.setLinearVelocity(velocidad,0);
                }
                player.b2body.applyLinearImpulse(new Vector2(0.09f, 0), player.b2body.getWorldCenter(), true);
            }
        }else{
            if(player.b2body.getPosition().x<=2 && !murio) {
                if (player.b2body.getLinearVelocity().x > 2) {
                    player.b2body.applyLinearImpulse(new Vector2(0, 0), player.b2body.getWorldCenter(), true);
                } else {
                    player.b2body.applyLinearImpulse(new Vector2(0.09f, 0), player.b2body.getWorldCenter(), true);
                }
                gamecam.position.x = 2.5f;
            }
        }
        if(player.b2body.getPosition().x>65 && player.b2body.getPosition().x<150){
            gamecam.position.x=player.b2body.getPosition().x+0.5f;
            if(player.b2body.getLinearVelocity().x>2.3f){

                velocidad = player.b2body.getLinearVelocity().x;
                player.b2body.getLinearVelocity().x=velocidad;

            }else{
                if(velocidad!=0){
                    player.b2body.setLinearVelocity(velocidad,0);
                }
                player.b2body.applyLinearImpulse(new Vector2(0.09f, 0), player.b2body.getWorldCenter(), true);
            }
        }


        //update gamecam with correct  coordinate after change
        gamecam.update();

        //le dice al render que dibuje solo lo que ve la gamecam
        renderer.setView(gamecam);

    }

    @Override
    public void render(float delta) {

        update(delta);

        //Limpia la pantalla con negro
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);


        //render el mapa
        renderer.render();

        //Box2D
        //b2dr.render(world, gamecam.combined);

        game.batch.setProjectionMatrix(gamecam.combined);
        game.batch.begin();
        player.draw(game.batch);
        for (Enemy enemy : creator.getObjetos())
            enemy.draw(game.batch);
        for (Enemy enemy : creator.getGatos())
            enemy.draw(game.batch);
        game.batch.end();

        game.batch.setProjectionMatrix(hud.stage.getCamera().combined);
        hud.stage.draw();
}




    @Override
    public void resize(int width, int height) {
        gamePort.update(width, height);
    }

    public TiledMap getMap(){
        return map;
    }

    public World getWorld(){
        return world;
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

        map.dispose();
        renderer.dispose();
        world.dispose();
        b2dr.dispose();
        hud.dispose();

    }
}
