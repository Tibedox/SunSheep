package ru.sunday.sunsheep;

import com.badlogic.gdx.math.MathUtils;

public abstract class Animal {
    public float x;
    public float y;
    public float width;
    public float height;
    private float stepX;
    private float stepY;

    public Animal(float x, float y, float width, float height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        stepX = MathUtils.random(-5f, 5);
        stepY = MathUtils.random(-5f, 5);
    }

    void fly(){
        x += stepX;
        y += stepY;
        if(x<0 || x>1280-width) stepX = -stepX;
        if(y<0 || y>720-height) stepY = -stepY;
    }

    abstract void say();
}
