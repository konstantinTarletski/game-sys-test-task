# game-sys-test-task
GameSys test task

## Used technologies and tools

* Java 11
* Gradle
* H2 database
* Gradle
* SpringBoot 2
* Lombok
* Docker
* Docker-compose
* Some other small spring embedded things

## Rss-reader API


To enable/disable scheduler for sending predefined persons from test-data file.

`GET http://localhost:8815/api/rss-reader/enable-scheduler?enabled=false`

Where enabled can be true or false



`GET http://localhost:8815/api/api/rss-reader/process-rrs`

`GET http://localhost:8815/api/api/rss-reader/read-last-items`


## H2 console

http://localhost:8815/h2-console/

`jdbc:h2:tcp://localhost:9090/mem:testdb`

## How to run project

This project can be run in 2 ways:
* With docker-compose
* Like local applications

### Run with docker-compose

To run this application with "docker-compose" you need just run the command :

`docker-compose up -d`

Yon do not need even to have Java installed on your local computer, because compilation of the source code take place inside docker images.

### Run like local applications

### See the results