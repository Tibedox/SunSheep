package ru.sunday.sunsheep;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.ScreenUtils;

public class Main extends ApplicationAdapter {
    public static final float SCR_WIDTH = 1280;
    public static final float SCR_HEIGHT = 720;

    private SpriteBatch batch;

    private Texture imgSheep, imgPig;
    private Texture imgBackGround;
    private Sound sndSheep;
    private Sound sndPig;

    Sheep[] sheep = new Sheep[3];
    Pig[] pig = new Pig[12];

    @Override
    public void create() {
        batch = new SpriteBatch();

        imgBackGround = new Texture("field.png");
        imgSheep = new Texture("sheep0.png");
        imgPig = new Texture("pig0.png");
        sndSheep = Gdx.audio.newSound(Gdx.files.internal("sound-sheep.mp3"));
        sndPig = Gdx.audio.newSound(Gdx.files.internal("sound-pig2.mp3"));

        for (int i = 0; i < sheep.length; i++) {
            float wh = MathUtils.random(30, 100);
            sheep[i] = new Sheep(SCR_WIDTH/2, SCR_HEIGHT/2, wh, wh, imgSheep, sndSheep);
        }
        for (int i = 0; i < pig.length; i++) {
            float wh = MathUtils.random(50, 120);
            pig[i] = new Pig(0, 0, wh, wh, imgPig, sndPig);
        }
    }

    @Override
    public void render() {
        for (int i = 0; i < sheep.length; i++) {
            sheep[i].fly();
        }
        for (int i = 0; i < pig.length; i++) {
            pig[i].fly();
        }

        ScreenUtils.clear(0.45f, 0.15f, 0.2f, 1f);
        batch.begin();
        batch.draw(imgBackGround, 0, 0, SCR_WIDTH, SCR_HEIGHT);
        for (int i = 0; i < sheep.length; i++) {
            batch.draw(sheep[i].img, sheep[i].x, sheep[i].y, sheep[i].width, sheep[i].height);
        }
        for (int i = 0; i < pig.length; i++) {
            batch.draw(pig[i].img, pig[i].x, pig[i].y, pig[i].width, pig[i].height);
        }
        batch.end();
    }

    @Override
    public void dispose() {
        batch.dispose();
        imgSheep.dispose();
        imgPig.dispose();
    }
}
