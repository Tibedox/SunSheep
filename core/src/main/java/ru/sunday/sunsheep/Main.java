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
    Sheep sheep0, sheep1;

    @Override
    public void create() {
        batch = new SpriteBatch();
        image = new Texture("sheep0.png");
        sheep0 = new Sheep(10, 50, 100, 100);
        sheep1 = new Sheep(400, 300, 80, 80);
    }

    @Override
    public void render() {
        sheep0.fly();
        sheep1.fly();
        ScreenUtils.clear(0.45f, 0.15f, 0.2f, 1f);
        batch.begin();
        batch.draw(image, sheep0.x, sheep0.y, sheep0.width, sheep0.height);
        batch.draw(image, sheep1.x, sheep1.y, sheep1.width, sheep1.height);
        batch.end();
    }

    @Override
    public void dispose() {
        batch.dispose();
        image.dispose();
    }
}
