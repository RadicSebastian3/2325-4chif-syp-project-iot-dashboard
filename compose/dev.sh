#!/usr/bin/env bash

docker compose -f influxdb.yaml pull

docker compose -f dev.yaml up --build
