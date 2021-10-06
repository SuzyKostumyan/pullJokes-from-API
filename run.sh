#!/usr/bin/env bash

mvn clean package -U -Dmaven.test.skip=true
#mvn -f dbsetup clean package -U -Dmaven.test.skip=true
sudo docker-compose up -d --build


