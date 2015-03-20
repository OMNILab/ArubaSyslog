#!/bin/bash

set -e

# Parameters
OUTFOLDERr=/home/hadoop/wifi.syslog.original
RORT=514
FILTER="all"
FILESIZE=1000000000
OPTS="-z -P $PORT -d $OUTFOLDER -s $FILESIZE -F $FILTER"

# kill older running processes
pid=$(ps -ef | grep $(basename $OUTFOLDER) | awk '$8 == "/usr/bin/java" {print$2}')
kill $pid

mkdir -p $OUTFOLDER

# Get runnable package file
BIN=`dirname "$0"`
BIN=`cd "$bin">/dev/null; pwd`
LHOME="${bin}/.."

RUNJAR=`find $LHOME/target -name *-with-dependencies.jar`

if [[ -z "$RUNJAR" ]] || [[ "$RUNJAR" == " " ]]; then
    echo "Cannot find runable jar binary. Please run 'mvn package' first."
    exit -1;
fi

# Run main logic
java -cp $RUNJAR cn.edu.sjtu.omnilab.syslogdumper.StartLogging $OPTS