package com.example.demo.src.mahjong;

import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;

/**
 * @author zhangjiaru
 * @date: 2020/11/11
 */
public class FrogPanel extends JPanel implements MouseListener {
    
    public Card[] m_aCards; //牌数组
    public int m_nScore; //当前积分
    public Card m_LastCard; //用户上次选定的牌
    //
    public ArrayList<Card>[] playersCard = new ArrayList[2];
    //
    public ArrayList<Card>[] playersOutCard = new ArrayList[2];
    private int k = 0; //发牌个数
    private Card playerSelectCard; //用户选定的牌
    private Boolean myTurn = false; //是否该玩家出牌
    private int order = 0; //轮到谁出牌
    private Card justOutCard; //上家刚出的牌
    private Image image;//背景图片
    Timer time1;
    Timer time2;
    JButton getBtn = new JButton(); //摸牌按钮
    JButton pengBtn = new JButton(); //碰按钮
    JButton chiBtn = new JButton(); //吃按钮
    JButton outBtn = new JButton();//出牌按钮
    JButton winBtn = new JButton();//
    
    
    public void gameMain() {
        loadCards();
        resetGame();
        this.setLayout(null);
        getBtn.setText("摸牌");
        getBtn.setSize(70, 27);
        pengBtn.setText("碰牌");
        pengBtn.setSize(70, 27);
        chiBtn.setText("吃牌");
        chiBtn.setSize(70, 27);
        outBtn.setText("出牌");
        outBtn.setSize(70, 27);
        winBtn.setText("和牌");
        winBtn.setSize(70, 27);
        this.add(getBtn);
        getBtn.setLocation(500, 600);
        this.add(pengBtn);
        pengBtn.setLocation(600, 600);
        this.add(chiBtn);
        chiBtn.setLocation(700, 600);
        this.add(outBtn);
        outBtn.setLocation(800, 600);
        this.add(winBtn);
        winBtn.setLocation(900, 600);
        getBtn.setVisible(false);
        pengBtn.setVisible(false);
        chiBtn.setVisible(false);
        outBtn.setVisible(false);
        winBtn.setVisible(false);
        getBtn.addActionListener(e -> onBtnGetClick());//摸牌点击
        outBtn.addActionListener(e -> {//出牌点击
            if (playerSelectCard != null) {
                outBtn.setVisible(false);
                playersOutCard[0].add(playerSelectCard);
                playerSelectCard.x = playersOutCard[0].size() * 25 - 25;//移动被选中的牌
                playerSelectCard.y = 420;
                playerSelectCard.moveTo(playerSelectCard.x, playerSelectCard.y);
                outCardOrder(playersOutCard[0]);
                playersCard[0].remove(playerSelectCard);
                m_LastCard = null;
                playerSelectCard = null;
                order++;
                myTurn = false;
            }
        });
        chiBtn.addActionListener(e -> {//吃牌点击
            Card card = playersOutCard[1].get(playersOutCard[1].size() - 1);
            card.moveTo(90 + 55 * 13, 500);
            card.setFront(true);
            playersCard[0].add(card);
            cardAddMouseListener(card);
            
            sortPoker2(playersCard[0]);
            Boolean result = computerCardNum(playersCard[0]);
            if (result) {
                getBtn.setVisible(false);
                pengBtn.setVisible(false);
                chiBtn.setVisible(false);
                return;
            }
            getBtn.setVisible(false);
            pengBtn.setVisible(false);
            chiBtn.setVisible(false);
            outBtn.setVisible(true);
        });
        pengBtn.addActionListener(e -> {
            Card card = playersOutCard[1].get(playersOutCard[1].size() - 1);
            card.moveTo(90 + 55 * 13, 500);
            card.setFront(true);
            playersCard[0].add(card);
            cardAddMouseListener(card);
            
            sortPoker2(playersCard[0]);
            Boolean result = computerCardNum(playersCard[0]);
            if (result) {
                getBtn.setVisible(false);
                pengBtn.setVisible(false);
                chiBtn.setVisible(false);
                return;
            }
            getBtn.setVisible(false);
            pengBtn.setVisible(false);
            chiBtn.setVisible(false);
            outBtn.setVisible(true);
        });
    }
    
