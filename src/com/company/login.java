package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.Color;
import java.awt.Container;
import javax.swing.JFrame;

public class login {
    public static void main(String args[] )
    {

        //Creating a frame
        loginclass f = new loginclass();
        //seetting frame visibilaty
        f.setTitle("Card2Cart");
        f.setVisible(true);
        f.setSize(360,640);
        f.setLocation(200,200);
        //Background color
        Container c=f.getContentPane();
        c.setBackground(Color.ORANGE);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setResizable(true);

    }
}
