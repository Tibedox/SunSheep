package ru.sunday.sunsheep;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.TimeUtils;

public class Main extends ApplicationAdapter {
    public static final float SCR_WIDTH = 1600;
    public static final float SCR_HEIGHT = 900;
    public static final float SPAWN_SHEEP_X = 580, SPAWN_SHEEP_Y = 426;
    public static final float SPAWN_PIG_X = 876, SPAWN_PIG_Y = 422;

    private SpriteBatch batch;
    private OrthographicCamera camera;
    private Vector3 touch;
    private BitmapFont font50, font70;

    private Texture imgSheep, imgPig;
    private Texture imgBackGround;
    private Sound sndSheep;
    private Sound sndPig;

    SunButton btnRestart;
    SunButton btnClear;

    Sheep[] sheeps = new Sheep[13];
    Pig[] pigs = new Pig[22];
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
        font50 = new BitmapFont(Gdx.files.internal("fonts/comic50.fnt"));
        font70 = new BitmapFont(Gdx.files.internal("fonts/comic70.fnt"));

        imgBackGround = new Texture("farm.jpg");
        imgSheep = new Texture("sheep0.png");
        imgPig = new Texture("pig0.png");
        sndSheep = Gdx.audio.newSound(Gdx.files.internal("sound-sheep.mp3"));
        sndPig = Gdx.audio.newSound(Gdx.files.internal("sound-pig2.mp3"));

        btnRestart = new SunButton("Restart", font70, 680, 150);
        btnClear = new SunButton("clear", font50, 750, 230);

        for (int i = 0; i < players.length; i++) {
            players[i] = new Player("Noname", 0);
        }
        loadTableOfRecords();
        gameRestart();
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
            if(isGameOver){
                if(btnRestart.hit(touch.x, touch.y)){
                    gameRestart();
                }
                if(btnClear.hit(touch.x, touch.y)){
                    clearTableOfRecords();
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
        font50.draw(batch, "SCORE: "+ countAnimals, 10, SCR_HEIGHT-10);
        font50.draw(batch, showTime(timeCurrent), SCR_WIDTH-230, SCR_HEIGHT-10);
        if(isGameOver){
            font70.draw(batch, "Game Over", 0, 800, SCR_WIDTH, Align.center, true);
            for (int i = 0; i < players.length-1; i++) {
                font50.draw(batch, players[i].name, 470, 650-i*90);
                font50.draw(batch, showTime(players[i].time), 900, 650-i*90);
            }
            btnRestart.font.draw(batch, btnRestart.text, btnRestart.x, btnRestart.y);
            btnClear.font.draw(batch, btnClear.text, btnClear.x, btnClear.y);
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
        font50.dispose();
        font70.dispose();
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
        sortTableOfRecords();
        saveTableOfRecords();
    }

    void gameRestart(){
        isGameOver = false;
        countAnimals = 0;
        for (int i = 0; i < sheeps.length; i++) {
            float wh = MathUtils.random(30, 100);
            sheeps[i] = new Sheep(SPAWN_SHEEP_X, SPAWN_SHEEP_Y, wh, wh, imgSheep, sndSheep);
        }
        for (int i = 0; i < pigs.length; i++) {
            float wh = MathUtils.random(50, 120);
            pigs[i] = new Pig(SPAWN_PIG_X, SPAWN_PIG_Y, wh, wh, imgPig, sndPig);
        }
        timeStartGame = TimeUtils.millis();
    }

    void sortTableOfRecords(){
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

    void saveTableOfRecords(){
        Preferences prefs = Gdx.app.getPreferences("SunSheepPrefs");
        for (int i = 0; i < players.length; i++) {
            prefs.putString("name"+i, players[i].name);
            prefs.putLong("time"+i, players[i].time);
        }
        prefs.flush();
    }

    void loadTableOfRecords(){
        Preferences prefs = Gdx.app.getPreferences("SunSheepPrefs");
        for (int i = 0; i < players.length; i++) {
            players[i].name = prefs.getString("name"+i, "Noname");
            players[i].time = prefs.getLong("time"+i, 0);
        }
    }

    void clearTableOfRecords(){
        for (Player player : players) player.set("Noname", 0);
    }
}
