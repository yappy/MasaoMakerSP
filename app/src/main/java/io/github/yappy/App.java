package io.github.yappy;

import java.util.Arrays;

public class App {
    public static void main(String[] args) throws Exception {
        if (args.length >= 1 && args[0].equals("--tools")) {
            Tools.entry(Arrays.copyOfRange(args, 1, args.length));
            return;
        }

        MainFrame.appMain(args);
    }
}
