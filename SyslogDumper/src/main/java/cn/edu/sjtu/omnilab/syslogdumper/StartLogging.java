package cn.edu.sjtu.omnilab.syslogdumper;

import org.apache.commons.cli.ParseException;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

/**
 * Receive and dump system messages from specific port into on-disk files.
 * *
 * @author jianwen
 */
public class StartLogging {

    public static void main(String[] args) throws IOException {

        // Prepare Logger from log4j
        final Logger LOG = Logger.getLogger(StartLogging.class.getName());

        // Create a ConfLogger instance which holds configuration information
        ConfLogger conf = new ConfLogger();

        // Further Parse the Command-line Parameters
        if (args.length != 0) {
            try {
                conf.readCmd(args);
            } catch (ParseException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        // Print Configuration Info
        LOG.info("Output target: " + conf.getOutTarget()
                + "; ZooKeepr Quorum:" + conf.getZk()
                + "; PORT_NUM:" + conf.getPort()
                + "; BUFF_SIZE:" + conf.getBufsize()
                + "; Filter-included types: " + conf.getFilter()
                + "; Name Length: " + conf.getNameLength()
                + "; Log Head: " + conf.getHead()
                + "; Table:" + conf.getTbName()
                + "; Column Family:" + conf.getColName()
                + "; Output dir name: " + conf.getDirName()
                + "; Output file size limit: " + conf.getFileLength());

        // Initialize the LoggerHBase
        LoggerAbstract logWriter;
        if ((conf.getOutTarget() == null) || (conf.getOutTarget().equals("FILE"))) {
            logWriter = new LoggerFile(conf);
            LOG.info("Start a new LoggerFile instance.");
        } else {
            logWriter = new LoggerFile(conf);
            LOG.info("Start a new LoggerHBase instance.");
        }

        // Prepare UDP Socket
        DatagramSocket rcvSocket = new DatagramSocket(conf.getPort());
        DatagramPacket rcvPacket = new DatagramPacket(new byte[conf.getBufsize()], conf.getBufsize());

        LOG.info("Receiving syslog messages...");
        // TODO: Get port info from rcvSocket and buf_size from rcvPacket and add them to LOG.info output

        // Receive and Log message
        while (true) {
            rcvSocket.receive(rcvPacket);
            String msg = new String(rcvPacket.getData(), 0, rcvPacket.getLength());

            // Log the message
            msg = LoggerFile.Filter(msg);
            if (msg != null)
                logWriter.log(msg);
        }

    }

}