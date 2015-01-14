/**
 *
 */
package cn.edu.sjtu.omnilab.syslogdumper;

import org.apache.log4j.Logger;

import java.io.IOException;

/**
 * @author jianwen
 */
public abstract class LoggerAbstract {

    protected static final Logger LOG = Logger.getLogger(LoggerAbstract.class.getName());

    public abstract void log(String msg) throws IOException;

    // The default constructor does NOTHING
    public LoggerAbstract() {}

    public LoggerAbstract(ConfLogger conf) {}

}
