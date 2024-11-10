package ru.sunday.sunsheep;

public class Sheep {
    float x = 140;
    float y = 210;
    float stepX = 5;
    float stepY = 5;
    float width = 300;
    float height = 300;

    void fly(){
        x += stepX;
        y += stepY;
        if(x<0 || x>1280-width) stepX = -stepX;
        if(y<0 || y>720-height) stepY = -stepY;
    }
}
