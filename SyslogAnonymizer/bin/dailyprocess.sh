#!/bin/bash

set -e

timeNow=`date +%F`
infilename="/home/data/WiFi.Syslog/wifilog$timeNow"
outfilename="/home/nfs/dataset/WiFi.Syslog/wifilog$timeNow"

mkdir -p outfilename

OPTS="-I $infilename -O $outfilename"

# Obtain runnable package file
bin=`dirname "$0"`
bin=`cd "$bin">/dev/null; pwd`
LHOME="${bin}/.."

RUNJAR=`find $LHOME/target -name *-with-dependencies.jar`
if [[ -z "$RUNJAR" ]] || [[ "$RUNJAR" == " " ]]; then
    echo "Cannot find runable jar binary. Please run 'mvn package' first."
    exit -1;
fi

java -cp $RUNJAR cn.edu.sjtu.omnilab.sysloganonymizer.StartProcessing $OPTS