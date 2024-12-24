package io.github.yappy;

import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

import javax.swing.JFrame;

import com.google.common.io.Resources;

import io.github.yappy.mccport.AppletMod;
import io.github.yappy.mccport.MccMod;

public class App {

    private static void putDefaultParameters(AppletMod applet) throws IOException {
        String src = Resources.toString(Resources.getResource("defaultparam.txt"), StandardCharsets.UTF_8);

        List<String> lines = src.lines().toList();
        int idx = 0;
        String key = null;
        for (var line : lines) {
            switch (idx) {
                case 1:
                    key = line;
                    break;
                case 2:
                    applet.setParameter(key, line);
                    break;
            }
            idx = (idx + 1) % 3;
        }

        // map
        String defmap = ".".repeat(60);
        for (int x = 0; x < 3; x++) {
            for (int y = 0; y < 30; y++) {
                applet.setParameter("map" + x + "-" + y, defmap);
            }
        }
        applet.setParameter("map0-29", ".".repeat(10) + "a".repeat(50));
    }

    public static void main(String[] args) throws Exception {
        JFrame frame = new JFrame("JFrame - Masao Construction");
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.repaint();

        AppletMod applet = MccMod.constructAppletMod();
        putDefaultParameters(applet);

        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowOpened(WindowEvent e) {
                applet.startup();
            }
            @Override
            public void windowClosing(WindowEvent windowEvent) {
                applet.shutdown();
                frame.dispose();
            }
        });

        frame.setLocationByPlatform(true);
        applet.setPreferredSize(new Dimension(512, 320));
        frame.add(applet);
        frame.pack();
        frame.setVisible(true);
    }
}