    private void outCardOrder(ArrayList cards) {//整理出过的牌Z轴深度
        for (int i = 0; i < cards.size(); i++) {
            this.setComponentZOrder((Component) cards.get(i), 0);
        }
    }
    
    //摸牌按钮事件,将卡组中的牌移动到玩家手中
    private void onBtnGetClick() {
        m_aCards[k].moveTo(90 + 55 * 13, 500);
        m_aCards[k].setFront(true);
        playersCard[0].add(m_aCards[k]);
        cardAddMouseListener(m_aCards[k]);
        sortPoker2(playersCard[0]);
        Boolean result1 = computerCardNum(playersCard[0]);
        if (result1) {
            getBtn.setVisible(false);
            pengBtn.setVisible(false);
            chiBtn.setVisible(false);
            return;
        }
        k++;
        getBtn.setVisible(false);
        pengBtn.setVisible(false);
        chiBtn.setVisible(false);
        outBtn.setVisible(true);
    }
    
    
    //计算手中各种牌型的数量
    private Boolean computerCardNum(ArrayList cards) {
        int i;
        Card card = null;
        int[][] paiArray = {{0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0}};
        for (int j = 0; j < 13; j++) {
            card = (Card) cards.get(j);
            if (card.getImageID() > 10 && card.getImageID() < 20) {//筒
                paiArray[0][0] += 1;
                paiArray[0][card.getImageID() - 10] += 1;
            }
            if (card.getImageID() > 20 && card.getImageID() < 30) {//条
                paiArray[1][0] += 1;
                paiArray[1][card.getImageID() - 20] += 1;
            }
            if (card.getImageID() > 30 && card.getImageID() < 40) {//万
                paiArray[2][0] += 1;
                paiArray[2][card.getImageID() - 30] += 1;
            }
            if (card.getImageID() > 40 && card.getImageID() < 50) {//字
                paiArray[3][0] += 1;
                paiArray[3][card.getImageID() - 40] += 1;
            }
        }
        HuMain huMain = new HuMain();
        Boolean result1 = huMain.win(paiArray);//判断是否和牌
        if (result1) {
            JOptionPane.showMessageDialog(null, "你和牌了！", "提示", JOptionPane.OK_OPTION);
            time2.stop();
            for (int j = 0; j < playersCard[0].size(); j++) {
                m_aCards[j].removeActionListener(null);
            }
        }
        return result1;
    }
    
    private void cardAddMouseListener(Card card) {
        card.addMouseListener(this);
    }
    
    public void loadCards() {
        m_aCards = new Card[136];
        int type = 0, num = 0, n, count = 0;
        Card card;
        
        for (type = 1; type <= 3; type++) { //饼、条、万
            for (num = 1; num <= 9; num++) { //1-9
                for (n = 1; n <= 4; n++) { //4张牌
                    card = new Card(type, num);
                    this.add(card); //添加到panel场景
                    m_aCards[count] = card; //添加牌到数组
                    count++;
                }
            }
        }
        type = 4; //字牌
        for (num = 1; num <= 7; num++) {
            for (n = 1; n <= 4; n++) {
                card = new Card(type, num); //创建字牌
                this.add(card);
                m_aCards[count] = card;
                count++;
            }
        }
    }
    
    public void resetGame() {
        exchangeCards();
        //开始发牌
        time1 = new Timer(50, new TimerListener());
        time1.start();
        time2 = new Timer(1000, new TimerListener2());
        m_LastCard = null;
        justOutCard = null;
        playersCard[0] = new ArrayList<>();
        playersCard[1] = new ArrayList<>();
        
        playersOutCard[0] = new ArrayList<>();
        playersOutCard[1] = new ArrayList<>();
        pengBtn.setVisible(false);
        chiBtn.setVisible(false);
        m_nScore = 0;
    }
    
