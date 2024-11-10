package ru.sunday.sunsheep;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.ScreenUtils;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main extends ApplicationAdapter {
    private SpriteBatch batch;
    private Texture imgSheep, imgPig;
    Sheep[] sheep = new Sheep[33];
    Pig[] pig = new Pig[22];

    @Override
    public void create() {
        batch = new SpriteBatch();
        imgSheep = new Texture("sheep0.png");
        imgPig = new Texture("pig0.png");
        for (int i = 0; i < sheep.length; i++) {
            float wh = MathUtils.random(30, 100);
            sheep[i] = new Sheep(1280/2, 720/2, wh, wh);
        }
        for (int i = 0; i < pig.length; i++) {
            float wh = MathUtils.random(50, 120);
            pig[i] = new Pig(0, 0, wh, wh);
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
        for (int i = 0; i < sheep.length; i++) {
            batch.draw(imgSheep, sheep[i].x, sheep[i].y, sheep[i].width, sheep[i].height);
        }
        for (int i = 0; i < pig.length; i++) {
            batch.draw(imgPig, pig[i].x, pig[i].y, pig[i].width, pig[i].height);
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
