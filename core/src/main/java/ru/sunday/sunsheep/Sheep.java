package ru.sunday.sunsheep;

public class Sheep {
    float x;
    float y;
    float width;
    float height;
    float stepX = 5;
    float stepY = 5;

    public Sheep(float x, float y, float width, float height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    void fly(){
        x += stepX;
        y += stepY;
        if(x<0 || x>1280-width) stepX = -stepX;
        if(y<0 || y>720-height) stepY = -stepY;
    }
}
