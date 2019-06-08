package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

public class MainWindow extends JFrame {

    public JButton startGame = new JButton("Start");
    private JFrame window = new JFrame();

    public void setUpMainWindow() {
        window.setSize(320, 345);
        window.setLocation((1366/2)-150,(768/2)-150);
        window.getContentPane().add(startGame,BorderLayout.CENTER);
        //starGame.setSize(200,200);
        startGame.addActionListener(new StartGame());
        window.setUndecorated(true);
        window.pack();
        window.setVisible(true);
    }

    class StartGame implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            startGame.setVisible(false);
            startGame.isDisplayable();
            window.setTitle("Змейка");
            window.add(new GameField(),BorderLayout.CENTER);
            window.setExtendedState(JFrame.MAXIMIZED_BOTH);
            window.setCursor(getToolkit().createCustomCursor(
                    new BufferedImage(3, 3, BufferedImage.TYPE_INT_ARGB), new Point(0, 0),
                    "null"));
        }
    }

    public static void main(String[] args) {
        MainWindow mw = new MainWindow();
        mw.setUpMainWindow();
    }
}