    public void exchangeCards() {
        int i, j, n;
        int num = 50;//洗50次
        Card temp;
        Random rd1 = new Random();
        for (n = 0; n < num; n++) {
            //随机取两张牌
            i = rd1.nextInt(136);
            j = rd1.nextInt(136);
            //交换
            temp = m_aCards[i];
            m_aCards[i] = m_aCards[j];
            m_aCards[j] = temp;
        }
        for (n = 0; n < m_aCards.length; n++) {
            System.out.println(m_aCards[n]);
            m_aCards[n].x = 90 + 20 * (n % 34);
            m_aCards[n].y = 170 + 55 * (n - n % 34) / 34;
            m_aCards[n].moveTo(m_aCards[n].x, m_aCards[n].y);
            this.setComponentZOrder(m_aCards[n], 0);
            m_aCards[n].setFront(false);
        }
    }
    
    public void shift() {
        int i, j;
        i = k % 2;
        j = (k - k % 2) / 2;
        switch (i) {
            case 0: //玩家
                m_aCards[k].setFront(true);
                m_aCards[k].moveTo(90 + 55 * j, 500);
                m_aCards[k].addMouseListener(this);
                break;
            case 1: //cpu1
                m_aCards[k].setFront(false);
                //m_aCards[k].rotation = 180;
                m_aCards[k].moveTo(90 + 55 * j, 80);
                break;
        }
        playersCard[(k % 2)].add(m_aCards[k]);//分别记录两个牌手的牌数组
        k++;
        if (k == 26) {
            time1.stop();
            sortPoker2(playersCard[0]);
            sortPoker2(playersCard[1]);
            getBtn.setVisible(true);//出牌按钮可见
            time2.start();//开始游戏逻辑
        }
    }
    
    public void sortPoker2(ArrayList cards) {//按花色理牌手中的牌
        int index, n, newx, y;
        Card temp;
        n = cards.size();
        //排序
        Collections.sort(cards, new SortByImageID());
        for (index = 0; index < n; index++) {//重设各张牌在场景的位置
            newx = 90 + 55 * index;
            y = ((Card) cards.get(index)).getY();
            ((Card) cards.get(index)).moveTo(newx, y);
            ((Card) cards.get(index)).cardID = index;
        }
        
    }
    
    class SortByImageID implements Comparator {
        
        @Override
        public int compare(Object o1, Object o2) {
            Card s1 = (Card) o1;
            Card s2 = (Card) o2;
            if (s1.getImageID() > s2.getImageID()) {
                return 1;
            }
            return 0;
        }
    }
    
    
    @Override
    public void mouseClicked(MouseEvent e) {
        int i;
        if (!myTurn || getBtn.isShowing()) {
            return;
        }
        Card card = null;
        for (i = 0; i < playersCard[0].size(); i++) {
            if (playersCard[0].get(i) == e.getSource()) {
                card = playersCard[0].get(i);
                System.out.println("单击的是" + card.m_nType + card.m_nNum);
            }
        }
        if (card == null) {
            return;
        }
        if (card.m_bFront) {
            card.moveTo(card.getX(), card.getY() - 20);
            card.y -= 20;
        }
        if (m_LastCard == null) {
            m_LastCard = card;
            playerSelectCard = card;
        } else {
            m_LastCard.moveTo(m_LastCard.getX(), m_LastCard.getY() + 20);
            m_LastCard.y += 20;
            m_LastCard = card;
            playerSelectCard = card;
        }
    }
    
    private void fun2() {
        if (order == 0) {
            if (!myTurn) {
                myTurn = true;
                getBtn.setVisible(true);
                if (playersOutCard[1].size() > 0) {
                    //取计算机刚出的牌
                    Card card = playersOutCard[1].get(playersOutCard[1].size() - 1);
                    //判断玩家是否可以吃碰
                    if (canPeng(playersCard[0], card)) {//是否可以碰
                        pengBtn.setVisible(true);
                    }
                    if (canChi(playersCard[0], card)) {//是否可以吃
                        chiBtn.setVisible(true);
                    }
                    if (!canChi(playersCard[0], card) && !canPeng(playersCard[0], card)) {
                        pengBtn.setVisible(false);
                        chiBtn.setVisible(false);
                    }
                } else {
                    //直接摸牌
                }
            }
            return;
        }
        computerOut(order);//计算机出牌
        order = 0;
    }
    
