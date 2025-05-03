#!/bin/bash

season=$1
location=$2

result=`cat races.txt | awk -v season=$season -v location=$location '($3 ~ season) && ($4 ~ location) {print $1}' | sort | uniq -c`
echo $result
