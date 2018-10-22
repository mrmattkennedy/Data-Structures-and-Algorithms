#!/bin/sh
print_help() {
	echo "Formatting: 6 flags.
	Flag 1: Language. c, python, java
	Flag 2: sort. insertion, bubble, merge, heap, quick
	Flag 3: Starting range. Any int.
	Flag 4: Ending range. Any int.
	Flag 5: Step range. Any int.
	Flag 6: Operation (Whether to add step range or multiply step range). + or x
	Ex: ./run.sh c insertion 100 1000 100 +"
}

for flag in "$@"
do
	if [ "$flag" = "h" ]; then
		print_help
		exit 1
	fi
done

#if [ $# -ne 6 ]; then
#	print_help
#	exit 0
#fi

fileToComp=""
sortToDo=$2
currRange=$3
stopRange=$4
stepRange=$5
operation=$6
output="c $sortToDo"
range="x"

clang sorts/c_sorts.c -o c_sorts
javac graph/TabbedPane.java

if [ "$1" = "c" ]; then
	fileToComp="c_sorts"
fi

while [ $currRange -le $stopRange ] 
do
	output="$output$(./$fileToComp $sortToDo $currRange)"
	range="$range $currRange"
	if [ "$operation" = "+" ]; then
		((currRange+=$stepRange))
	fi

	if [ "$operation" = "x" ]; then
		((currRange*=$stepRange))
	fi
	echo $currRange
done
output="$output $range end"
echo "$output"

java graph/TabbedPane $output
