package io.github.yappy;

import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.swing.ButtonGroup;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import com.google.common.io.Resources;

import io.github.yappy.mcport.AppletMod;
import io.github.yappy.mcport.AudioClipMod;
import io.github.yappy.mcport.McMod;
import io.github.yappy.mcport.McMod.McVersion;
import io.github.yappy.mcutil.McParam;

public class MainFrame extends JFrame {

    private static final String TITLE = "まさおコンストラクション Desktop";
    private static final String URL_SUPPORT = "https://github.com/yappy/MasaoMakerSP";
    private static final String URL_ISSUES = "https://github.com/yappy/MasaoMakerSP/issues";
    private static final String URL_LATEST = "https://github.com/yappy/MasaoMakerSP/releases/latest";

    private Properties versionInfo;

    private List<GameFrame> gameFrames = new ArrayList<>();
    private JRadioButtonMenuItem menuItem_2_8;
    private JRadioButtonMenuItem menuItem_3_0;
    private JCheckBoxMenuItem menuItem_se;
    private JMenu menuSample;

    private JTable table;
    private DefaultTableModel tableModel;

    public MainFrame() {
        super(TITLE);

        loadVersionInfo();

        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                onWindowClosing();
            }
        });
        setLocationByPlatform(true);

        setJMenuBar(createMenuBar());

        String[] colLabels = { "Param Name", "Value", "Help" };
        this.tableModel = new DefaultTableModel(colLabels, 0);
        this.table = new JTable(tableModel);
        table.setFont(new Font(Font.MONOSPACED, Font.PLAIN, table.getFont().getSize()));
        table.getColumnModel().getColumn(0).setMinWidth(150);
        table.getColumnModel().getColumn(0).setMaxWidth(150);
        table.getColumnModel().getColumn(1).setMinWidth(500);
        table.getColumnModel().getColumn(1).setMaxWidth(500);
        add(new JScrollPane(table));

        onChangeSelectedVersion();

        getContentPane().setPreferredSize(new Dimension(1280, 720));
        pack();
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

        item = new JMenuItem("遊び方 (H)", KeyEvent.VK_H);
        item.addActionListener(this::actionHelp);
        menu.add(item);

        menu.addSeparator();

        group = new ButtonGroup();
        menuItem_2_8 = new JRadioButtonMenuItem("まさおコンストラクション 2.8");
        menuItem_3_0 = new JRadioButtonMenuItem("まさおコンストラクション 3.0", true);
        menuItem_2_8.addActionListener(e -> onChangeSelectedVersion());
        menuItem_3_0.addActionListener(e -> onChangeSelectedVersion());
        group.add(menuItem_2_8);
        group.add(menuItem_3_0);
        menu.add(menuItem_2_8);
        menu.add(menuItem_3_0);

        menuItem_se = new JCheckBoxMenuItem("効果音を強制 ON (3.0 でのみ有効) (S)", true);
        menu.add(menuItem_se);

        menu.addSeparator();

        item = new JMenuItem("終了 (X)", KeyEvent.VK_X);
        item.addActionListener(this::actionExit);
        menu.add(item);

        menuSample = new JMenu("サンプル (S)");
        menuSample.setMnemonic(KeyEvent.VK_S);
        menuBar.add(menuSample);

        menu = new JMenu("ヘルプ (H)");
        menu.setMnemonic(KeyEvent.VK_H);
        menuBar.add(menu);

        item = new JMenuItem("サポートサイト (S)", KeyEvent.VK_S);
        item.addActionListener(e -> actionOpenURL(URL_SUPPORT));
        menu.add(item);
        item = new JMenuItem("問題のレポート (I)", KeyEvent.VK_I);
        item.addActionListener(e -> actionOpenURL(URL_ISSUES));
        menu.add(item);
        item = new JMenuItem("最新バージョンを確認 (L)", KeyEvent.VK_L);
        item.addActionListener(e -> actionOpenURL(URL_LATEST));
        menu.add(item);

        menu.addSeparator();

        item = new JMenuItem("バージョン情報 (A)", KeyEvent.VK_A);
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

    private void onChangeSelectedVersion() {
        loadDefaultParams();
        updateSampleMenu();
    }

    private void updateSampleMenu() {
        McVersion ver = getSelectedVersion();

        menuSample.removeAll();
        List<String> names = McMod.getParamNames(ver);
        for (var name : names) {
            var item = new JMenuItem(name);
            item.setActionCommand(name);
            item.addActionListener(ae -> {
                loadSampleParams(ae.getActionCommand());
            });
            menuSample.add(item);
        }
    }

    private void loadSampleParams(String name) {
        McVersion ver = getSelectedVersion();
        List<McParam> params = McMod.getParam(ver, name);

        tableModel.setRowCount(0);
        for (var param : params) {
            tableModel.addRow(new Object[] { param.name(), param.value(), param.comment() });
        }
        table.getColumnModel().getColumn(0).setPreferredWidth(100);
        table.getColumnModel().getColumn(1).setPreferredWidth(200);
    }

    private void loadDefaultParams() {
        McVersion ver = getSelectedVersion();
        String name = McMod.getDefParamName(ver);

        loadSampleParams(name);
    }

    private void putParamAndRes(AppletMod applet) throws IOException {
        for (int y = 0; y < tableModel.getRowCount(); y++) {
            String name = tableModel.getValueAt(y, 0).toString();
            String value = tableModel.getValueAt(y, 1).toString();
            applet.setParameter(name, value);
        }

        Map<String, Image> images = McMod.getDefImages(getSelectedVersion());
        applet.setImage(images);

        Map<String, AudioClipMod> sounds = McMod.getDefSounds(getSelectedVersion());
        applet.setSound(sounds);
    }

    private void onWindowClosing() {
        for (var gameFrame : gameFrames) {
            if (gameFrame.isVisible()) {
                gameFrame.onWindowClosing();
            }
        }
        System.exit(0);
    }

    private void actionStart(ActionEvent ae) {
        try {
            AppletMod appletMod = McMod.constructAppletMod(getSelectedVersion());
            putParamAndRes(appletMod);
            if (menuItem_se.isSelected()) {
                appletMod.setParameter("se_switch", "1");
            }

            var gameFrame = new GameFrame(appletMod);
            gameFrame.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    gameFrames.remove(e.getWindow());
                }
            });
            this.gameFrames.add(gameFrame);
            gameFrame.setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void actionHelp(ActionEvent ae) {
        String help = McMod.getHelp(getSelectedVersion());
        var text = new JTextArea(help);
        text.setLineWrap(true);
        text.setEditable(false);
        var scroll = new JScrollPane(text, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scroll.setPreferredSize(new Dimension(640, 380));
        JOptionPane.showMessageDialog(this, scroll, "遊び方", JOptionPane.INFORMATION_MESSAGE);
    }

    private void actionExit(ActionEvent ae) {
        onWindowClosing();
    }

    private void actionOpenURL(String url) {
        try {
            var desktop = Desktop.getDesktop();
            desktop.browse(new URI(url));
        } catch (Exception e) {
            var text = new JTextField(url);
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
        var area = new JTextArea(TITLE + "\n\n" + text);
        area.setEditable(false);
        JOptionPane.showMessageDialog(this, area, TITLE, JOptionPane.INFORMATION_MESSAGE);
    }

    public static void appMain(String... args) {
        MainFrame frame = new MainFrame();
        frame.setVisible(true);
    }

}
