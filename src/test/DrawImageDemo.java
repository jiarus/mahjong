package com.example.demo.src;

import javax.swing.*;
import java.awt.*;

/**
 * @author zhangjiaru
 * @date: 2020/11/10
 */
public class DrawImageDemo extends JFrame {
    
    public DrawImageDemo(){
        super();
        setTitle("Draw....");
        setSize(300, 300);
        setVisible(true);
    }
    
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.setColor(Color.RED);
        //绘制图形
        g.drawLine(10, 30, 5 + getWidth() / 2 - 50, 30 + getHeight() / 2 - 50);
        g.drawRect(10, 30, getWidth() / 2 - 50, getHeight() / 2 - 50);
        g.drawOval(10, 30, getWidth() / 2 - 50, getHeight() / 2 - 50);
        //填充颜色
        g.setColor(Color.lightGray);
        g.fill3DRect(10, 180, getWidth() / 2 - 50, getHeight() / 2 - 50, true);
        g.fillRoundRect(130, 30, getWidth() / 2 - 50, getHeight() / 2 - 50, 30, 40);
        g.fillOval(130, 180, getWidth() / 2, getHeight() / 2 - 50);
        
    }
    
    public static void main(String[] args) {
        DrawImageDemo drawImageDemo = new DrawImageDemo();
        drawImageDemo.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
