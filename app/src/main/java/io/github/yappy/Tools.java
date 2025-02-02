package io.github.yappy;

import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import io.github.yappy.mcutil.HtmlParser;
import io.github.yappy.mcutil.McParam;

public class Tools {

    // ./gradlew run --args="--tools param SJIS ../original/mc2 ../mcport/src/main/resources/mc2/param"
    // ./gradlew run --args="--tools param UTF-8 ../original/mc3 ../mcport/src/main/resources/mc3/param"
    private static void param(String[] args) throws Exception {
        var charset = args[0];
        var indir = Paths.get(args[1]);
        var outdir = Paths.get(args[2]);

        var inpaths = new ArrayList<Path>();
        try (Stream<Path> stream = Files.walk(indir, 2)) {
            stream.forEach(path -> {
                var filename = path.getFileName().toString();
                if (filename.endsWith(".html")) {
                    System.out.println(path);
                    inpaths.add(path);
                }
            });
        }
        inpaths.sort((a, b) -> a.getFileName().compareTo(b.getFileName()));

        Files.createDirectories(outdir);
        List<McParam> all = new ArrayList<>();
        var filelist = new StringBuilder();
        for (var inpath : inpaths) {
            var filename = inpath.getFileName().toString();
            var woext = filename.substring(0, filename.lastIndexOf(".html"));
            filename = woext + ".txt";
            filelist.append(woext).append('\n');
            var outpath = outdir.resolve(filename);
            System.out.printf("%s -> %s%n", inpath, outpath);

            String src = Files.readString(inpath, Charset.forName(charset));
            List<McParam> result = HtmlParser.parse(src);
            Files.writeString(outpath, HtmlParser.toString(result));

            HtmlParser.merge(all, result);
        }
        Files.writeString(outdir.resolve("all.txt"), HtmlParser.toString(all));
        Files.writeString(outdir.resolve("list.txt"), filelist.toString());
    }

    public static void entry(String[] args) throws Exception {
        String[] rest = Arrays.copyOfRange(args, 1, args.length);
        if (args[0].equals("param")) {
            param(rest);
        }
    }

}
