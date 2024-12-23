package io.github.yappy;

import java.awt.Dimension;
import javax.swing.JFrame;

import io.github.yappy.mccport.AppletMod;
import io.github.yappy.mccport.MccMod;

public class App {

    public static void main(String[] args) throws Exception {
        JFrame frame = new JFrame("Masao Construction");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        AppletMod applet =  MccMod.constructAppletMod();
        applet.init();
        applet.start();
        applet.setPreferredSize(new Dimension(512, 320));
        frame.add(applet);
        frame.pack();
        frame.setVisible(true);
    }
}
