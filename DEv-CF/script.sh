#!/bin/bash
mkdir bin
mkdir output
javac -cp "./lib/fwkdevs-v0.7" -d ./bin ./src/*.java
java -cp ./bin:./lib/fwkdevs-v0.7 Simulator
cd output
cat report
