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



    public static void main(String[] args) throws Exception {
        MainFrame.appMain(args);
    }
}
