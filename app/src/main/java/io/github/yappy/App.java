package io.github.yappy;

import java.awt.Dimension;
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
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        AppletMod applet = MccMod.constructAppletMod();
        putDefaultParameters(applet);
        applet.init();
        applet.start();

        frame.setLocationByPlatform(true);
        applet.setPreferredSize(new Dimension(512, 320));
        frame.add(applet);
        frame.pack();
        frame.setVisible(true);
    }
}
