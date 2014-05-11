#!/bin/bash
javac PiBBP.java
if [ $? -ne 0 ]; then
   echo "Error in compilation";
   exit;
fi;
echo "Testing 0th place"
java PiBBP 0 > $1
echo "Testing 10th place"
java PiBBP 10 >> $1
echo "Testing 100ths place"
java PiBBP 100 >> $1
echo "Testing 1000ths place"
java PiBBP 1000 >> $1
echo "Testing 10000ths place"
java PiBBP 10000 >> $1
echo "Testing 100000ths place"
java PiBBP 100000 >> $1
echo "Testing 1000000ths place"
java PiBBP 1000000 >> $1
