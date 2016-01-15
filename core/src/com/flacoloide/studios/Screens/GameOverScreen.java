package com.flacoloide.studios.Screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.flacoloide.studios.Scenes.Hud;
import com.flacoloide.studios.MegaCatastrofeNuclear;
/**
 * Created by brentaureli on 10/8/15.
 */
public class GameOverScreen implements Screen {
    private Viewport viewport;
    private Stage stage;
    private Hud hud;
    private MegaCatastrofeNuclear gamess;
    private Sound sound;
    private Game game;
    private PlayScreen screen;

    public GameOverScreen(Game game){
        this.game = game;
        viewport = new FitViewport(MegaCatastrofeNuclear.V_WIDTH, MegaCatastrofeNuclear.V_HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport);

        Label.LabelStyle font = new Label.LabelStyle(new BitmapFont(), Color.WHITE);

        Table table = new Table();
        table.center();
        table.setFillParent(true);

        Label gameOverLabel = new Label("GAME OVER", font);
        Label playAgainLabel = new Label("Click to Play Again or Back Button to Exit", font);

        table.add(gameOverLabel).expandX();
        table.row();
        table.add(playAgainLabel).expandX().padTop(10f);

        stage.addActor(table);
        sound = MegaCatastrofeNuclear.manager.get("audio/sounds/mariodie.wav", Sound.class);
        sound.play();
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        if(Gdx.input.justTouched()) {
            screen.hud.vidas = 2;
            screen.hud.score = 0;
            screen.hud.level="1";
            screen.game.setScreen(new PlayScreen(screen.game, "Level1/Level1.tmx"));
            //dispose();
            dispose();
        }
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {

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
        stage.dispose();
    }
}
