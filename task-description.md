

## We would like to see a little project.

* Read input data from any http source (for you to choose, rss feed, twitter feed...)  every x seconds-minutes
* Process data with any rules (modify input value, add additional values)
* Put it in any in-memory database (H2 for example)
* By the request in browser show the last 10 entries (no UI, just json) by reading from the in-memory db
* Don’t use hibernate (JPA). Use plain SQL.
* Spring Boot 2, Java
* No Spring integrations framework
* (optional) Create a Docker image for the project, so we can run this as a Docker container

## Project structure:
```
   src/
      main/
          java/
              --here you have classes for process and store data, and defined api to show the data
      test/
          java/
              --here you have test classes for reading data, invoke methods from the ‘main’ classes with data -> read from db -> validate results

 ```

## Hints:

1) Framework for tests: junit
2) Please, do not spend time on complex processing logic.
3) Run tests
 
Put it in github. Should be runnable from the IDE.

P.S. We’ll take a look at every single class ☺
P.P.S. Do not invent the wheel.