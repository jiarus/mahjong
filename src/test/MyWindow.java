package com.example.demo.src;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * @author zhangjiaru
 * @date: 2020/11/10
 */
public class MyWindow extends JFrame {
    MyWindow() {
        this.setTitle("标题");
        Container c = this.getContentPane();
        //c.add(new TetrisPanel());
        c.add(new SpritePanel());
        this.setBounds(400, 200, 300, 300);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setVisible(true);
    }
    
    public static void main(String[] args) {
        MyWindow m = new MyWindow();
        m.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
    }
}
