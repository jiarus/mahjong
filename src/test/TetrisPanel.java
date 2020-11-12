package com.example.demo.src;

import lombok.SneakyThrows;

import javax.swing.*;
import java.awt.*;

/**
 * @author zhangjiaru
 * @date: 2020/11/10
 */
public class TetrisPanel extends JPanel implements Runnable {
    
    @SneakyThrows
    @Override
    public void run() {
        while (true) {
            Thread.sleep(10);
            ypos += 5;
            if (ypos > 300) {
                ypos = -80;
            }
            repaint();
        }
    }
    
    private int ypos = -80;
    
    public TetrisPanel() {
        Thread t = new Thread(this);
        t.start();
    }
    
    private Image iBuffer;
    
    private Graphics gBuffer;
    
    @Override
    public void paint(Graphics g) {
        if (iBuffer == null) {
            iBuffer = createImage(this.getSize().width, this.getSize().height);
            gBuffer = iBuffer.getGraphics();
        }
        gBuffer.setColor(getBackground());
        gBuffer.fillRect(0, 0, this.getSize().width, this.getSize().height);
        gBuffer.setColor(Color.RED);
        gBuffer.fillOval(90, ypos, 80, 80);
        g.drawImage(iBuffer, 0, 0, this);
//        super.paint(g);
//        g.setColor(Color.RED);
//        g.fillOval(90, ypos, 80, 80);
    }
}
