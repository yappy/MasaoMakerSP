package io.github.yappy;

import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import io.github.yappy.mcport.AppletMod;
import io.github.yappy.mcport.McMod;
import io.github.yappy.mcport.ThreadHangException;

public class GameFrame extends JFrame {

    private boolean exit = false;
    private AppletMod appletMod;

    public GameFrame(AppletMod appletMod) {
        super("まさおコンストラクション");
        this.appletMod = appletMod;

        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent we) {
                onWindowClosing();
            }
        });
        setLocationByPlatform(true);

        getContentPane().setPreferredSize(new Dimension(McMod.MC_APPLET_W, McMod.MC_APPLET_H));
        add(appletMod);
        pack();
        setResizable(false);

        appletMod.startup();
    }

    public void onWindowClosing() {
        try {
            if (!exit) {
                exit = true;
                appletMod.shutdown();
            }
        } catch (ThreadHangException e) {
            JOptionPane.showMessageDialog(this, "ゲーム終了に失敗しました。", "エラー", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        } finally {
            dispose();
        }
    }

}
