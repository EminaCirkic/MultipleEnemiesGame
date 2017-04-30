package com.company;

import com.googlecode.lanterna.terminal.Terminal;

/**
 * Created by Emina on 10/3/2016.
 */
public class Player {
    private int x;
    private int y;
    private Terminal.Color color;

    public Player(int x, int y,Terminal.Color color){
        this.x=x;
        this.y=y;
        this.color=color;
    }

    public void setPlayerX(int x){this.x=x;}

    public void setPlayerY(int y){
        this.y=y;
    }

    public int getPlayerX(){
        return x;
    }

    public int getPlayerY(){
        return y;
    }

    public void setColor(Terminal.Color color){this.color=color;}

    public Terminal.Color getColor() { return color;}

}
