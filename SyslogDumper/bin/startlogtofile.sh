#!/bin/bash

set -e

# Parameters
dirname=/home/data/WiFi.Syslog
port=514
filter="all"
filesize=1000000000
OPTS="-z -P $port -d $dirname -s $filesize -F $filter"

mkdir -p $dirname

# Obtain runnable package file
bin=`dirname "$0"`
bin=`cd "$bin">/dev/null; pwd`
LHOME="${bin}/.."

RUNJAR=`find $LHOME/target -name *-with-dependencies.jar`
if [[ -z "$RUNJAR" ]] || [[ "$RUNJAR" == " " ]]; then
    echo "Cannot find runable jar binary. Please run 'mvn package' first."
    exit -1;
fi

# Run main logic
java -cp $RUNJAR cn.edu.sjtu.omnilab.syslogdumper.StartLogging $OPTS