This program helps to pre-process the received wifi syslog messages to file.

# How to use it?

1. Compile the program with maven.

	$ mvn clean && mvn package

2. Edit the input and output path in `dailyprocess.sh`

3. Start the program with the script named ```dailyprocess.sh```.

	$ bin/dailyprocess.sh