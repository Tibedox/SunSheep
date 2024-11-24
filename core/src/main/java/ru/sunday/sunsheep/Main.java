package ru.sunday.sunsheep;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector3;

public class Main extends ApplicationAdapter {
    public static final float SCR_WIDTH = 1600;
    public static final float SCR_HEIGHT = 900;
    public static final float SPAWN_SHEEP_X = 580, SPAWN_SHEEP_Y = 426;
    public static final float SPAWN_PIG_X = 876, SPAWN_PIG_Y = 422;

    private SpriteBatch batch;
    private OrthographicCamera camera;
    private Vector3 touch;

    private Texture imgSheep, imgPig;
    private Texture imgBackGround;
    private Sound sndSheep;
    private Sound sndPig;

    Sheep[] sheep = new Sheep[13];
    Pig[] pig = new Pig[12];

    @Override
    public void create() {
        batch = new SpriteBatch();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, SCR_WIDTH, SCR_HEIGHT);
        touch = new Vector3();

        imgBackGround = new Texture("farm.jpg");
        imgSheep = new Texture("sheep0.png");
        imgPig = new Texture("pig0.png");
        sndSheep = Gdx.audio.newSound(Gdx.files.internal("sound-sheep.mp3"));
        sndPig = Gdx.audio.newSound(Gdx.files.internal("sound-pig2.mp3"));

        for (int i = 0; i < sheep.length; i++) {
            float wh = MathUtils.random(30, 100);
            sheep[i] = new Sheep(SPAWN_SHEEP_X, SPAWN_SHEEP_Y, wh, wh, imgSheep, sndSheep);
        }
        for (int i = 0; i < pig.length; i++) {
            float wh = MathUtils.random(50, 120);
            pig[i] = new Pig(SPAWN_PIG_X, SPAWN_PIG_Y, wh, wh, imgPig, sndPig);
        }
    }

    @Override
    public void render() {
        // касания
        if(Gdx.input.justTouched()){
            touch.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(touch);
            System.out.println(touch.x+" "+ touch.y);
            for (Sheep s : sheep) {
                if (s.hit(touch.x, touch.y)) {
                    sndSheep.play();
                }
            }
            for (Pig p : pig) {
                if(p.hit(touch.x, touch.y)){
                    sndPig.play();
                }
            }
        }

        // события
        for (Sheep s: sheep) {
            s.fly();
        }
        for (Pig p: pig) {
            p.fly();
        }

        // отрисовка
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        batch.draw(imgBackGround, 0, 0, SCR_WIDTH, SCR_HEIGHT);
        for (Sheep s: sheep) {
            batch.draw(s.img, s.x, s.y, s.width, s.height);
        }
        for (Pig p: pig) {
            batch.draw(p.img, p.x, p.y, p.width, p.height);
        }
        batch.end();
    }

    @Override
    public void dispose() {
        batch.dispose();
        imgSheep.dispose();
        imgPig.dispose();
        imgBackGround.dispose();
        sndSheep.dispose();
        sndPig.dispose();
    }
}
