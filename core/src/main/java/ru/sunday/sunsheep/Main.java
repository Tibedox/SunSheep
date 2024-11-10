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
    private Texture image;
    Sheep[] sheep = new Sheep[3333];

    @Override
    public void create() {
        batch = new SpriteBatch();
        image = new Texture("sheep0.png");
        for (int i = 0; i < sheep.length; i++) {
            float wh = MathUtils.random(30, 100);
            sheep[i] = new Sheep(1280/2, 720/2, wh, wh);
        }
    }

    @Override
    public void render() {
        for (int i = 0; i < sheep.length; i++) {
            sheep[i].fly();
        }

        ScreenUtils.clear(0.45f, 0.15f, 0.2f, 1f);
        batch.begin();
        for (int i = 0; i < sheep.length; i++) {
            batch.draw(image, sheep[i].x, sheep[i].y, sheep[i].width, sheep[i].height);
        }
        batch.end();
    }

    @Override
    public void dispose() {
        batch.dispose();
        image.dispose();
    }
}
