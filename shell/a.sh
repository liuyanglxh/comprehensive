#!/bin/bash
export HADOOP_USER_NAME=hive

docType='deal'
if [ $# -gt 0 ]
then
	docType=$1
fi


hdfsFile=/tmp/dm/dmusertags.meg2.txt
if [  "$docType" == 'sp' ]
then
	hdfsFile=/tmp/dm/sp.dmusertags.meg2.txt
fi

localSaveDir=/tmp
localFileName=`basename $hdfsFile`
localSaveFile=$localSaveDir/$localFileName
if [ -e $localSaveFile ]
then
	rm -f $localSaveFile
fi

echo $hdfsFile,$localFileName,$localSaveFile
hadoop fs -get $hdfsFile $localSaveDir