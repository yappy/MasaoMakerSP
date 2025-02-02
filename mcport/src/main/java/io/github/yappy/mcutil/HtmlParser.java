package io.github.yappy.mcutil;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Scanner;

import javax.swing.text.MutableAttributeSet;
import javax.swing.text.html.HTML;
import javax.swing.text.html.HTML.Tag;
import javax.swing.text.html.HTMLEditorKit.ParserCallback;
import javax.swing.text.html.parser.ParserDelegator;

public class HtmlParser {

    public static List<McParam> parse(String html) {
        var delegator = new ParserDelegator();
        var parser = new Parser();
        try {
            delegator.parse(new StringReader(html), parser, true);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return parser.getResult();
    }

    public static void merge(List<McParam> list, List<McParam> additional) {
        var map = new LinkedHashMap<String, McParam>();
        for (var param : list) {
            map.put(param.name(), param);
        }
        for (var param : additional) {
            map.putIfAbsent(param.name(), param);
        }
        list.clear();
        list.addAll(map.values());
    }

    public static String toString(List<McParam> list) {
        var result = new StringBuilder();
        for (var param : list) {
            result.append(param.name()).append('\n');
            result.append(param.value()).append('\n');
            result.append(param.comment()).append('\n');
        }

        return result.toString();
    }

    private static class Parser extends ParserCallback {
        private List<McParam> params = new ArrayList<>();
        private StringBuilder commentBuffer = new StringBuilder();

        public Parser() {
            super();
        }

        public List<McParam> getResult() {
            return params;
        }

        @Override
        public void handleComment(char[] data, int pos) {
            var comment = new String(data);
            commentBuffer.append(comment);
        }

        @Override
        public void handleSimpleTag(Tag t, MutableAttributeSet a, int pos) {
            if (t == Tag.PARAM) {
                var comment = formatComment(commentBuffer.toString());
                var name = a.getAttribute(HTML.Attribute.NAME);
                var value = a.getAttribute(HTML.Attribute.VALUE);
                if (name != null && value != null) {
                    var nameStr = name.toString();
                    var valueStr = value.toString();
                    if (nameStr.startsWith("map")) {
                        comment = "";
                    }
                    params.add(new McParam(nameStr, valueStr, comment));
                }
            }
            commentBuffer.setLength(0);
        }

        @Override
        public void handleStartTag(Tag t, MutableAttributeSet a, int pos) {
            handleSimpleTag(t, a, pos);
        }

        private static String formatComment(String src) {
            var result = new StringBuilder();

            var in = new Scanner(src);
            while (in.hasNext()) {
                if (!result.isEmpty()) {
                    result.append(" ");
                }
                result.append(in.next());
            }
            in.close();

            return result.toString();
        }
    }

}
