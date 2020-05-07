Hello

Thanks for test task implementation. It looks good.

Our comments bellow.

## Pro's:

* Solution overall works
* Original approach with DB Operations
* Code is easy to read and understand
* Docker in place (compilation takes place on docker build stage)
* Very good readme

## Con's:
## Major:

## Average:

* SqlSelect#processSql - ResultSet not closed. Underlying resources are released but it's always a good habit to close result set
* Would be nice to use connection pool instead of manual handling connections. No need to reinvent the wheel: DatabaseManager#getConnection
* Strange inheritance(or naming): SqlSelect extends DatabaseManager, SqlInsert extends DatabaseManager, SqlUpdate extends DatabaseManager
* DatabaseManager - consider using cache and not loading resource each time
* DatabaseManager#executeSql and processSql - returns List<T> . I think signature could be improved.
* Rest endpoint naming doesn't follow best practices
* SchedulerReader.status - misuse of "volatile"/AtomicBoolean. Value is set once and never reassigned again.

## Minor:

* Misuse of logger. No need to format string - logger can do it by yourself. Example: String logText = format("There was error while executing SQL, error message is %s", e.getMessage());
  log.error(logText, e);

* Initial delay could be moved to configuration as well
* H2 server could be started by spring
* RssProcessor#processDescription - StringBuilder could be used instead of StringBuffer.
* RssProcessor.DB_ROW_LENGHT - typo and being not static is better to have different name
* Some not needed stuff for testing - SchedulerReader.status
* SqlInsert:processSql - better to return something instead of null
* SqlUpdate: <T> is never used in update method
* SqlUpdate: return null also is not a very good practice

## Other:

* Test coverage might be better, testing could be improved
* App is run under root user inside of docker


We need to take a little bit more time for decision 😊


Dmitri Plahhotnikov

# MY RESPONCE 

Hello,

Thanks for the answer,
Here is my answer to your answer :)

1. SqlSelect#processSql - ResultSet not closed — Yes, my mistake.
2. Would be nice to use connection pool instead of manual handling connections — I specially do not use Hinkari connection pool and did all manually. I decided that it will be more interesting. Do not agree with this point, because in the task was written that I can chose how to write.
3. Strange inheritance(or naming). — Yes agree, strange naming.
4. DatabaseManager - consider using cache and not loading resource each time. --- It was not scope of the task to do cashing and or other improvements. So, I do not agree with this point. There can be done lot of improvements if it plan to be «in production»
5. DatabaseManager#executeSql and processSql - returns List<T> . I think signature could be improved. --- May be, but I was trying to do it universal. So. Agree
6. Rest endpoint naming doesn't follow best practices. --- Agree. We now use it in BigBank, forgot co check. Agree
7. SchedulerReader.status - misuse of "volatile"/AtomicBoolean. Value is set once and never reassigned again. --- Do not agree. You can change status via REST call «@GetMapping(path = "/enable-scheduler")». Do not agree.
8. Misuse of logger. No need to format string --- if you use «log.error» then there only 2 parameters and second is «Throwable» no more parameters if I use second «Throwable». That is why I use manual formatting. And Also use manual if I return in method same text. You can see my another task I send to you, there I use parameters of logger. Do not agree.
9. Initial delay could be moved to configuration as well --- Yes. Agree.
10. H2 server could be started by spring. --- What You mean ? «H2Configuration.java» starts H2 server.
11. RssProcessor#processDescription - StringBuilder could be used instead of StringBuffer. --- But why you do not like «StringBuffer» ?
12. RssProcessor.DB_ROW_LENGHT --- Yes, may be naming can be better.
13. Some not needed stuff for testing - SchedulerReader.status --- I did it for myself, to not send manual REST requests. And decided to let it be.
14. SqlInsert:processSql - better to return something instead of null. --- Agree. But anyway no-one reads the result, I was thing that You will ask «Why you do not process result? » and decided to return null.
15. SqlUpdate: <T> is never used in update method ---- not sure what You mean.
16. SqlUpdate: return null also is not a very good practice --- same like 14.
17. Test coverage might be better, testing could be improved. --- Testing and coverage always can be improved :) I show all kind of tests. and decided that it is enough fo show that I can write any kind of tests.
18. App is run under root user inside of docker --- Yes. Decided not to spend time for creating new user. So. Agree

# Dmitri Answer

Hello

Sorry for the late reply.
Your test task was quite good and you defended the implementation, which gives your reputation points 😊
 
We discussed your candidacy among team leads, and decided not to go further with you.

Thanks and good luck.

Dmitri Plahhotnikov
