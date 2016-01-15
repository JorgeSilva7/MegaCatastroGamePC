package com.flacoloide.studios.Scenes;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.flacoloide.studios.MegaCatastrofeNuclear;
import com.flacoloide.studios.Screens.PlayScreen;


/**
 * Created by Coke on 20-11-2015.
 */
public class Hud implements Disposable {
    public Stage stage;
    private Viewport viewport;

    public Integer worldTimer;
    private float timeCount;
    public static Integer score=0;

    private static Label scoreLabel;
    public Label levelLabel;
    public Label lifesLabel;
    private Label vidasLabel;
    private Label worldLabel;
    private Label megaLabel;
    public static String level = "1";
    public static int vidas=2;
    public String cadenaVidas= "";

    public Hud(SpriteBatch sb){
        worldTimer = 300;
        timeCount = 0;


       cadenaVidas = "";



        cadenaVidas = String.valueOf(vidas);

       //cadenaVidas= Integer.toString(vidas);

        viewport = new FitViewport(MegaCatastrofeNuclear.V_WIDTH, MegaCatastrofeNuclear.V_HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport, sb);

        Table table = new Table();
        table.top();
        table.setFillParent(true);

        //countDownLabel = new Label(String.format("%03d", worldTimer), new Label.LabelStyle(new BitmapFont(), com.badlogic.gdx.graphics.Color.WHITE));
        scoreLabel = new Label(String.format("%03d", score), new Label.LabelStyle(new BitmapFont(), com.badlogic.gdx.graphics.Color.WHITE));
        //timeLabel = new Label("TIME", new Label.LabelStyle(new BitmapFont(), com.badlogic.gdx.graphics.Color.WHITE));
        levelLabel = new Label(level, new Label.LabelStyle(new BitmapFont(), com.badlogic.gdx.graphics.Color.WHITE));
        worldLabel = new Label("LEVEL", new Label.LabelStyle(new BitmapFont(), com.badlogic.gdx.graphics.Color.WHITE));
        megaLabel = new Label("PLAYER", new Label.LabelStyle(new BitmapFont(), com.badlogic.gdx.graphics.Color.WHITE));
        lifesLabel = new Label(cadenaVidas, new Label.LabelStyle(new BitmapFont(), com.badlogic.gdx.graphics.Color.WHITE));
        vidasLabel = new Label("LIFES", new Label.LabelStyle(new BitmapFont(), com.badlogic.gdx.graphics.Color.WHITE));

        table.add(vidasLabel).expandX().padTop(10);
        table.add(megaLabel).expandX().padTop(10);
        table.add(worldLabel).expandX().padTop(10);
        table.row();
        table.add(lifesLabel).expandX();
        table.add(scoreLabel).expandX();
        table.add(levelLabel).expandX();

        stage.addActor(table);
    }


    public void update(float dt){
        timeCount +=dt;
        if(timeCount >=1 && !PlayScreen.murio){
            score++;
            scoreLabel.setText((String.format("%03d", score)));
            timeCount = 0;
        }else{
                scoreLabel.setText((String.format("%03d", score)));
        }
    }

    public void bajarVida(){
        this.vidas=this.vidas-1;
    }

    public static void addScore(int value){
        score+= value;
        scoreLabel.setText((String.format("%03d", score)));

    }
    @Override
    public void dispose() {
        stage.dispose();
    }
}
