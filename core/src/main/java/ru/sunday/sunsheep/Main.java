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
    Sheep sheep;

    @Override
    public void create() {
        batch = new SpriteBatch();
        image = new Texture("sheep0.png");
        sheep = new Sheep();
    }

    @Override
    public void render() {
        sheep.fly();
        ScreenUtils.clear(0.45f, 0.15f, 0.2f, 1f);
        batch.begin();
        batch.draw(image, sheep.x, sheep.y, sheep.width, sheep.height);
        batch.end();
    }

    @Override
    public void dispose() {
        batch.dispose();
        image.dispose();
    }
}
