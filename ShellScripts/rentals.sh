#!/bin/bash

filtered=$(cat rental.txt | awk '$3 ~ "Luxury" && $1 ~ /^2024-0[3-7]/ {print $4, $5, $6}')
names=$(echo "$filtered" | awk '{print $1 "_" $2}' | sort | uniq)

for name in $names
do
    new_name=$(echo "$name" | sed 's/_/ /')
    total=$(echo "$filtered" | awk -v name="$new_name" '$1 " " $2 == name {sum+=$3} END {print sum}')
    echo "$new_name $total"
done
