package com.example.demo.src;

import javax.swing.*;
import java.awt.*;

/**
 * @author zhangjiaru
 * @date: 2020/11/10
 */
public class Sprite {
    
    public int m_posX = 0, m_posY = 0;
    
    private Image[] pic = null;
    
    private int mPlayID = 0;
    
    boolean mFacus = true;
    
    public Sprite() {
        pic = new Image[4];
        for (int i = 0; i < 4; i++) {
            pic[i] = Toolkit.getDefaultToolkit().getImage(Sprite.class.getClassLoader().getResource("image/timg.jpeg"));
        }
    }
    
    public void init(int x, int y) {
        m_posX = x;
        m_posY = y;
    }
    
    public void set(int x, int y) {
        m_posX = x;
        m_posY = y;
    }
    
    public void drawSprite(Graphics g, JPanel i) {
        g.drawImage(pic[mPlayID], m_posX, m_posY, i);
        mPlayID++;
        if (mPlayID == 4) {
            mPlayID = 0;
        }
    }
    
    public void updateSprite() {
        if (mFacus) {
            m_posX += 15;
        }
        if (m_posX == 300) {
            m_posX = 0;
        }
        System.out.println(m_posX);
    }
}
