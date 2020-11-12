package com.example.demo.src.mahjong;

import javax.swing.*;

/**
 * @author zhangjiaru
 * @date: 2020/11/10
 */
public class Card extends JButton {
    
    //正面标志
    public Boolean m_bFront;
    
    //牌类型 1饼、2条、3万、4字牌
    public int m_nType;
    //牌点数 1-9
    public int m_nNum;
    //牌图片路径
    private String frontURL;
    //图片编号
    private int imageID;
    //牌在数组的索引
    public int cardID;
    //牌的坐标
    public int x, y;
    
    public Card(int type, int num) {
        m_nType = type;
        m_nNum = num;
        switch (m_nType) {
            case 1:
                frontURL = "image/1";
                break;
            case 2:
                frontURL = "image/2";
                break;
            case 3:
                frontURL = "image/3";
                break;
            case 4:
                frontURL = "image/4";
                break;
        }
        imageID = m_nType * 10 + num;
        frontURL = frontURL + m_nNum + ".png";
        showPic(frontURL);
        this.m_bFront = true;
        this.setSize(89, 105);
        this.x = getX();
        this.y = getY();
    }
    
    public void setFront(Boolean b) {
        this.m_bFront = b;
        if (b) {
            showPic(frontURL);
        } else {
            showPic("image/48.jpeg");
           
        }
    }
    
    private void showPic(String frontURL) {
        //System.out.println(frontURL);
        Icon icon = new ImageIcon(Card.class.getClassLoader().getResource(frontURL));
        setIcon(icon);
    }
    
    public void moveTo(int x1, int y1) {
        this.setLocation(x1, y1);
    }
    
    @Override
    public int getX() {
        return this.getBounds().x;
    }
    
    @Override
    public int getY() {
        return this.getBounds().y;
    }
    
    public int getImageID() {
        return imageID;
    }
    
    public static void main(String[] args) {
        Icon icon = new ImageIcon(Card.class.getClassLoader().getResource("image/48.jpeg"));
    }
    
}
