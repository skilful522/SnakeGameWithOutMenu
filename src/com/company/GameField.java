package com.company;

import jaco.mp3.player.MP3Player;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.Random;

import static java.awt.event.KeyEvent.*;

public class GameField extends JPanel implements ActionListener {

    private final int SIZE = 1366;
    private final int DOT_SIZE = 32;
    private final int ALL_DOTS = 1764;
    private Image backGround;
    private Image dot;
    private Image dot2;
    private Image bodyImage;
    private Image apple;
    private Image waterImage;
    private int appleX;
    private int appleY;
    private boolean check = false;
    private boolean pause = false;

    //snake's head images
    private Image headRightImage;
    private Image headLeftImage;
    private Image headUpImage;
    private Image headDownImage;

    //snake 1
    private int[] x = new int[ALL_DOTS];
    private int[] y = new int[ALL_DOTS];
    private int dots;
    private boolean left = false;
    private boolean right = true;
    private boolean up = false;
    private boolean down = false;

    //snake2
    private int[] x2 = new int[ALL_DOTS];
    private int[] y2 = new int[ALL_DOTS];
    private int dots2;
    private boolean left2 = true;
    private boolean right2 = false;
    private boolean up2 = false;
    private boolean down2 = false;

    private MP3Player mp3Player = new MP3Player();
    private Timer timer;
    boolean inGame = true;
    private int delay = 250;

    public GameField() {
        //setBackground(Color.BLACK);
        playMusic();
        loadImage();
        initGame();
        addKeyListener(new FieldKeyListener());
        setFocusable(true);
    }

    public void playMusic() {
        mp3Player.addToPlayList(new File("Minecraft_Theme_Sweden_Calm.mp3"));
        mp3Player.play();
        mp3Player.setRepeat(true);
    }

    public void initGame() {
        dots = 3;
        dots2 = 3;
        for (int i = 0; i < dots; i++) {
            x[i] = 160 - i * DOT_SIZE;
            y[i] = 160;
        }

        for (int i = 0; i < dots; i++) {
            x2[i] = 640 - i * DOT_SIZE;
            y2[i] = 640;
        }

        timer = new Timer(delay, this);
        timer.start();
        createApple();
    }

    public void creatSnake() {
        dots = 3;
        //  dots2 = 3;

        int head = new Random().nextInt(200);
        //   int head2 = new Random().nextInt(300);

        if (head == 0) {
            head = 64;
        }

        //    if (head2 == 0){
        //       head2  = 160;
        //  }
        for (int i = 0; i < dots; i++) {
            x[i] = 96 - i * DOT_SIZE;
            y[i] = 96;
        }

     /*   for (int i = 0; i < dots ; i++) {
            x2[i] = 1248 - i*DOT_SIZE;
            y2[i] = 704;
        } */
    }

    public void createApple() {

        appleX = new Random().nextInt(41) * DOT_SIZE;
        appleY = new Random().nextInt(23) * DOT_SIZE;

        if (appleX == 0) {
            appleX = 2 * DOT_SIZE;
        }

        if (appleY == 0) {
            appleY = 2 * DOT_SIZE;
        }

       // System.out.println("apple x " + appleX + " apple y " + appleY);

    }

    public void loadImage() {
        ImageIcon iibackground = new ImageIcon("Background.png");
        backGround = iibackground.getImage();
        ImageIcon iia = new ImageIcon("apple2.png");
        apple = iia.getImage();
        ImageIcon iid = new ImageIcon("dot.png");
        dot = iid.getImage();
        ImageIcon iid2 = new ImageIcon("dot2.png");
        dot2 = iid2.getImage();

        ImageIcon imageIconBody = new ImageIcon("snakeBody.png");
        bodyImage = imageIconBody.getImage();
        ImageIcon imageIconWater = new ImageIcon("water.png");
        waterImage = imageIconWater.getImage();

        // snake heads
        ImageIcon imageIconHeadRight = new ImageIcon("snakeHeadRight.png");
        headRightImage = imageIconHeadRight.getImage();
        ImageIcon imageIconHeadLeft = new ImageIcon("snakeHeadLeft.png");
        headLeftImage = imageIconHeadLeft.getImage();
        ImageIcon imageIconHeadUp = new ImageIcon("snakeHeadUp.png");
        headUpImage = imageIconHeadUp.getImage();
        ImageIcon imageIconHeadDown = new ImageIcon("snakeHeadDown.png");
        headDownImage = imageIconHeadDown.getImage();

    }

