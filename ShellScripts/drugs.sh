#!/bin/bash

if [ $# -ne 1 ]
then
	echo "One argument needed"
	exit 1
fi

if [ ! -r $1 ]
then
	echo "The file is not readable"
	exit 1
fi

file=$1
cat "$file" | awk -F, 'NR>1 && $3 > 2010 {print $2, $NF}' | awk 'BEGIN {max=0; maxName=""} {if ($NF>max){max=$NF; maxName=$1} } END {print maxName ": " max}'


