package ru.sunday.sunsheep;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.TimeUtils;

public class Main extends ApplicationAdapter {
    public static final float SCR_WIDTH = 1600;
    public static final float SCR_HEIGHT = 900;
    public static final float SPAWN_SHEEP_X = 580, SPAWN_SHEEP_Y = 426;
    public static final float SPAWN_PIG_X = 876, SPAWN_PIG_Y = 422;

    private SpriteBatch batch;
    private OrthographicCamera camera;
    private Vector3 touch;
    private BitmapFont font;

    private Texture imgSheep, imgPig;
    private Texture imgBackGround;
    private Sound sndSheep;
    private Sound sndPig;

    Sheep[] sheeps = new Sheep[3];
    Pig[] pigs = new Pig[2];
    Player[] players = new Player[6];
    int countAnimals;
    long timeStartGame;
    long timeCurrent;
    boolean isGameOver;

    @Override
    public void create() {
        batch = new SpriteBatch();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, SCR_WIDTH, SCR_HEIGHT);
        touch = new Vector3();
        font = new BitmapFont(Gdx.files.internal("comic50.fnt"));

        imgBackGround = new Texture("farm.jpg");
        imgSheep = new Texture("sheep0.png");
        imgPig = new Texture("pig0.png");
        sndSheep = Gdx.audio.newSound(Gdx.files.internal("sound-sheep.mp3"));
        sndPig = Gdx.audio.newSound(Gdx.files.internal("sound-pig2.mp3"));

        for (int i = 0; i < sheeps.length; i++) {
            float wh = MathUtils.random(30, 100);
            sheeps[i] = new Sheep(SPAWN_SHEEP_X, SPAWN_SHEEP_Y, wh, wh, imgSheep, sndSheep);
        }
        for (int i = 0; i < pigs.length; i++) {
            float wh = MathUtils.random(50, 120);
            pigs[i] = new Pig(SPAWN_PIG_X, SPAWN_PIG_Y, wh, wh, imgPig, sndPig);
        }
        for (int i = 0; i < players.length; i++) {
            players[i] = new Player("Noname", 0);
        }
        timeStartGame = TimeUtils.millis();
    }

    @Override
    public void render() {
        // касания
        if(Gdx.input.justTouched()){
            touch.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(touch);

            for (Sheep s : sheeps) {
                if (s.hit(touch.x, touch.y)) {
                    s.snd.play();
                    s.catched(SPAWN_SHEEP_X, SPAWN_SHEEP_Y);
                    countAnimals++;
                }
            }
            for (Pig p : pigs) {
                if(p.hit(touch.x, touch.y)){
                    p.snd.play();
                    p.catched(SPAWN_PIG_X, SPAWN_PIG_Y);
                    countAnimals++;
                }
            }
        }

        // события
        for (Sheep s: sheeps) s.fly();
        for (Pig p: pigs) p.fly();
        if(!isGameOver) {
            timeCurrent = TimeUtils.millis() - timeStartGame;
            if(countAnimals == sheeps.length+pigs.length) {
                gameOver();
            }
        }

        // отрисовка
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        batch.draw(imgBackGround, 0, 0, SCR_WIDTH, SCR_HEIGHT);
        for (Sheep s: sheeps) batch.draw(s.img, s.x, s.y, s.width, s.height);
        for (Pig p: pigs) batch.draw(p.img, p.x, p.y, p.width, p.height);
        font.draw(batch, "SCORE: "+ countAnimals, 10, SCR_HEIGHT-10);
        font.draw(batch, showTime(timeCurrent), SCR_WIDTH-230, SCR_HEIGHT-10);
        if(isGameOver){
            for (int i = 0; i < players.length-1; i++) {
                font.draw(batch, players[i].name, 600, 650-i*90);
                font.draw(batch, showTime(players[i].time), 1000, 650-i*90);
            }
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
        font.dispose();
    }

    String showTime(long time){
        long msec = time%1000/100;
        long sec = time/1000%60;
        long min = time/1000/60%60;
        //long hour = time/1000/60/60;
        return ""+min/10+min%10+":"+sec/10+sec%10+"."+msec;
    }

    void gameOver(){
        isGameOver = true;
        players[players.length-1].set("Winner", timeCurrent);
        sortPlayers();
    }

    void sortPlayers(){
        for (int i = 0; i < players.length; i++) {
            if(players[i].time == 0){
                players[i].time = Long.MAX_VALUE;
            }
        }
        for (int j = 0; j < players.length; j++) {
            for (int i = 0; i < players.length-1; i++) {
                if(players[i].time>players[i+1].time){
                    Player tmp = players[i];
                    players[i] = players[i+1];
                    players[i+1] = tmp;
                }
            }
        }
        for (int i = 0; i < players.length; i++) {
            if(players[i].time == Long.MAX_VALUE){
                players[i].time = 0;
            }
        }
    }
}
