#!/bin/bash

if [ $# -ne 1 ]
then
        echo "One argument needed"
        exit 1
fi


file=$1
if [ ! -f "$file" ]
then
        echo "The file does not exist"
        exit 1
fi

if [ ! -r "$file" ]
then
        echo "The file is not readable"
        exit 1
fi

if [[ "$file" != *.csv ]]
then
	echo "csv file needed"
	exit 1
fi


data=$(cat $file | sort | head -n-1)

echo "Exam Scores Analysis"
echo "-------------------"
students=$(echo "$data" | wc -l)
echo "Total Number of Students: $students"
echo " "
echo " "
echo "Subject Averages:"
echo "$data" | awk -F, -v students="$students" 'BEGIN {math=0; science=0; english=0; history=0} {math+=$3; science+=$4; english+=$5; history+=$6;} END{printf "Math:		%.2f\n", math/students; printf "Science:	%.2f\n", science/students; printf "English:	%.2f\n", english/students; printf "History:	%.2f\n", history/students;}'

echo " "
echo " "
echo "Subject Extreme Performers:"
subjects=("Math" "Science" "English" "History")

echo "$data" | awk -F, -v students="$students" 'BEGIN {maxMath=0; minMath=100; nameMaxMath=""; nameMinMath=""; maxScience=0; minScience=100; nameMaxScience=""; nameMinScience=""; maxEnglish=0; minEnglish=100; nameMaxEnglish=""; nameMinEnglish=""; maxHistory=0; minHistory=100; nameMaxHistory=""; nameMinHistory=""} {if ($3 > maxMath) {maxMath=$3; nameMaxMath=$2;} if ($3 < minMath) {minMath=$3; nameMinMath=$2;} if ($4 > maxScience) {maxScience=$4; nameMaxScience=$2;} if ($4 < minScience) {minScience=$4; nameMinScience=$2;} if ($5 > maxEnglish) {maxEnglish=$5; nameMaxEnglish=$2;} if ($5 < minEnglish) {minEnglish=$5; nameMinEnglish=$2;} if ($6 > maxHistory) {maxHistory=$6; nameMaxHistory=$2;} if ($6 < minHistory) {minHistory=$6; nameMinHistory=$2;}} END {print "Math - Highest: " nameMaxMath " (Score: " maxMath "), Lowest: " nameMinMath " (Score: " minMath ")"; print "Science - Highest: " nameMaxScience " (Score: " maxScience "), Lowest: " nameMinScience " (Score: " minScience ")"; print "English - Highest: " nameMaxEnglish " (Score: " maxEnglish "), Lowest: " nameMinEnglish " (Score: " minEnglish ")"; print "History - Highest: " nameMaxHistory " (Score: " maxHistory "), Lowest: " nameMinHistory " (Score: " minHistory ")"}'
