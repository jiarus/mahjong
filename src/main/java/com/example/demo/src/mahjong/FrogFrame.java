package com.example.demo.src.mahjong;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author zhangjiaru
 * @date: 2020/11/11
 */
public class FrogFrame extends JFrame {
    
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                FrogFrame frogFrame = new FrogFrame();
                frogFrame.setVisible(true);
            }
        });
    }
    
    public FrogFrame() {
        super();
        getContentPane().setLayout(new BorderLayout());
        setTitle("麻将馆");
        setBounds(50, 50, 1400, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        final JPanel panel = new JPanel();
        getContentPane().add(panel, BorderLayout.SOUTH);
        final FrogPanel gamePanel = new FrogPanel();//游戏面板
        getContentPane().add(gamePanel, BorderLayout.CENTER);
        final JButton startButton = new JButton();
        startButton.setText("开始");
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gamePanel.onBtnNewCLick();
                //gamePanel.gameMain();
                gamePanel.repaint();
            }
        });
        panel.add(startButton);
    }
    
    
}
