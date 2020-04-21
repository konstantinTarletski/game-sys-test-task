# GameSys test task

This test task consist of one application, which read RSS news from configurable URL, process each news data and store it to database.

List of RSS channels can be found here :
`https://edition.cnn.com/services/rss/` 

Processing data performed like this :
Title of the news transformed to uppercase and content of it in reversed order.
Also all fields trimmed to 1000 letters.
RSS news can be processed manually or by scheduler, which delay may be also configured also.

This application have API to control it and view results.

## Architecture 

I was choosing between "JDBCTemplate" and "ResultSet" and decided to build application on "ResultSet". It seems to me more "interesting".
Knowing if that will be a "real" application maybe I will decide to use "JDBCTemplate” or some other technology. But for test task "ResultSet" is more interesting, I think.

For not having code duplication with opening and close connection and  "Statement" I create "base" class for every SQL operation. That is why structure become not usual. 

But not usual not means bad. Application have flexible structure and we can easy add new DAO for other tables if we need in future. Application structure will not create new "SQL instances" if we add new DAO.

Of course, there can be done some improvements. For example, for now it is impossible to pass parameters for "SELECT" operations and "SQL" can be taken only from "Resource" what is not very convenient. But there was no need to add more complex solutions. Because this task has certain requirements. And, for example, having only 3 different SQLs, put 2 of them in one place and 1 to another, not logical in scope of this task.


## Used technologies and tools

* Java 11
* Gradle
* H2 database
* Gradle
* SpringBoot 2
* Lombok
* Docker
* Docker-compose
* Swagger
* Some other small spring embedded things

## Rss-reader API

All API available on swagger by link:
`localhost:8815/swagger-ui.html`

* To process RSS news manually you can call this endpoint:
    `GET http://localhost:8815/api/rss-reader/process-rss`.
    It will execute same logic, that scheduler do. But only once.

* To enable/disable scheduler for reading RSS news from you can use this endpoint.

    `GET http://localhost:8815/api/rss-reader/enable-scheduler?enabled=false`

    Where enabled can be true or false

* To view last 10 database records you can use this endpoint. 
    `GET http://localhost:8815/api/rss-reader/read-last-items`

## How to run project

This project can be run in 2 ways:
* With docker-compose
* Like local applications

### Run with docker-compose

To run this application with "docker-compose" you need just run the command :

`docker-compose up -d`

Yon do not need even to have Java installed on your local computer, because compilation of the source code take place inside docker images.

All API links will be same like if you run it locally.

### Run like local applications

You can start this project like a normal SpringBoot application from console or IDE.

### See the results

There are 2 ways to see result data generated by this project:

* From web API : to view last 10 database records you can use this endpoint. 
    `GET http://localhost:8815/api/api/rss-reader/read-last-items`
    
* From database : you can use H2 console, here is the configuration to it. It also visible from docker-compose

    Link : `http://localhost:8815/h2-console/`

    JDBC URL: `jdbc:h2:tcp://localhost:9090/mem:testdb`
    
    USER : `sa`
    
    PASSWORD : `<empty>` 

### Tests

I covered in tests service, repository and also "DB" layer, where is written logic to work with database.

For testing I use "Junit" like it written in task description, but in extra I was using  "powermock" for "mock" static methods and have ability to cover all "service", "repository" and "db" package methods.

Integration tests I did only for "RssDao" because others classes from service layer can be tested without application context.
