#!/bin/bash

file=$1

cat "$file" | awk -F, '$3 ~ "Sales" {print}' | awk -F, 'BEGIN {counter=0; sum=0} {counter+=1; sum+=$NF} END{print "Sales: " sum/counter}'

cat "$file" | awk -F, '$3 ~ "Finance" {print}' | awk -F, 'BEGIN {counter=0; sum=0} {counter+=1; sum+=$NF} END{print "Finance: " sum/counter}'

