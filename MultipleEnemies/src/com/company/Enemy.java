package com.company;

import com.googlecode.lanterna.terminal.Terminal;

import java.util.LinkedList;


/**
 * Created by Emina on 10/3/2016.
 */
public class Enemy {
    private float x;
    private float y;
    private float speed;
    private char displaychar;
    Terminal.Color color;


    public Enemy(float x,float y,float speed,char displaychar,Terminal.Color color){
        this.x=x;
        this.y=y;
        this.speed=speed;
        this.displaychar=displaychar;
        this.color=color;
    }



    public void setEnemyX(float x){
        this.x=x;
    }

    public void setEnemyY(float y){
        this.y=y;
    }

    public float getEnemyX(){return x;}//getX

    public float getEnemyY(){return y;}

    public void setSpeed(float speed){
        this.speed=speed;
    }

    public float getSpeed(){return speed;}

    public char getDisplaychar(){return displaychar;}

    public void setDisplaychar(char displaychar){this.displaychar=displaychar;}

    public void setColor(Terminal.Color color){this.color=color;}

    public Terminal.Color getColor() { return color;}


}
