# game-sys-test-task
GameSys test task

## Rss-reader API



To enable/disable scheduler for sending predefined persons from test-data file.

`GET http://localhost:8815/api/rss-reader/enable-scheduler?enabled=false`

Where enabled can be true or false



`GET http://localhost:8815/api/api/rss-reader/process-rrs`

`GET http://localhost:8815/api/api/rss-reader/read-last-items`


## H2 console

http://localhost:8815/h2-console/

`jdbc:h2:tcp://localhost:9090/mem:testdb`