#!/bin/bash

pid=$(ps -ef | grep WiFi.Syslog | awk '$8 == "/usr/bin/java" {print$2}')
kill $pid

/home/hadoop/workspace/ArubaSyslog/SyslogDumper/bin/startlogtofile.sh