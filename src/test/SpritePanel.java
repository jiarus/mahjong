package com.example.demo.src;

import lombok.SneakyThrows;

import javax.swing.*;
import java.awt.*;

/**
 * @author zhangjiaru
 * @date: 2020/11/10
 */
public class SpritePanel extends JPanel implements Runnable {
    
    private Sprite player;
    
    @SneakyThrows
    @Override
    public void run() {
        while (true) {
            player.updateSprite();
            Thread.sleep(300);
            repaint();
        }
    }
    
    SpritePanel() {
        player = new Sprite();
        Thread t = new Thread(this);
        t.start();
    }
    
    @Override
    public void paint(Graphics g) {
//        super.paint(g);
        g.clearRect(0, 0, this.getWidth(), this.getHeight());
        player.drawSprite(g, this);
    }
}
