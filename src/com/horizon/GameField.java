package com.horizon;

import jaco.mp3.player.MP3Player;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.util.Random;
import javax.swing.Timer;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class GameField extends JPanel implements ActionListener {

    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    int width = (int) screenSize.getWidth();
    int height = (int) screenSize.getHeight();
    private final int SIZE = width;
    private final int DOT_SIZE = 32;
    private final int ALL_DOTS = (int) Math.pow(SIZE / DOT_SIZE, 2);
    private Image backGround;
    private Image bodyImage;
    private Image apple;
    private Image waterImage;
    private Image ground;
    private Image surprise;
    private int appleX;
    private int appleY;
    private int surpriseX;
    private int surpriseY;
    private int count;
    private int rand = 1;

    private JLabel label = new JLabel();
    private JLabel scoreLabel = new JLabel();
    private JLabel timerLabel = new JLabel();
    private JLabel gameOverLable = new JLabel();
    private JLabel restartLable = new JLabel();
    private JLabel escLable = new JLabel();

    //snake's head images
    private Image headRightImage;
    private Image headLeftImage;
    private Image headUpImage;
    private Image headDownImage;

    private Image blueHeadRightImage;
    private Image blueHeadLeftImage;
    private Image blueHeadUpImage;
    private Image blueHeadDownImage;
    private Image blueSnakeBodyImage;

    //snake
    private int[] x = new int[ALL_DOTS];
    private int[] y = new int[ALL_DOTS];
    private int dots;
    private boolean left = false;
    private boolean right = true;
    private boolean up = false;
    private boolean down = false;
    private MP3Player mp3Player = new MP3Player();
    private Timer timer;
    private Timer actionTimer;
    private Timer gameTimer;
    private Font buttacup;
    boolean inGame = true;
    private int delay = 250;
    private int seconds = 0;
    private int secondsPlayed = 0;
    private String[] actions = {"You have been increased", "You have been speed up",
            "Your color has been changed", "Map has been changed", "Empty box"};
    private int eaten;
    private JPanel infoPanel = new JPanel(new BorderLayout());
    private boolean changeSnake = false;
    private boolean changeBackground = false;

    public GameField() {
        loadImage();
        // playMusic();
        initScoreLabel();
        initGame();
        addKeyListener(new FieldKeyListener());
        setFocusable(true);
    }

    public void playMusic() {
        mp3Player.addToPlayList(new File("music/Minecraft_Theme_Sweden_Calm.mp3"));
        mp3Player.play();
        mp3Player.setRepeat(true);
    }

    public void initGame() {
        initGameTimer();
        dots = 3;
        for (int i = 0; i < dots; i++) {
            x[i] = 160 - i * DOT_SIZE;
            y[i] = 160;
        }
        timer = new Timer(delay, this);
        timer.start();
        createApple();
        if (eaten % 3 == 0) {
            createSurprise();
        }
    }

    public void initScoreLabel() {
        setLayout(new BorderLayout());
        scoreLabel.setHorizontalAlignment(JLabel.CENTER);
        scoreLabel.setPreferredSize(new Dimension(200, 40));
        add(scoreLabel, BorderLayout.PAGE_START);
        timerLabel.setHorizontalAlignment(JLabel.SOUTH_EAST);
        add(timerLabel, BorderLayout.PAGE_END);
        label.setHorizontalAlignment(JLabel.CENTER);
        label.setVerticalAlignment(JLabel.CENTER);
        add(label, BorderLayout.CENTER);
    }

    public void initGameTimer() {
        ActionListener actionListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                secondsPlayed++;
                timerLabel.setForeground(Color.WHITE);
                timerLabel.setFont(buttacup);
                timerLabel.setText("Time " + secondsPlayed);
            }
        };
        int ping = 1000;
        gameTimer = new Timer(ping, actionListener);
        gameTimer.start();
    }

    public void initTimer(int secondsPassed) {
        ActionListener action = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (seconds == 0) {
                    actionTimer.stop();
                } else {
                    seconds--;
                }
                count = seconds;
                System.out.println(seconds);
            }
        };
        int actionDelay = 1000;
        actionTimer = new Timer(actionDelay, action);
        actionTimer.setInitialDelay(0);
        actionTimer.start();
        seconds = secondsPassed;
    }

    public void creatSnake() {
        dots = 3;
        for (int i = 0; i < dots; i++) {
            x[i] = 96 - i * DOT_SIZE;
            y[i] = 96;
        }
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
        for (int i = 0; i < x.length; i++) {
            if (appleX == x[i]) {
                createApple();
            }
        }
    }

    public void createSurprise() {

        if (eaten % 3 == 0) {
            surpriseX = new Random().nextInt(41) * DOT_SIZE;
            surpriseY = new Random().nextInt(23) * DOT_SIZE;

            if (surpriseX == 0) {
                surpriseX = 5 * DOT_SIZE;
            }

            if (surpriseY == 0) {
                surpriseY = 5 * DOT_SIZE;
            }

            for (int i = 0; i < x.length; i++) {
                if (surpriseX == x[i]) {
                    createSurprise();
                }

            }
        }
    }

    public void initLable() {
        label.setFont(buttacup);
        label.setForeground(Color.WHITE);
    }

    public void loadImage() {
        ImageIcon iibackground = new ImageIcon("background/Background.png");
        backGround = iibackground.getImage();
        ImageIcon iia = new ImageIcon("eat/apple.png");
        apple = iia.getImage();

        ImageIcon imageIconGround = new ImageIcon("background/ground.png");
        ground = imageIconGround.getImage();

        ImageIcon imageIconSurprise = new ImageIcon("eat/surprise.png");
        surprise = imageIconSurprise.getImage();

        ImageIcon imageIconBody = new ImageIcon("skins/snakeBody.png");
        bodyImage = imageIconBody.getImage();
        ImageIcon imageIconWater = new ImageIcon("background/water.png");
        waterImage = imageIconWater.getImage();

        // snake heads
        ImageIcon imageIconHeadRight = new ImageIcon("skins/snakeHeadRight.png");
        headRightImage = imageIconHeadRight.getImage();
        ImageIcon imageIconHeadLeft = new ImageIcon("skins/snakeHeadLeft.png");
        headLeftImage = imageIconHeadLeft.getImage();
        ImageIcon imageIconHeadUp = new ImageIcon("skins/snakeHeadUp.png");
        headUpImage = imageIconHeadUp.getImage();
        ImageIcon imageIconHeadDown = new ImageIcon("skins/snakeHeadDown.png");
        headDownImage = imageIconHeadDown.getImage();

        ImageIcon imageIconBlueHeadRight = new ImageIcon("skins/blueSnakeHeadRight.png");
        blueHeadRightImage = imageIconBlueHeadRight.getImage();
        ImageIcon imageIconBlueHeadLeft = new ImageIcon("skins/blueSnakeHeadLeft.png");
        blueHeadLeftImage = imageIconBlueHeadLeft.getImage();
        ImageIcon imageIconBlueHeadUp = new ImageIcon("skins/blueSnakeHeadUp.png");
        blueHeadUpImage = imageIconBlueHeadUp.getImage();
        ImageIcon imageIconBlueHeadDown = new ImageIcon("skins/blueSnakeHeadDown.png");
        blueHeadDownImage = imageIconBlueHeadDown.getImage();
        ImageIcon imageIconBlueSnakeBody = new ImageIcon("skins/blueSnakeBody.png");
        blueSnakeBodyImage = imageIconBlueSnakeBody.getImage();

    }

    public void checkSurprise() {
        if (x[0] == surpriseX && y[0] == surpriseY) {
            eaten += 10;
            scoreLabel.setForeground(Color.WHITE);
            scoreLabel.setFont(buttacup);
            scoreLabel.setText("Score " + eaten);
            rand = new Random().nextInt(5);
            initTimer(3);
            switch (rand) {
                case 1:
                    dots++;
                    break;
                case 2:
                    delay = delay - 10;
                    timer.setDelay(delay);
                    break;
                case 3:
                    if (changeSnake) {
                        changeSnake = false;
                        break;
                    }
                    changeSnake = true;
                    break;
                case 4:
                    if (changeBackground) {
                        changeBackground = false;
                        break;
                    }
                    changeBackground = true;
                    break;
                default:

            }
            createSurprise();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {

        super.paintComponent(g);
        g.drawImage(backGround, 0, 0, width, height, this);

        try {
            buttacup = Font.createFont(Font.TRUETYPE_FONT, new File("fonts/SF Buttacup.ttf")).deriveFont(30f);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("fonts/SF Buttacup.ttf")));
        } catch (FontFormatException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (inGame) {
            g.setFont(buttacup);
            g.setColor(Color.WHITE);
            switch (rand) {
                case 1:
                    initLable();
                    label.setText(actions[0]);
                    if (count == 0) {
                        label.setText(null);
                    }
                    break;
                case 2:
                    initLable();
                    label.setText(actions[1]);
                    if (count == 0) {
                        label.setText(null);
                    }
                    break;
                case 3:
                    initLable();
                    label.setText(actions[2]);
                    if (count == 0) {
                        label.setText(null);
                    }
                    break;
                case 4:
                    if (changeBackground) {
                        initLable();
                        label.setText(actions[3]);
                        if (count == 0) {
                            label.setText(null);
                        }
                        g.drawImage(ground, 0, 0, width, height, this);
                    }
                    if (!changeBackground) {
                        initLable();
                        label.setText(actions[3]);
                        if (count == 0) {
                            label.setText(null);
                        }
                        g.drawImage(backGround, 0, 0, width, height, this);
                    }
                    break;

                default:
                    initLable();
                    label.setText(actions[4]);
                    if (count == 0) {
                        label.setText(null);
                    }
            }

            if (changeSnake) {
                if (right) {
                    g.drawImage(blueHeadRightImage, x[0], y[0], 32, 32, this);
                } else if (left) {
                    g.drawImage(blueHeadLeftImage, x[0], y[0], 32, 32, this);
                } else if (up) {
                    g.drawImage(blueHeadUpImage, x[0], y[0], 32, 32, this);
                } else if (down) {
                    g.drawImage(blueHeadDownImage, x[0], y[0], 32, 32, this);
                }
                for (int i = 1; i < dots; i++) {
                    g.drawImage(blueSnakeBodyImage, x[i], y[i], 32, 32, this);
                }
            } else {
                if (right) {
                    g.drawImage(headRightImage, x[0], y[0], 32, 32, this);
                } else if (left) {
                    g.drawImage(headLeftImage, x[0], y[0], 32, 32, this);
                } else if (up) {
                    g.drawImage(headUpImage, x[0], y[0], 32, 32, this);
                } else if (down) {
                    g.drawImage(headDownImage, x[0], y[0], 32, 32, this);
                }
                for (int i = 1; i < dots; i++) {
                    g.drawImage(bodyImage, x[i], y[i], 32, 32, this);
                }
            }
            g.drawImage(apple, appleX, appleY, 32, 32, this);
            if (eaten % 3 == 0) {
                g.drawImage(surprise, surpriseX, surpriseY, 32, 32, this);
            } else g.drawImage(null, 0, 0, 32, 32, this);

            for (int i = 0; i < width; i = i + 32) {
                g.drawImage(waterImage, 0, i, 32, 32, this);
                g.drawImage(waterImage, i, 0, 32, 32, this);
                g.drawImage(waterImage, width - 32, i, 32, 32, this);
                g.drawImage(waterImage, i, height - 32, 32, 32, this);
            }

        } else {
            super.paintComponent(g);
            label.setText(null);
            gameTimer.stop();
            timerLabel.setHorizontalAlignment(JLabel.CENTER);
            timerLabel.setText("You have played " + secondsPlayed + " second(s)");
            setBackground(Color.BLACK);
            String str = "Game Over";
            String strRestart = "Space to restart";
            String strQuite = "Escape to quite";
            scoreLabel.setForeground(Color.WHITE);
            scoreLabel.setFont(buttacup);
            scoreLabel.setText("Your Score" + " is " + eaten);
            delay = 250;
            try {
                buttacup = Font.createFont(Font.TRUETYPE_FONT, new File("fonts/SF Buttacup.ttf")).deriveFont(75f);
            } catch (FontFormatException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            g.setFont(buttacup);
            //g.setColor(Color.RED);
            gameOverLable.setForeground(Color.RED);
            gameOverLable.setFont(buttacup);
            gameOverLable.setText(str);
            gameOverLable.setHorizontalAlignment(gameOverLable.CENTER);
            infoPanel.setBackground(Color.black);
            infoPanel.add(gameOverLable,BorderLayout.NORTH);
            restartLable.setForeground(Color.YELLOW);
            restartLable.setFont(buttacup);
            restartLable.setText(strRestart);
            restartLable.setHorizontalAlignment(restartLable.CENTER);
            infoPanel.add(restartLable,BorderLayout.CENTER);
            escLable.setForeground(Color.GREEN);
            escLable.setFont(buttacup);
            escLable.setText(strQuite);
            escLable.setHorizontalAlignment(restartLable.CENTER);
            infoPanel.add(escLable, BorderLayout.SOUTH);
            add(infoPanel,BorderLayout.CENTER);
            //gameOver.setHorizontalAlignment(JLabel.CENTER);

            //g.drawString(str, width / 6, (height / 2) - 100);
            //g.setColor(Color.YELLOW);
            //g.drawString(strRestart, width / 6, height / 2);
            //g.setColor(Color.GREEN);
            //g.drawString(strQuite, width / 6, (height / 2) + 100);
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
            eaten++;
            scoreLabel.setForeground(Color.WHITE);
            scoreLabel.setFont(buttacup);
            scoreLabel.setText("Score " + eaten);
            createApple();
        }

    }

    public void checkCollissions() {

        for (int i = dots; i > 0; i--) {
            if (i > 3 && x[0] == x[i] && y[0] == y[i]) {
                dots = 3;
            }
        }

        if (x[0] > SIZE - 32) {
            inGame = false;
        }
        if (x[0] < 32) {
            inGame = false;
        }
        if (y[0] > height - 64) {
            inGame = false;
        }
        if (y[0] < 32) {
            inGame = false;
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (inGame) {
            checkApple();
            checkCollissions();
            checkSurprise();
            move();
        }
        repaint();
    }

    public class FieldKeyListener extends KeyAdapter {

        @Override
        public void keyPressed(KeyEvent e) {
            super.keyPressed(e);

            int key = e.getKeyCode();

            if (key == KeyEvent.VK_ESCAPE) {
                System.exit(0);
            }

            if (key == KeyEvent.VK_SPACE) {
                inGame = true;
                right = true;
                up = false;
                left = false;
                down = false;
                scoreLabel.setText(null);
                timerLabel.setText(null);
                secondsPlayed = 0;
                eaten = 0;
                timerLabel.setHorizontalAlignment(JLabel.SOUTH_EAST);
                gameTimer.restart();
                creatSnake();
                createApple();
                createSurprise();
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
