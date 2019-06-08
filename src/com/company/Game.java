/*package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;

public class Game {

    JFrame window = new JFrame("snake");

    public void setUpGame(){

        GameField gf = new GameField();

        gf.restart.addActionListener(new RestartListener());
        window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        window.setSize(320,345);
        window.add(new GameField(),BorderLayout.CENTER);
        window.add(gf.restart,BorderLayout.SOUTH);
        window.setLocation(400,400);
        window.setVisible(true);
    }

    public class GameField extends JPanel implements ActionListener {
        private final int SIZE = 320;
        private final int DOT_SIZE = 16;
        private final int ALL_DOTS = 400;
        private Image dot;
        private Image apple;
        private int appleX;
        private int appleY;
        private int[] x = new int[ALL_DOTS];
        private int[] y = new int[ALL_DOTS];
        private int dots;
        private Timer timer;
        private boolean left = false;
        private boolean right = true;
        private boolean up = false;
        private boolean down = false;
        private boolean inGame = true;
        private int delay = 125;
        private JButton restart = new JButton("restart");

        public GameField() {
            setBackground(Color.BLACK);
            loadImage();
            initGame();
            addKeyListener(new FieldKeyListener());
            setFocusable(true);
        }

        public void initGame() {
            dots = 3;
            for (int i = 0; i < dots; i++) {
                x[i] = 48 - i * DOT_SIZE;
                y[i] = 48;
            }
            timer = new Timer(250, this);
            timer.start();
            createApple();
        }

        public void initRestart() {
            dots = 3;
            for (int i = 0; i < dots; i++) {
                x[i] = 48 - i * DOT_SIZE;
                y[i] = 48;
            }
            createApple();
        }


        public void creatSnake() {
            dots = 3;
            int head = new Random().nextInt(200);
            if (head == 0) {
                head = 48;
            }
            for (int i = 0; i < dots; i++) {
                x[i] = 64 - i * DOT_SIZE;
                y[i] = 64;
            }
        }

        public void createApple() {

            appleX = new Random().nextInt(20) * DOT_SIZE;
            appleY = new Random().nextInt(20) * DOT_SIZE;

            if (appleX == 0) {
                appleX = 16 * DOT_SIZE;
            }

            if (appleY == 0) {
                appleY = 16 * DOT_SIZE;
            }

            System.out.println("apple x " + appleX + " apple y " + appleY);

        }

        public void loadImage() {
            ImageIcon iia = new ImageIcon("apple.png");
            apple = iia.getImage();
            ImageIcon iid = new ImageIcon("dot.png");
            dot = iid.getImage();
        }

        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (inGame) {
                g.drawImage(apple, appleX, appleY, this);
                for (int i = 0; i < dots; i++) {
                    g.drawImage(dot, x[i], y[i], this);
                    System.out.println("x " + x[0] + " y " + y[0]);
                }
                for (int i = 0; i < 336; i++) {
                    g.setColor(Color.RED);
                    g.drawRect(i, 0, 16, 16);
                    g.drawRect(0, i, 16, 16);
                    g.drawRect(336, i, 16, 16);
                    g.drawRect(i, 336, 16, 16);

                }


            } else {
                String str = "Game Over";
                String strRestart = "Space to restart ";
                //Font fontGameOver = new Font("Times New Roman", 29, Font.BOLD);
                //Font fontRestart = new Font("Times New Roman", 14,Font.PLAIN);
                //g.setFont(fontGameOver);
                g.setColor(Color.WHITE);
                g.drawString(str, 125, SIZE / 2);
                g.setColor(Color.WHITE);
                g.drawString(strRestart, 125, (SIZE / 2) + 30);
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
        }

        public void checkApple() {
            if (x[0] == appleX && y[0] == appleY) {
                dots++;
                createApple();
            }
        }

        public void checkCollissions() {
            for (int i = dots; i > 0; i--) {
                if (i > 4 && x[0] == x[i] && y[0] == y[i]) {
                    //inGame = false;
                    dots = 3;
                }
            }

            if (x[0] > SIZE) {
                inGame = false;
            }
            if (x[0] < 16) {
                inGame = false;
            }
            if (y[0] > SIZE) {
                inGame = false;
            }
            if (y[0] < 16) {
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



        class FieldKeyListener extends KeyAdapter {

            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);

                int key = e.getKeyCode();

                if (key == KeyEvent.VK_SPACE) {
                    inGame = true;
                    creatSnake();
                    createApple();
                    repaint();
                }

                if (key == KeyEvent.VK_LEFT && !right) {
                    left = true;
                    up = false;
                    down = false;
                }
                if (key == KeyEvent.VK_RIGHT && !left) {
                    right = true;
                    up = false;
                    down = false;
                }
                if (key == KeyEvent.VK_UP && !down) {
                    up = true;
                    left = false;
                    right = false;
                }
                if (key == KeyEvent.VK_DOWN && !up) {
                    down = true;
                    right = false;
                    left = false;
                }
            }
        }
    }

    class RestartListener implements ActionListener {
        GameField gameField = new GameField();
        @Override
        public void actionPerformed(ActionEvent e) {
                gameField.inGame = true;
                gameField.creatSnake();
                gameField.createApple();
                gameField.repaint();
        }
    }
}
*/