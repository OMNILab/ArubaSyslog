This program receives and dumps Aruba system messages to file.
The message stream is specified by a concrete host number determined by the message sender.

# How to use it?

1. Replace parameters (e.g., port, dirname) in `bin/startlogtofile.sh` with your local values.

2. Compile and package the program with maven.

    $ cd SyslogDumper  
    $ mvn clean && mvn package

3. Start the program with the script `startlogtofile.sh` who writes logs into text files.

    $ bin/startlogfile.sh