    private boolean canChi(ArrayList<Card> cards, Card card) {
        int i, n;
        n = 0;
        Card c1, c2;
        for (i = 0; i < cards.size() - 1; i++) {
            c1 = cards.get(i);
            c2 = cards.get(i + 1);
            if (c1.m_nNum == card.m_nNum + 1 && c1.m_nType == card.m_nType
                    && c2.m_nNum == card.m_nNum + 2 && c2.m_nType == card.m_nType) {
                return true;
            }
        }
        for (i = 0; i < cards.size() - 1; i++) {
            c1 = cards.get(i);
            c2 = cards.get(i + 1);
            if (c1.m_nNum == card.m_nNum - 1 && c1.m_nType == card.m_nType
                    && c2.m_nNum == card.m_nNum + 1 && c2.m_nType == card.m_nType) {
                return true;
            }
        }
        for (i = 0; i < cards.size() - 1; i++) {
            c1 = cards.get(i);
            c2 = cards.get(i + 1);
            if (c1.m_nNum == card.m_nNum - 2 && c1.m_nType == card.m_nType
                    && c2.m_nNum == card.m_nNum - 1 && c2.m_nType == card.m_nType) {
                return true;
            }
        }
        return false;
    }
    
    private boolean canPeng(ArrayList<Card> cards, Card card) {
        int i, n;
        n = 0;
        Card c;
        for (int j = 0; j < cards.size(); j++) {
            c = cards.get(j);
            if (c.getImageID() == card.getImageID()) {
                n++;
            }
        }
        if (n >= 2) {
            return true;
        }
        return false;
    }
    
    private void computerOut(int order) {
        int i;
        //计算机摸牌
        m_aCards[k].moveTo(90 + 55 * 13, 80);
        m_aCards[k].setFront(false);
        playersCard[1].add(m_aCards[k]);
        
        boolean result = computerCardNum(playersCard[1]);
        if (result) {
            return;
        }
        i = computerCard(playersCard[1]);//智能出牌
        //i =0;
        Card card = playersCard[1].remove(i);
        //加到计算机出过牌的数组
        playersOutCard[1].add(card);
        //整理
        outCardOrder(playersOutCard[1]);
        card.setFront(true);
        //判断计算机的牌选择声音文件
        String music = "";//todo
        music = "" + toChineseNumString(card.m_nNum);
        switch (card.m_nType) {
            case 1://
                music += "饼.wav";
                break;
            case 2://
                music += "条.wav";
                break;
            case 3://
                music += "万.wav";
                break;
            case 4://字牌
                music += "res/sound/give.wav";
                break;
        }
        // playMusic(music);
        sortPoker2(playersCard[1]);
        card.x = playersOutCard[1].size() * 25 - 25;
        card.y = 10;
        card.moveTo(card.x, card.y);
        k++; //发过牌的总数
        order++;
        if (order == 2) {
            order = 0;
        }
    }
    
