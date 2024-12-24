package io.github.yappy;

import java.awt.BorderLayout;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import com.google.common.io.Resources;

import io.github.yappy.mccport.AppletMod;
import io.github.yappy.mccport.MccMod;

public class MainFrame extends JFrame {

    private static final String SUPPORT_URL = "https://github.com/yappy/MasaoMakerSP";

    private JPanel gamePanel;
    private AppletMod appletMod = null;

    public MainFrame() {
        super("Masao Construction Desktop");
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                onWindowClosing(e);
            }
        });
        setLocationByPlatform(true);

        setJMenuBar(createMenuBar());

        gamePanel = new JPanel(new BorderLayout());
        gamePanel.setPreferredSize(new Dimension(MccMod.MC_APPLET_W, MccMod.MC_APPLET_H));
        add(gamePanel);
        pack();
    }

    private JMenuBar createMenuBar() {
        var menuBar = new JMenuBar();
        JMenu menu;
        JMenuItem item;

        menu = new JMenu("ゲーム (G)");
        menu.setMnemonic(KeyEvent.VK_G);
        menuBar.add(menu);

        item = new JMenuItem("開始 (S)", KeyEvent.VK_S);
        item.addActionListener(this::actionStart);
        menu.add(item);

        menu.addSeparator();

        item = new JMenuItem("終了 (X)", KeyEvent.VK_X);
        item.addActionListener(this::actionExit);
        menu.add(item);

        menu = new JMenu("ヘルプ (H)");
        menu.setMnemonic(KeyEvent.VK_H);
        menuBar.add(menu);

        item = new JMenuItem("サポートサイト (S)", KeyEvent.VK_S);
        item.addActionListener(this::actionSite);
        menu.add(item);

        menu.addSeparator();

        item = new JMenuItem("このソフトウェアについて (A)", KeyEvent.VK_A);
        item.addActionListener(this::actionAbout);
        menu.add(item);

        return menuBar;
    }

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
                applet.setParameter(String.format("map%d-%d", x, y), defmap);
            }
        }
        applet.setParameter("map0-29", ".".repeat(10) + "a".repeat(50));
    }

    private void onWindowClosing(WindowEvent we) {
        try {
            if (appletMod != null) {
                appletMod.shutdown();
                gamePanel.remove(appletMod);
                appletMod = null;
            }
        } finally {
            dispose();
        }
    }

    private void actionStart(ActionEvent ae) {
        try {
            if (appletMod != null) {
                appletMod.shutdown();
                gamePanel.remove(appletMod);
                appletMod = null;
            }
            AppletMod appletMod = MccMod.constructAppletMod();
            putDefaultParameters(appletMod);
            this.appletMod = appletMod;
            add(appletMod);
            System.out.println(appletMod.getSize());
            appletMod.startup();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void actionExit(ActionEvent ae) {
        dispose();
    }

    private void actionSite(ActionEvent ae) {
        try {
            var desktop = Desktop.getDesktop();
            desktop.browse(new URI(SUPPORT_URL));
        } catch (Exception e) {
            var text = new JTextField(SUPPORT_URL);
            text.setEditable(false);
            JOptionPane.showMessageDialog(this, text, "ブラウザを開けません", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void actionAbout(ActionEvent ae) {
        var text = new JTextArea("まさおコンストラクション Desktop\n\n"
                + "詳細は、サポートサイトをご覧ください。");
        text.setEditable(false);
        JOptionPane.showMessageDialog(this, text, "このソフトウェアについて", JOptionPane.INFORMATION_MESSAGE);
    }

    public static void appMain(String... args) {
        MainFrame frame = new MainFrame();
        frame.setVisible(true);
    }

}
