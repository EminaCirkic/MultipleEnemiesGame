package com.company;

import com.googlecode.lanterna.*;
import com.googlecode.lanterna.input.Key;
import com.googlecode.lanterna.terminal.Terminal;

import java.nio.charset.Charset;
import java.util.*;




public class Main{


    public static void main(String[] args) throws InterruptedException {

        Terminal terminal = TerminalFacade.createTerminal(System.in,
                System.out, Charset.forName("UTF8"));

        terminal.enterPrivateMode();

        Player player=new Player(10,10,Terminal.Color.RED);
        List<Enemy> enemies= new ArrayList<>();
        enemies.add(new Enemy(5,5,0.8f,'\u263a',Terminal.Color.BLUE));
        enemies.add(new Enemy(15,5,0.7f,'\u263a',Terminal.Color.BLUE));
        enemies.add(new Enemy(5,15,0.6f,'\u263a',Terminal.Color.BLUE));
        enemies.add(new Enemy(15,15,0.6f,'\u263a',Terminal.Color.BLUE));
        enemies.add(new Enemy(7,17,0f,'\u2601',Terminal.Color.YELLOW));
        enemies.add(new Enemy(17,17,0f,'\u2601',Terminal.Color.YELLOW));
        enemies.add(new Enemy(7,7,0f,'\u2601',Terminal.Color.YELLOW));
        enemies.add(new Enemy(17,7,0f,'\u2601',Terminal.Color.YELLOW));
        enemies.add(new Enemy(4,10,0f,'\u2601',Terminal.Color.YELLOW));
        enemies.add(new Enemy(16,10,0f,'\u2601',Terminal.Color.YELLOW));
        enemies.add(new Enemy(10,4,0f,'\u2601',Terminal.Color.YELLOW));
        enemies.add(new Enemy(13,6,0f,'\u2601',Terminal.Color.YELLOW));

       /*For each enemy call the following methods while the player and the enemy coordinates are
       not the same , aka player eaten by the enemy
        */
        for(Enemy enemy: enemies) {
            while (player.getPlayerX()!=enemy.getEnemyX() & player.getPlayerY()!=enemy.getEnemyY() ) {
                gameLogic(player, enemies);
                updateScreen(player, terminal, enemies);
                movePlayer(player, terminal);
            }
            printText(10, 10, "GAME OVER", terminal);
        }

            // System.exit(0);



    }


    /**
     * Here we have the game logic, basically the movement of enemies depends on
     * the placement of the player. Also the enemies are slowed down by the given speed
     * for each enemy. Then we have enemies which just stand still and once the player
     * encounters them the player is always placed at the right down corner of the enemy,
     * so the player basically jumps over this type of enemy.
     * @param player
     * @param enemies
     */
    private static  void gameLogic(Player player,List<Enemy> enemies) {
        int counter = 0;

        for (Enemy enemy : enemies) {
            if (enemy.getEnemyX() > player.getPlayerX() & enemy.getEnemyY() > player.getPlayerY()) {
                enemy.setEnemyX(enemy.getEnemyX() - enemy.getSpeed());
                enemy.setEnemyY(enemy.getEnemyY() - enemy.getSpeed());

            } else if (enemy.getEnemyX() < player.getPlayerX() & enemy.getEnemyY() < player.getPlayerY()) {
                enemy.setEnemyX(enemy.getEnemyX() + enemy.getSpeed());
                enemy.setEnemyY(enemy.getEnemyY() + enemy.getSpeed());

            } else if (enemy.getEnemyX() < player.getPlayerX() & enemy.getEnemyY() > player.getPlayerY()) {
                enemy.setEnemyX(enemy.getEnemyX() + enemy.getSpeed());
                enemy.setEnemyY(enemy.getEnemyY() - enemy.getSpeed());

            } else if (enemy.getEnemyX() > player.getPlayerX() & enemy.getEnemyY() < player.getPlayerY()) {
                enemy.setEnemyX(enemy.getEnemyX() - enemy.getSpeed());
                enemy.setEnemyY(enemy.getEnemyY() + enemy.getSpeed());
            }

            if(counter>3){
                enemy.setEnemyX(enemy.getEnemyX());
                enemy.setEnemyY(enemy.getEnemyY());
                if(enemy.getEnemyX()==player.getPlayerX() & enemy.getEnemyY()==player.getPlayerY()) {
                    player.setPlayerX(player.getPlayerX()+1) ;
                    player.setPlayerY(player.getPlayerY()+1);
                }


            }

            counter++;
        }



    }


    /**
     * Updates screen with all the players and enemies, including position coordinates
     * character and color.
     * @param player
     * @param terminal
     * @param enemies
     */
    private static void updateScreen(Player player, Terminal terminal, List<Enemy> enemies) {
        terminal.clearScreen();
        terminal.moveCursor(player.getPlayerX(),player.getPlayerY());
        terminal.putCharacter('O');
        terminal.applyForegroundColor(player.getColor());



        for (Enemy enemy : enemies) {
            terminal.moveCursor((int) enemy.getEnemyX(), (int) enemy.getEnemyY());
            terminal.putCharacter(enemy.getDisplaychar());
            terminal.applyForegroundColor(enemy.getColor());
        }

        terminal.moveCursor(0,0);
    }


    /**
     * Here we have the logic of player movement. First we listen for a key input
     * and put the player in right coordinates according to key option up, down, right, left.
     * Also if the player reaches the wall of the game, in this case 0x or 20x coordinates,
     * the player is pushed back.
     * @param player
     * @param terminal
     * @throws InterruptedException
     */
    private static void movePlayer(Player player,Terminal terminal) throws InterruptedException {
        //Wait for a key to be pressed
        Key key;
        do {
            Thread.sleep(5);
            key = terminal.readInput();
        }
        while (key == null);
        System.out.println(key.getCharacter() + " " + key.getKind());

        if(0>player.getPlayerX() ) {
        player.setPlayerX(player.getPlayerX()+1) ;
        }
        else if(0>player.getPlayerX()){
           player.setPlayerY(player.getPlayerY()+1);
        }
        else if(20<player.getPlayerX()){
            player.setPlayerX(player.getPlayerX()-1);
        }
        else if(20<player.getPlayerY()){
            player.setPlayerY(player.getPlayerY()-1);
        }
        else {
            switch (key.getKind()) {
                case ArrowLeft:
                    player.setPlayerX(player.getPlayerX() -1);
                    player.setPlayerY(player.getPlayerY());
                    break;
                case ArrowRight:
                    player.setPlayerX(player.getPlayerX() +1);
                    player.setPlayerY(player.getPlayerY());
                    break;
                case ArrowUp:
                    player.setPlayerY(player.getPlayerY() -1);
                    player.setPlayerX(player.getPlayerX());
                    break;
                case ArrowDown:
                    player.setPlayerY(player.getPlayerY() +1);
                    player.setPlayerX(player.getPlayerX());
                    break;

            }
        }
    }


    /**
     * Here we have a method that puts characters into the terminal from x,y coordinat.
     * It loops through an array of characters which makes up the whole message(string).
     * Each character in the message(string) is converted to a char and put into the char array
     * which then is looped through and printed in the terminal.
     * @param x
     * @param y
     * @param message
     * @param terminal
     */
    private static void printText(int x, int y, String message,Terminal terminal){

        terminal.moveCursor(x,y);
        char[] printToChar = message.toCharArray();
        for (int i=0;i<message.length();i++)
        {
            terminal.putCharacter(printToChar[i]);
        }


    }



}



