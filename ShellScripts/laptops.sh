# pipe

cat laptops.csv | awk -F, 'BEGIN {nvidia=0; amd=0;} $5 ~ /Nvidia/ {nvidia+=$NF} $5 ~ /AMD/ {amd+=$NF} END {print "Nvidia: " nvidia; print "AMD: " amd}'