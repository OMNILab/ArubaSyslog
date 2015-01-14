package cn.edu.sjtu.omnilab.syslogdumper;

import java.io.IOException;
import java.net.URL;
import java.util.Properties;

import org.apache.commons.cli.BasicParser;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

public class ConfLogger {

    public int getPort() {
        return port;
    }


    public int getBufsize() {
        return bufsize;
    }


    public String getFilter() {
        return filterLst;
    }


    public String getHead() {
        return head;
    }


    public String getTbName() {
        return tbName;
    }


    public String getColName() {
        return colName;
    }


    public int getNameLength() {
        return nameLength;
    }

    public int getFileLength() {
        return fileLength;
    }

    public String getDirName() {
        return dirName;
    }

    public String getOutTarget() {
        return outTarget;
    }

    public String getZk() {
        return zk;
    }

    protected int port;
    protected int bufsize;
    protected String filterLst;
    protected String head;
    protected String tbName;
    protected String colName;
    protected String dirName;
    protected int nameLength;
    protected int fileLength;
    protected String outTarget;
    protected String zk;    // ZooKeeper List (Initialized from hbase-site.xml)

    public ConfLogger() throws IOException {

        Properties props = new Properties();
        URL url = ClassLoader.getSystemResource("conf.properties");
        props.load(url.openStream());

        // Get properties and Intialize ConfLogger
        port = Integer.parseInt(props.getProperty("cn.edu.sjtu.omnilab.panabittohbase.udpport"));
        bufsize = Integer.parseInt(props.getProperty("cn.edu.sjtu.omnilab.panabittohbase.buffersize"));
        filterLst = props.getProperty("cn.edu.sjtu.omnilab.panabittohbase.logfilter");
        head = props.getProperty("cn.edu.sjtu.omnilab.panabittohbase.loghead");
        tbName = props.getProperty("cn.edu.sjtu.omnilab.panabittohbase.tbname");
        colName = props.getProperty("cn.edu.sjtu.omnilab.panabittohbase.colfam");
        nameLength = Integer.parseInt(props.getProperty("cn.edu.sjtu.omnilab.panabittohbase.namelength"));
        dirName = props.getProperty("cn.edu.sjtu.omnilab.panabittohbase.dirname");
        fileLength = Integer.parseInt(props.getProperty("cn.edu.sjtu.omnilab.panabittohbase.filelength"));
    }

    public void readCmd(String args[]) throws ParseException {

        Options options = new Options();

        // Add Possible Options
        options.addOption("P", "PORT_NUM", true, "The port number for receiving Panabit Syslog messages.");
        options.addOption("F", "STRFILTER", true, "Multiple filter types seperated by SPACE. For example, ssh rdesktop default unknown80.");
        options.addOption("H", "LOGHEAD", true, "Log head which needs trimming in the log message body.");
        options.addOption("T", "TBNAME", true, "HBase table name.");
        options.addOption("C", "COLFAM", true, "HBase column family name.");
        options.addOption("L", "APPNAMELENGTH", true, "Limitation on the length of the application name.");
        options.addOption("d", "DIR", true, "The output dir.");
        options.addOption("s", "FILESIZE", true, "The plain text file size limit (in the number of lines).");
        options.addOption("h", "host", true, "Remote hosts (ZooKeeper Quorum).");
        // TODO: Change the option -z to some "long ones", such as "--to-file" or "-to-file"
        options.addOption("z", "TOFILE", false, "Whether the output goes to plaint-text files.");

        // The Command-line parser
        CommandLineParser parser = new BasicParser();
        CommandLine line = parser.parse(options, args);

        if (line.hasOption("P")) {
            port = Integer.parseInt(line.getOptionValue("P"));
        }
        if (line.hasOption("F")) {
            filterLst = line.getOptionValue("F");
        }
        if (line.hasOption("H")) {
            head = line.getOptionValue("H");
        }
        if (line.hasOption("T")) {
            tbName = line.getOptionValue("T");
        }
        if (line.hasOption("C")) {
            colName = line.getOptionValue("C");
        }
        if (line.hasOption("L")) {
            nameLength = Integer.parseInt(line.getOptionValue("L"));
        }
        if (line.hasOption("d")) {
            dirName = line.getOptionValue("d");
        }
        if (line.hasOption("s")) {
            fileLength = Integer.parseInt(line.getOptionValue("s"));
        }
        if (line.hasOption("h")) {
            zk = line.getOptionValue("h");
        }
        if (line.hasOption("z")) {
            outTarget = "FILE";
        } else {
            outTarget = "HBASE";
        }

    }


}
