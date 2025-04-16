#!/bin/bash

idx=$1

cat logins.txt | awk -v idx="$idx" '$1 ~ idx {print $NF}' | sed -e 's/(//' -e 's/)//' | awk -F: 'BEGIN {total=0} {total+=$1*60 + $2} END {print total;}'

