package cn.edu.sjtu.omnilab.syslogdumper;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Author: Yusu Zhao
 */
public class LoggerFile extends LoggerAbstract {

    protected String dirName;
    protected int maxLine;    // File line limit
    private int numLine = 0;    // The current line number;
    private int numFile = 0;    // The current File number;
    private String fileName;
    private File file;
    private FileWriter fileWriter;
    final private String prefix = "wifilog" +
            new SimpleDateFormat("yyyy-MM-dd").format(new Date()); // Filename prefix
    private static final int[] errorCodeList = {124003, 132030, 132053, 132197, 202086, 404400, 500010,
            501044, 501080, 501093, 501095, 501098, 501099, 501100, 501102, 501105, 501106, 501107, 501108,
            501109, 501111, 501114, 522005, 522006, 522008, 522010, 522026, 522029, 522030, 522035, 522036,
            522038, 522042, 522044, 522049, 522050};

    public static String Filter(String str) {

        Pattern pattern;
        Matcher matcher;

        pattern = Pattern.compile("<[0-9]{6}>");
        matcher = pattern.matcher(str);

        if (!matcher.find()) return null;

        String tmp = matcher.group();
        int errorCode = Integer.parseInt(tmp.substring(1, 7));

        for (int num : errorCodeList) {
            if (errorCode == num) return str;
        }
        return null;
    }

    public LoggerFile(ConfLogger conf) throws IOException {
        // Call super constructor to set the filter
        super(conf);
        dirName = conf.getDirName();
        maxLine = conf.getFileLength();
        // The initial file to write
        fileName = dirName + "/" + prefix;
        file = new File(fileName);
        if (!file.exists())
            file.createNewFile();
        fileWriter = new FileWriter(file.getAbsolutePath());
        // TODO: Remove the LOG
        LOG.info("Open file " + file.getAbsolutePath());
    }

    @Override
    public void log(String msg) throws IOException {

        numLine++;

        // Determine which output file to use
        if (numLine <= maxLine) {
            LOG.debug("Use the old file.");
        } else {
            LOG.debug("Open a new file to write.");
            // Close the old file
            fileWriter.close();

            // Open a new one
            numFile++;
            fileName = dirName + "/" + prefix + String.format("%04d", numFile);
            file = new File(fileName);
            if (!file.exists())
                file.createNewFile();
            fileWriter = new FileWriter(file.getAbsolutePath());

            // Reset numLine
            numLine = 0;
        }

        // Write to file
        Date date = new Date();
        Long utc_in_ms = date.getTime();
        fileWriter.write(utc_in_ms + " " + msg + "\n");
    }

}
