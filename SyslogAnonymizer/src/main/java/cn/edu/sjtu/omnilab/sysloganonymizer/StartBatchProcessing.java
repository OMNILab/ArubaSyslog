package cn.edu.sjtu.omnilab.sysloganonymizer;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.cli.BasicParser;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

public class StartBatchProcessing {
	
	String inPath;
	String outPath;

	public void ReadCmd(String[] args) throws ParseException {
		
		Options options = new Options();
		
		// Add Possible Options
		options.addOption("I", "Input Path", true, "The absolue path of the input file or directory.");
		options.addOption("O", "Output Path", true, "The absolue path of the output file or directory.");
		
		CommandLineParser parser = new BasicParser();
		CommandLine line = parser.parse(options, args);

		if (line.hasOption("I")) {
			inPath = line.getOptionValue("I");
		}
		if (line.hasOption("O")) {
			outPath = line.getOptionValue("O");
		}
	}

	/**
	 * @param args
	 * @throws ParseException 
	 * @throws IOException 
	 * @throws NoSuchAlgorithmException 
	 */
	public static void main(String[] args) throws ParseException, IOException, NoSuchAlgorithmException {
		// TODO Auto-generated method stub
//		File inPath, outPath;
//		FileWriter fileWriter;
//		StartBatchProcessing sp = new StartBatchProcessing();
//
//		sp.ReadCmd(args);
//
//		inPath = new File(sp.inPath);
//		outPath = new File(sp.outPath);
//
//		inPath.
//
//		BufferedReader in = new BufferedReader(new FileReader(inPath));
//		if(!outFile.exists()) outFile.createNewFile();
//		fileWriter = new FileWriter(outFile);
//		String input;
//
//		while ((input = in.readLine()) != null) {
//			SyslogPreprocess spp = new SyslogPreprocess();
//
//			String output = SyslogPreprocess.Filter(input);
//			output = SyslogPreprocess.APIPDeletion(output);
//
//			output = SyslogPreprocess.APNameAnonymous(output);
//			output = SyslogPreprocess.IPAnonymous(output);
//			output = SyslogPreprocess.MacAnonymous(output);
//			output = SyslogPreprocess.UserNameAnonymous(output);
//			if (output != null) fileWriter.write(output + "\n");
//		}
//		fileWriter.close();
	}

}
