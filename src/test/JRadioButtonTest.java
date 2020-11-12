package com.example.demo.src;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * @author zhangjiaru
 * @date: 2020/11/10
 */
public class JRadioButtonTest {
    JFrame jFrame = null;
    
    JRadioButtonTest() {
        jFrame = new JFrame("单选框");
        Container container = jFrame.getContentPane();
        container.setLayout(new FlowLayout());
        JPanel jPanel = new JPanel();
        jPanel.setLayout(new GridLayout(3, 3));
        jPanel.setBorder(BorderFactory.createTitledBorder("选择你喜欢的城市"));
        //定义三个JRadio单选按钮
        JRadioButton jRadioButton1 = new JRadioButton("北京");
        JRadioButton jRadioButton2 = new JRadioButton("上海");
        JRadioButton jRadioButton3 = new JRadioButton("广州");
        ButtonGroup buttonGroup = new ButtonGroup();
        buttonGroup.add(jRadioButton1);
        buttonGroup.add(jRadioButton2);
        buttonGroup.add(jRadioButton3);
        jPanel.add(jRadioButton1);
        jPanel.add(jRadioButton2);
        jPanel.add(jRadioButton3);
        jRadioButton1.setSelected(true);
        container.add(jPanel);
        jFrame.pack();
        jFrame.setVisible(true);
        jFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        
    }
    
    public static void main(String[] args) {
        new JRadioButtonTest();
    }
}
