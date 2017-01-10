package com.mljr.spider.parser;

import com.csvreader.CsvWriter;
import org.apache.commons.io.FileUtils;
import us.codecraft.webmagic.selector.Html;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;

/**
 * Created by songchi on 17/1/10.
 */
public abstract class AbstractXpathParser {
    public abstract String[] getHearders(Html html);

    public abstract List<String[]> getContent(Html html);

    public abstract void parseToCsv(Html html);

    public  Html readHtmlFile(File file) {
        try {
            StringBuilder sb = new StringBuilder();
            List<String> lines = FileUtils.readLines(file, "GBK");
            if (lines != null && lines.size() > 3) {
                for (int i = 0; i < lines.size(); i++) {
                    if (i == 0) {
                        continue;
                    }
                    if (i == 1 && lines.get(1).startsWith(":")) {
                        sb.append(lines.get(1).replaceFirst(":", ""));
                        continue;
                    }
                    sb.append(lines.get(i));
                }
                return new Html(sb.toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public  void write(final String filePath, String[] headers, List<String[]> content) {

        File file = new File(filePath);
        File parent = file.getParentFile();
        if (!parent.exists()) {
            parent.mkdirs();
        }

        try {
            CsvWriter csvWriter = new CsvWriter(filePath, ',', Charset.forName("GBK"));
            csvWriter.writeRecord(headers, true);
            for (String[] row : content) {
                csvWriter.writeRecord(row);
            }

            csvWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public  File[] getHtmlFiles(String htmlFilePath) {
        File htmlDir = new File(htmlFilePath);
        return htmlDir.listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File file, String name) {
                return name.endsWith(".html");
            }
        });
    }
}