    /**
     * 计算机智能出牌
     *
     * @param cards
     * @return
     */
    private int computerCard(ArrayList<Card> cards) {
        int i, j, k;
        int[][] paiArray = {{0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0}};
        Card card = null;
        for (i = 0; i < 13; i++) {
            card = cards.get(i);
            if (card.getImageID() > 10 && card.getImageID() < 20) {//饼
                paiArray[0][0] += 1;
                paiArray[0][card.getImageID() - 10] += 1;
            }
            if (card.getImageID() > 20 && card.getImageID() < 30) {//条
                paiArray[1][0] += 1;
                paiArray[1][card.getImageID() - 20] += 1;
            }
            if (card.getImageID() > 30 && card.getImageID() < 40) {//万
                paiArray[2][0] += 1;
                paiArray[2][card.getImageID() - 30] += 1;
            }
            if (card.getImageID() > 40 && card.getImageID() < 50) {//字
                paiArray[3][0] += 1;
                paiArray[3][card.getImageID() - 40] += 1;
            }
        }
        System.out.println(paiArray);
        //计算机智能选牌
        //1.判断字牌的单张，有则找到
        for (j = 1; j < 10; j++) {
            if (paiArray[3][j] == 1) {
                //获取在手中牌的位置下标
                k = computerSelectCard(cards, 4, j);
                return k;
            }
        }
        //2.判断顺子、刻字（3张相同的）
        for (i = 0; i < 3; i++) {
            for (j = 1; j < 10; j++) {
                if (paiArray[i][j] >= 3) {
                    paiArray[i][j] -= 3;
                }
                if (j <= 7 && paiArray[i][j] >= 1 && paiArray[i][j + 1] >= 1 && paiArray[i][j + 2] >= 1) {
                    paiArray[i][j] -= 1;
                    paiArray[i][j + 1] -= 1;
                    paiArray[i][j + 2] -= 1;
                }
            }
        }
        //3。判断单张非字牌，有则找到
        for (i = 0; i < 3; i++) {
            for (j = 0; j < 10; j++) {
                if (paiArray[i][j] == 1) {
                    k = computerSelectCard(cards, i + 1, j);
                    return k;
                }
            }
        }
        //4.判断两张牌(饼条万、包括字),有则找到,拆双牌
        for (i = 3; i >= 0; i--) {
            if (paiArray[i][j] == 2) {
                k = computerSelectCard(cards, i + 1, j);
                return k;
            }
        }
        //5.如果以上情况距没有出现则随机选出1张牌
        Random rd1 = new Random();
        k = rd1.nextInt(14);//随机选出一张牌
        return k;
    }
    
    private int computerSelectCard(ArrayList<Card> cards, int type, int num) {
        for (int i = 0; i < cards.size(); i++) {
            if (cards.get(i).m_nType == type && cards.get(i).m_nNum == num) {
                return cards.get(i).cardID;
            }
        }
        return 0;
    }
    
    private void playMusic(String music) {
        try {
            FileInputStream file = new FileInputStream(music);
            AudioStream as = new AudioStream(file);
            AudioPlayer.player.start(as);
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }
    
    private String toChineseNumString(int n) {
        String music = "";
        switch (n) {
            case 1:
                music = "一";
                break;
            case 2:
                music = "二";
                break;
            case 3:
                music = "三";
                break;
            case 4:
                music = "四";
                break;
            case 5:
                music = "五";
                break;
            case 6:
                music = "六";
                break;
            case 7:
                music = "七";
                break;
            case 8:
                music = "八";
                break;
            case 9:
                music = "九";
                break;
        }
        return music;
    }
    
    public void onBtnNewCLick() {
        if (m_aCards != null) {
            //从舞台删除所有的牌
            int n = m_aCards.length;
            for (int i = 0; i < n; i++) {
                this.remove(m_aCards[i]);//从panel移除
                m_aCards[i] = null;
            }
        }
        playersCard = null;
        m_aCards = null;
        playersOutCard = null;
        k = 0;
        myTurn = false;
        if (time2 != null) {
            time2.stop();
        }
        playersCard = new ArrayList[2];
        playersOutCard = new ArrayList[2];
        gameMain();
        getBtn.setVisible(false);
        outBtn.setVisible(false);
        winBtn.setVisible(false);
    }
    
    @Override
    public void mousePressed(MouseEvent e) {
        
    }
    
    @Override
    public void mouseReleased(MouseEvent e) {
        
    }
    
    @Override
    public void mouseEntered(MouseEvent e) {
        
    }
    
    @Override
    public void mouseExited(MouseEvent e) {
        
    }
    
    public static void main(String[] args) {
        Random r = new Random();
        System.out.println(r.nextInt(1));
        ;
    }
    
    private class TimerListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            shift();//发牌
        }
    }
    
    private class TimerListener2 implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            fun2();
        }
    }
}
