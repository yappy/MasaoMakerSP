package io.github.yappy;

import java.awt.BorderLayout;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.swing.ButtonGroup;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import com.google.common.io.Resources;

import io.github.yappy.mccport.AppletMod;
import io.github.yappy.mccport.AudioClipMod;
import io.github.yappy.mccport.McMod;
import io.github.yappy.mccport.McMod.McVersion;
import io.github.yappy.mcutil.McParam;

public class MainFrame extends JFrame {

    private static final String SUPPORT_URL = "https://github.com/yappy/MasaoMakerSP";

    private Properties versionInfo;
    private AppletMod appletMod = null;

    private JPanel gamePanel;
    private JRadioButtonMenuItem menuItem_2_8;
    private JRadioButtonMenuItem menuItem_3_0;

    public MainFrame() {
        super("Masao Construction Desktop");

        loadVersionInfo();

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
        gamePanel.setPreferredSize(new Dimension(McMod.MC_APPLET_W, McMod.MC_APPLET_H));
        add(gamePanel);
        pack();
        setResizable(false);
    }

    private void loadVersionInfo() {
        versionInfo = new Properties();
        try {
            versionInfo.load(Resources.getResource("version.properties").openStream());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private JMenuBar createMenuBar() {
        var menuBar = new JMenuBar();
        JMenu menu;
        JMenuItem item;
        ButtonGroup group;

        menu = new JMenu("ゲーム (G)");
        menu.setMnemonic(KeyEvent.VK_G);
        menuBar.add(menu);

        item = new JMenuItem("開始 (S)", KeyEvent.VK_S);
        item.addActionListener(this::actionStart);
        menu.add(item);

        menu.addSeparator();

        group = new ButtonGroup();
        menuItem_2_8 = new JRadioButtonMenuItem("まさおコンストラクション 2.8", true);
        menuItem_3_0 = new JRadioButtonMenuItem("まさおコンストラクション 3.0");
        group.add(menuItem_2_8);
        group.add(menuItem_3_0);
        menu.add(menuItem_2_8);
        menu.add(menuItem_3_0);

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

    private McVersion getSelectedVersion() {
        if (menuItem_2_8.isSelected()) {
            return McVersion.MC_2_8;
        } else {
            return McVersion.MC_3_0;
        }
    }

    private void putDefaultParamAndRes(AppletMod applet) throws IOException {
        List<McParam> params = McMod.getDefParams(getSelectedVersion());
        for (var param : params) {
            applet.setParameter(param.name(), param.value());
        }

        Map<String, Image> images = McMod.getDefImages(getSelectedVersion());
        applet.setImage(images);

        Map<String, AudioClipMod> sounds = McMod.getDefSounds(getSelectedVersion());
        applet.setSound(sounds);
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
            AppletMod appletMod = McMod.constructAppletMod(getSelectedVersion());
            putDefaultParamAndRes(appletMod);
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
        String desc = versionInfo.getProperty("versionDescribe", "unknown");
        String branch = versionInfo.getProperty("versionBranch", "unknown");
        String hash = versionInfo.getProperty("versionHashfull", "unknown");
        String date = versionInfo.getProperty("versionDate", "unknown");
        var text = "%s\nBranch: %s\n%s\n%s".formatted(desc, branch, hash, date);
        var area = new JTextArea("まさおコンストラクション Desktop\n\n" + text);
        area.setEditable(false);
        JOptionPane.showMessageDialog(this, area, "このソフトウェアについて", JOptionPane.INFORMATION_MESSAGE);
    }

    public static void appMain(String... args) {
        MainFrame frame = new MainFrame();
        frame.setVisible(true);
    }

}
