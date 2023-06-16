package sk.stuba.fei.uim.oop.settings;

import sk.stuba.fei.uim.oop.controls.GameLogic;

import javax.swing.*;
import java.awt.*;

public class Game {

    public Game() throws HeadlessException {
        JFrame frame = new JFrame(" -- WATERPIPES --");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, 1000);


        frame.setResizable(false);
        frame.setFocusable(true);
        frame.requestFocusInWindow();

        GameLogic logic = new GameLogic(frame);

        frame.addKeyListener(logic);
        JPanel sideMenu = new JPanel();
        sideMenu.setBackground(new Color(50, 170, 160));

        JButton buttonRestart = new JButton("RESTART");
        buttonRestart.setBackground(new Color(210, 70, 50));
        buttonRestart.addActionListener(logic);
        buttonRestart.setFocusable(false);

        JButton correct = new JButton("CHECK");
        correct.setBackground(new Color(200, 80, 50));
        correct.addActionListener(logic);
        correct.setFocusable(false);

        JSlider slider = new JSlider(JSlider.HORIZONTAL, 8, 12, 8);
        slider.setMinorTickSpacing(1);
        slider.setMajorTickSpacing(1);
        slider.setSnapToTicks(true);

        slider.setPaintTicks(true);
        slider.setPaintLabels(true);
        slider.addChangeListener(logic);
        slider.setFocusable(false);

        sideMenu.setLayout(new GridLayout(2, 2));
        sideMenu.add(logic.getLabel());

        sideMenu.add(slider);
        sideMenu.add(buttonRestart);
        sideMenu.add(correct);

        sideMenu.add(logic.getBoardSizeLabel());
        frame.add(sideMenu, BorderLayout.PAGE_START);

        //frame.setFocusable(true)
        frame.setVisible(true);
    }
}
