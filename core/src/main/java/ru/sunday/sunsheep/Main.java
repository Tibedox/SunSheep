package ru.sunday.sunsheep;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main extends ApplicationAdapter {
    private SpriteBatch batch;
    private Texture image;
    Sheep[] sheep = new Sheep[3];

    @Override
    public void create() {
        batch = new SpriteBatch();
        image = new Texture("sheep0.png");
        sheep[0] = new Sheep(10, 50, 100, 100);
        sheep[1] = new Sheep(400, 300, 80, 80);
        sheep[2] = new Sheep(800, 300, 150, 150);
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