    @Override
    public void paintComponent(Graphics g) {

        super.paintComponent(g);
        g.drawImage(backGround, 0, 0, 1350, 752, this);
        if (inGame) {
            g.drawImage(apple, appleX, appleY, 32, 32, this);
            if (right){
                g.drawImage(headRightImage, x[0], y[0], 32, 32, this);
            } else if (left){
                g.drawImage(headLeftImage, x[0], y[0], 32, 32, this);
            } else if (up){
                g.drawImage(headUpImage, x[0], y[0], 32, 32, this);
            } else if (down){
                g.drawImage(headDownImage, x[0], y[0], 32, 32, this);
            }

            for (int i = 1; i < dots; i++) {
                g.drawImage(bodyImage, x[i], y[i], 32, 32, this);
                //   System.out.println("x " + x[0] + " y " + y[0]);
            }

        /*    for (int i = 0; i < dots2 ; i++) {
                g.drawImage(dot2, x2[i], y2[i], 32,32,this);
            } */
            for (int i = 0; i < 1350; i++) {
                //g.setColor(Color.RED);
                g.drawImage(waterImage, i, 0, 32, 32, this);
                g.drawImage(waterImage, 0, i, 32, 32, this);
                g.drawImage(waterImage, 1350, i, 32, 32, this);
                g.drawImage(waterImage, i, 736, 32, 32, this);
               /* g.drawRect(i,0,16,16);
                g.drawRect(0,i,16,16);
                g.drawRect(1350,i,16,16);
                g.drawRect(i,752,16,16); */

            }

        } else {
            super.paintComponent(g);
            pause = true;
            setBackground(Color.BLACK);
            String str = "Game Over";
            String strRestart = "Space to restart ";
            String strQuite = "Escape to quite";
            delay = 250;
            if (pause){
                timer.stop();
            }
            g.setColor(Color.RED);
            g.drawString(str, (1366 / 2) - 50, 768 / 2);
            g.setColor(Color.YELLOW);
            g.drawString(strRestart, (1366 / 2) - 50, (758 / 2) + 30);
            g.setColor(Color.GREEN);
            g.drawString(strQuite, (1366 / 2) - 50, (758 / 2) + 60);
        }
    }

    public void move() {
        for (int i = dots; i > 0; i--) {
            x[i] = x[i - 1];
            y[i] = y[i - 1];
        }
        if (left) {
            x[0] = x[0] - DOT_SIZE;
        }
        if (right) {
            x[0] = x[0] + DOT_SIZE;
        }
        if (up) {
            y[0] = y[0] - DOT_SIZE;
        }
        if (down) {
            y[0] = y[0] + DOT_SIZE;
        }

        for (int i = dots2; i > 0; i--) {
            x2[i] = x2[i - 1];
            y2[i] = y2[i - 1];
        }
        if (left2) {
            x2[0] = x2[0] - DOT_SIZE;
        }
        if (right2) {
            x2[0] = x2[0] + DOT_SIZE;
        }
        if (up2) {
            y2[0] = y2[0] - DOT_SIZE;
        }
        if (down2) {
            y2[0] = y2[0] + DOT_SIZE;
        }
    }

    public void checkApple() {

        if (x[0] == appleX && y[0] == appleY) {
            dots++;
            createApple();
            delay = delay - 10;
            timer.setDelay(delay);
        }

    /*    if(x2[0] == appleX && y2[0] == appleY){
            dots2++;
            createApple();
        } */
    }

    public void checkCollissions() {
        for (int i = dots; i > 0; i--) {
            if (i > 3 && x[0] == x[i] && y[0] == y[i]) {
                //inGame = false;
                dots = 3;
            }
        }

        for (int i = dots2; i > 0; i--) {
            if (i > 4 && x2[0] == x2[i] && y2[0] == y2[i]) {
                //inGame = false;
                dots2 = 3;
            }
        }

        if (x[0] > SIZE ) {
            inGame = false;
        }
        if (x[0] < 16 ) {
            inGame = false;
        }
        if (y[0] > 704 ) {
            inGame = false;
        }
        if (y[0] < 16 ) {
            inGame = false;
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (inGame) {
            checkApple();
            checkCollissions();
            move();
        }
        repaint();
    }

    public class FieldKeyListener extends KeyAdapter {

        @Override
        public void keyPressed(KeyEvent e) {
            super.keyPressed(e);

            int key = e.getKeyCode();

            if (key == KeyEvent.VK_P) {
                timer.start();
                right = true;
                up = false;
                left = false;
                down = false;
            }

            if (key == KeyEvent.VK_ESCAPE) {
                System.exit(0);
            }

            if (key == KeyEvent.VK_LEFT || key == KeyEvent.VK_RIGHT || key == KeyEvent.VK_UP || key == KeyEvent.VK_DOWN){
                pause = false;
                if (pause == false){
                    timer.start();
                }
            }

            if (key == KeyEvent.VK_SPACE) {
                inGame = true;
                right = true;
                up = false;
                left = false;
                down = false;
                creatSnake();
                createApple();
                repaint();
            }

            if (key == VK_A && !right2) {
                left2 = true;
                up2 = false;
                down2 = false;
            }
            if (key == KeyEvent.VK_D && !left2) {
                right2 = true;
                up2 = false;
                down2 = false;
            }
            if (key == KeyEvent.VK_W && !down2) {
                up2 = true;
                left2 = false;
                right2 = false;
            }
            if (key == KeyEvent.VK_S && !up2) {
                down2 = true;
                right2 = false;
                left2 = false;
            }

            if (key == KeyEvent.VK_LEFT && !right) {
                left = true;
                up = false;
                down = false;
                System.out.println("left");
            }
            if (key == KeyEvent.VK_RIGHT && !left) {
                right = true;
                up = false;
                down = false;
                System.out.println("right");
            }
            if (key == KeyEvent.VK_UP && !down) {
                up = true;
                left = false;
                right = false;
                System.out.println("up");
            }
            if (key == KeyEvent.VK_DOWN && !up) {
                down = true;
                right = false;
                left = false;
                System.out.println("down");
            }
        }
    }
}
