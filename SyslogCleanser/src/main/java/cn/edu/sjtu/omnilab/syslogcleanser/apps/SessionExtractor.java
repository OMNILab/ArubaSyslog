package cn.edu.sjtu.omnilab.syslogcleanser.apps;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.util.LinkedList;
import java.util.List;

import cn.edu.sjtu.omnilab.syslogcleanser.wifilogfilter.SessionExtraction;
import cn.edu.sjtu.omnilab.syslogcleanser.wifilogfilter.Utils;
import org.apache.commons.io.FilenameUtils;

/**
 * This class extracts wifi sessions from filtered wifi logs.
 *
 * Input format: Filtered Aruba syslog output by RawLogFilter.java.
 * Output format: SessionInfo (usermac, STime, ETime, Dur, AP)
 *
 * @author chenxm
 */
public class SessionExtractor {

    public static void main(String[] args) throws IOException, ParseException {

        // Initial options
        String input_location = "";
        String output_location = "";

        // fetch command line options 
        int optSetting = 0;
        for (; optSetting < args.length; optSetting++) {
            if ("-i".equals(args[optSetting])) {
                input_location = args[++optSetting];
            } else if ("-o".equals(args[optSetting])) {
                output_location = args[++optSetting];
            }
        }

        if (input_location.length() == 0) {
            System.out.println("Usage: SessionExtractor -i <source> -o <destination>>");
            System.exit(-1);
        }

        // for statistics
        long start = System.currentTimeMillis();

        // reading filtered files one by one to extract user sessions
        System.out.println("Extracting user trace sessions from raw logs ...");
        Utils.createFolder(output_location);
        File[] user_files = Utils.getInputFiles(input_location);

        for (File file : user_files) {
            
            String umac = file.getName();
            System.out.println(System.currentTimeMillis() + " " + umac);
            
            // read the file
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String thisLine = "";
            List<String> allLines = new LinkedList<String>();
            
            // read each line
            while ((thisLine = reader.readLine()) != null) {
                thisLine = thisLine.replaceAll("[\\r\\n]", "");
                if (thisLine.length() == 0) {
                    continue;
                }
                allLines.add(thisLine);
            }
            
            // extract session and save to file
            allLines = SessionExtraction.extractSessions(allLines);
            
            // write into file
            BufferedWriter bw = new BufferedWriter(
                    new FileWriter(FilenameUtils.concat(output_location, umac)));
            for (String l : allLines)
                bw.write(l + "\n");
            
            bw.close();
            reader.close();
        }

        System.out.println(String.format("Total time: %.3f sec",
                (System.currentTimeMillis() - start) / 1000.0));
    }
}
