SyslogCleanser
==============

Author: Wenjuan Gong
05-2013

This is a tool to filter out useful message from Aruba syslog
for research purpose.

Several types of messages are retained including:

 * Auth/Deauth  
 * Assoc/Deassoc  
 * User authentication  
 * IP allocation  
 * IP recycle

The message content is matched and extracted using Regular expression.
It's easy to extend the message filter capability.


BUILDING
--------

This tool runs on Java 1.6+ and makes to be compiled with Maven.
As Java and Maven are installed, you can start with:

        $ cd SyslogCleanser
        $ mvn clean && mvn package


HOW TO USE
----------

There are four APPs shipped with this package:

* RawLogFilter: Filter and format raw Aruba syslogs.
* UserSplitter: Split syslogs into users (one file for one user).
* SessionExtractor: Extract session info. for each user.
* UserSessions: A combination of UserSplitter and SessionExtractor.

Calling commands:

        $ java -cp target/SyslogCleanser-<version>-with-dependencies.jar \
                cn.edu.sjtu.omnilab.syslogcleanser.apps.<APPNAMES> \
                -i [source] \
                -o [destination]


NOTICE
------

To avoid Out of Memory error, -Xmx1G or other option should be add to java command;
You can also use export this option to env. using

        $ export _JAVA_OPTIONS="-Xmx1G"