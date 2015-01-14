package cn.edu.sjtu.omnilab.sysloganonymizer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

import org.apache.commons.cli.BasicParser;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

/**
 * @Author: Yusu Zhao
 */
public class StartProcessing {

    String inFile;
    String outFile;

    /**
     * @param args
     * @throws ParseException
     * @throws IOException
     * @throws NoSuchAlgorithmException
     */
    public static void main(String[] args) throws ParseException, IOException, NoSuchAlgorithmException {
        
        File inFile, outFile;
        FileWriter fileWriter;
        StartProcessing sp = new StartProcessing();

        sp.ReadCmd(args);

        inFile = new File(sp.inFile);
        outFile = new File(sp.outFile);
        BufferedReader in = new BufferedReader(new FileReader(inFile));
        
        if (!outFile.exists())
            outFile.createNewFile();
        
        fileWriter = new FileWriter(outFile);
        String input;
        ArrayList<String> apName = new ArrayList<String>();

        while ((input = in.readLine()) != null) {
            String output = SyslogPreprocess.Filter(input);
            output = SyslogPreprocess.APIPDeletion(output);

            output = SyslogPreprocess.APNameAnonymous(output, apName);
            output = SyslogPreprocess.IPAnonymous(output);
            output = SyslogPreprocess.MacAnonymous(output);
            output = SyslogPreprocess.UserNameAnonymous(output);
            
            if (output != null)
                fileWriter.write(output + "\n");
        }
        
        fileWriter.close();
    }

    private void ReadCmd(String[] args) throws ParseException {

        Options options = new Options();

        // Add Possible Options
        options.addOption("I", "Input File", true, "The absolue file path of the input file.");
        options.addOption("O", "Output File", true, "The absolue file path of the output file.");

        CommandLineParser parser = new BasicParser();
        CommandLine line = parser.parse(options, args);

        if (line.hasOption("I")) {
            inFile = line.getOptionValue("I");
        }
        if (line.hasOption("O")) {
            outFile = line.getOptionValue("O");
        }
    }

}
