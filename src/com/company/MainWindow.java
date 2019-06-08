package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

public class MainWindow extends JFrame {

    public JButton starGame = new JButton("Start");
    private JFrame window = new JFrame();

  /*  public void setUpWindow(){
        window.setSize(400,400);
        window.setTitle("snake");
        window.add(starGame);
        window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        starGame.addActionListener(new StartGame());
        window.addKeyListener(fieldKeyListener);
        window.setUndecorated(false);
        window.setVisible(true);
    } */

    class StartGame implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            starGame.setVisible(false);
            starGame.isDisplayable();
            //new GameField().requestFocusInWindow();
            window.setTitle("Змейка");
            window.add(new GameField(),BorderLayout.CENTER);
           // new GameField().setFocusable(true);
            window.setExtendedState(JFrame.MAXIMIZED_BOTH);

            window.setCursor(getToolkit().createCustomCursor(
                    new BufferedImage(3, 3, BufferedImage.TYPE_INT_ARGB), new Point(0, 0),
                    "null"));
            window. setVisible(true);
        }
    }

    public void setUpMainWindow() {

        window.setSize(320, 345);
        window.setLocation((1366/2)-150,(768/2)-150);
        window.getContentPane().add(starGame,BorderLayout.CENTER);
        starGame.setSize(200,200);
        starGame.addActionListener(new StartGame());
        window.setUndecorated(true);
        window.pack();
       // window.setCursor(getToolkit().createCustomCursor(
           //     new BufferedImage(3, 3, BufferedImage.TYPE_INT_ARGB), new Point(0, 0),
            //    "null"));
        window.setVisible(true);
    }

  /*  public MainWindow(){
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setUndecorated(true);
        setTitle("Змейка");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(320,345);
        //add(new GameField(),BorderLayout.CENTER);
        add(starGame);
        starGame.addActionListener(new StartGame());
        setCursor(getToolkit().createCustomCursor(
                new BufferedImage(3, 3, BufferedImage.TYPE_INT_ARGB), new Point(0, 0),
                "null"));
        setVisible(true);
    } */


    public static void main(String[] args) {
        MainWindow mw = new MainWindow();
        mw.setUpMainWindow();
        // mw.setUpWindow();
        /*Game g = new Game();
        g.setUpGame();*/
    }
}
