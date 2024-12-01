package ru.sunday.sunsheep;

import static ru.sunday.sunsheep.Main.*;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;

public abstract class Animal {
    public float x;
    public float y;
    public float width;
    public float height;
    private float stepX;
    private float stepY;
    public Texture img;
    public Sound snd;
    float homeX, homeY;
    float speedGoHome = 20;
    float reSize;
    boolean isCatched;

    public Animal(float x, float y, float width, float height, Texture img, Sound snd) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        stepX = MathUtils.random(-2f, 2);
        stepY = MathUtils.random(-2f, 2);
        this.img = img;
        this.snd = snd;
    }

    void fly(){
        x += stepX;
        y += stepY;
        if (x < 0 || x > SCR_WIDTH - width) {
            stepX = -stepX;
        }
        if (y < 0 || y > SCR_HEIGHT - height) {
            stepY = -stepY;
        }
        if(isCatched) {
            width -= reSize;
            height -= reSize;
            if(homeX-Math.abs(stepX)<x && x<homeX+Math.abs(stepX)){
                stepX = 0;
                stepY = 0;
                reSize = 0;
                width = 0;
                height = 0;
            }
        }
    }

    boolean hit(float tx, float ty){
        return x<tx && tx<x+width && y<ty && ty<y+height;
    }

    void catched(float homeX, float homeY){
        this.homeX = homeX;
        this.homeY = homeY;
        isCatched = true;
        stepX = (homeX - x)/speedGoHome;
        stepY = (homeY - y)/speedGoHome;
        reSize = width/speedGoHome/2;
    }
}
