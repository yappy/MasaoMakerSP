package io.github.yappy.mcutil;

import org.junit.Test;

import io.github.yappy.mcutil.HtmlParser;
import io.github.yappy.mcutil.McParam;

//import static org.junit.Assert.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedHashMap;
import java.util.List;

public class McUtilTest {

    private static final boolean WRITE_FILE = false;

    @Test
    public void createDefaultData2() throws IOException {
        var data = new LinkedHashMap<String, McParam>();

        for (int i = 1; i <= 1; i++) {
            mergeDefaultData(data, "SJIS", "../original/mc2/game%d.html".formatted(i));
        }

        if (WRITE_FILE) {
            var writer = new PrintWriter(Files.newBufferedWriter(Paths.get("mc2param.txt")));
            for (var param : data.values()) {
                writer.printf("%s\n%s\n%s\n", param.name(), param.value(), param.comment());
            }
            writer.close();
        }
    }

    @Test
    public void createDefaultData3() throws IOException {
        var data = new LinkedHashMap<String, McParam>();

        //mergeDefaultData(data, "UTF-8", "../original/mc3/game1.html");
        mergeDefaultData(data, "UTF-8", "../original/mc3/game_mt.html");
        //for (int i = 2; i <= 15; i++) {
        //    mergeDefaultData(data, "UTF-8",
        //    "../original/mc3/omake/game%d.html".formatted(i));
        //}

        if (WRITE_FILE) {
            var writer = new PrintWriter(Files.newBufferedWriter(Paths.get("mc3param.txt")));
            for (var param : data.values()) {
                writer.printf("%s\n%s\n%s\n", param.name(), param.value(), param.comment());
            }
            writer.close();
        }
    }

    private void mergeDefaultData(LinkedHashMap<String, McParam> data, String charset, String path) throws IOException {
        List<McParam> list = parseOne(charset, path);
        for (var param : list) {
            if (data.get(param.name()) == null) {
                data.putIfAbsent(param.name(), param);
            }
        }
    }

    @Test
    public void parse() throws IOException {
        for (int i = 1; i <= 15; i++) {
            parseOne("SJIS", "../original/mc2/game%d.html".formatted(i));
        }
        for (int i = 2; i <= 15; i++) {
            parseOne("UTF-8", "../original/mc3/omake/game%d.html".formatted(i));
        }
    }

    private List<McParam> parseOne(String charset, String path) throws IOException {
        String src = Files.readString(Paths.get(path), Charset.forName(charset));
        List<McParam> result = HtmlParser.parse(src);

        return result;
    }

}
