package main;


import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {

    GamePanel gp;
    public boolean upPressed, downPressed, leftPressed, rightPressed, enterPressed, spacePressed;
    public boolean checkDrawTime = false;

    public KeyHandler(GamePanel gp) {
        this.gp = gp;
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();

        //TITLE STATE
        if(gp.gameState == gp.titleState) {
            if (code == KeyEvent.VK_W) {
               gp.ui.commandNum--;
               if(gp.ui.commandNum < 0) {
                   gp.ui.commandNum = 2; // Assuming there are 3 commands, wrap around to the last one
               }
            }
            if (code == KeyEvent.VK_S) {
                gp.ui.commandNum++;
                if(gp.ui.commandNum > 2) {
                    gp.ui.commandNum = 0; // Wrap around to the first command
                }
            }
            if (code == KeyEvent.VK_ENTER) {
                if(gp.ui.commandNum == 0) {
                    gp.gameState = gp.playState;
                    gp.stopMusic();
                    gp.playMusic(0);
                }
                if (gp.ui.commandNum == 1) {
                    // Load game logic here
                }
                if (gp.ui.commandNum == 2) {
                    System.exit(0); // Exit the game
                }
            }
        }

        //PLAY STATE
        else if(gp.gameState == gp.playState) {
            if (code == KeyEvent.VK_W) {
                upPressed = true;
            }
            if (code == KeyEvent.VK_S) {
                downPressed = true;
            }
            if (code == KeyEvent.VK_A) {
                leftPressed = true;
            }
            if (code == KeyEvent.VK_D) {
                rightPressed = true;
            }
            if (code == KeyEvent.VK_P) {
                gp.gameState = gp.pauseState;
            }if (code == KeyEvent.VK_ENTER) {
                enterPressed = true;
            }if (code == KeyEvent.VK_ESCAPE) {
                gp.gameState = gp.optionsState;
            }

            if (code == KeyEvent.VK_T) {
                if (checkDrawTime == false) {
                    checkDrawTime = true;
                } else {
                    checkDrawTime = false;
                }
            }
            if (code == KeyEvent.VK_R) {
                switch (gp.currentMap) {
                    case 0 -> {
                        gp.currentMap = 1;
                        gp.tileM.loadMap("/maps/clean_worldV2.txt", 0);
                    }
                    case 1 -> {
                        gp.currentMap = 0;
                        gp.tileM.loadMap("/maps/clean_village-Map.txt", 1);
                    }
                }
            }
        }
        //pause state
        else if(gp.gameState == gp.pauseState){
            if (code == KeyEvent.VK_P) {
                gp.gameState = gp.playState;
            }
        }
        //dialogue state
        else if(gp.gameState == gp.dialogueState){
            if(code == KeyEvent.VK_ENTER){
                gp.gameState = gp.playState;
            }
        }

        //options state
        else if(gp.gameState == gp.optionsState){
            if (code == KeyEvent.VK_ESCAPE) {
                gp.gameState = gp.playState;
            }
            if (code == KeyEvent.VK_ENTER) {
                enterPressed = true;
            }

            int maxCommandNum = 0; // Assuming there are 3 options in the options menu
            switch(gp.ui.subState){
                case 0: maxCommandNum = 5; // Assuming 3 sound options
                    break;
                case 3: maxCommandNum = 1; // Assuming 2 options in the controls menu
                    break;
            }
            if (code == KeyEvent.VK_W) {
                gp.ui.commandNum--;
                if(gp.ui.commandNum < 0) {
                    gp.ui.commandNum = maxCommandNum; // Wrap around to the last command
                }
            }
            if (code == KeyEvent.VK_S) {
                gp.ui.commandNum++;
                if(gp.ui.commandNum > maxCommandNum) {
                    gp.ui.commandNum = 0; // Wrap around to the first command
                }
            }
            if(code == KeyEvent.VK_A){
                if(gp.ui.subState == 0) { // Sound options
                    if(gp.ui.commandNum == 1 && gp.music.volumeScale > 0){ // Music volume
                        gp.music.volumeScale--;
                        gp.music.checkVolume();
                    }
                    if(gp.ui.commandNum == 2 && gp.se.volumeScale > 0){ // Music volume
                        gp.se.volumeScale--;
                        gp.se.checkVolume();
                    }
                }
            }
            if(code == KeyEvent.VK_D){
                if(gp.ui.subState == 0) { // Sound options
                    if(gp.ui.commandNum == 1 && gp.music.volumeScale < 5){ // Music volume
                        gp.music.volumeScale++;
                        gp.music.checkVolume();
                    }
                    if(gp.ui.commandNum == 2 && gp.se.volumeScale < 5){ // Music volume
                        gp.se.volumeScale++;
                        gp.se.checkVolume();
                    }
                }
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

        int code = e.getKeyCode();


        if(code == KeyEvent.VK_W) {
            upPressed = false;
        }
        if(code == KeyEvent.VK_S) {
            downPressed = false;
        }
        if(code == KeyEvent.VK_A) {
            leftPressed = false;
        }
        if(code == KeyEvent.VK_D) {
            rightPressed = false;
        }

    }
}
