#!/usr/bin/env bash
cd ../../../..
mvn clean package
hadoop jar target/bigDataDemo-1.0.jar mapReduceDemo.maxTemperature
