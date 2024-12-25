package io.gihub.yappy.mcutil;

import org.junit.Test;

import io.github.yappy.mcutil.HtmlParser;
import io.github.yappy.mcutil.HtmlParser.McParam;

//import static org.junit.Assert.*;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class LibraryTest {

    @Test
    public void parse() throws IOException {
        for (int i = 1; i <= 15; i++) {
            parseOne("SJIS", "../original/mc2", i);
        }
        for (int i = 2; i <= 15; i++) {
            parseOne("UTF-8", "../original/mc3/omake", i);
        }
    }

    private void parseOne(String charset, String dir, int num) throws IOException {
        String path = "%s/game%d.html".formatted(dir, num);
        String src = Files.readString(Paths.get(path), Charset.forName(charset));
        List<McParam> result = HtmlParser.parse(src);
        for (var p : result) {
            System.out.println(p);
        }
    }

}
