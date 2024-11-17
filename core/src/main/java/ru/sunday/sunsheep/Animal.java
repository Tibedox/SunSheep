package ru.sunday.sunsheep;

import static ru.sunday.sunsheep.Main.*;

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
    static int c;

    public Animal(float x, float y, float width, float height, Texture img) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        stepX = MathUtils.random(-5f, 5);
        stepY = MathUtils.random(-5f, 5);
        this.img = img;
    }

    void fly(){
        x += stepX;
        y += stepY;
        if(x<0 || x>SCR_WIDTH-width) stepX = -stepX;
        if(y<0 || y>SCR_HEIGHT-height) stepY = -stepY;
    }

    abstract void say();
}
