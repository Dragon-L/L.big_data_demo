#!/usr/bin/env bash
docker-compose -f build-env/spark-docker-compose.yml up --scale spark-worker=2