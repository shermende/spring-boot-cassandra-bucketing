## Cassandra time-based bucketing with pagination

[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=shermende_spring-boot-cassandra-bucketing&metric=alert_status)](https://sonarcloud.io/dashboard?id=shermende_spring-boot-cassandra-bucketing)
[![CircleCI](https://circleci.com/gh/shermende/spring-boot-cassandra-bucketing.svg?style=svg)](https://circleci.com/gh/shermende/spring-boot-cassandra-bucketing)

## Run
```
$ docker-compose up -d --build
$ ./gradlew bootRun
```
* localhost:9042 - cassandra
* localhost:8080 - application
## References
* [About bucketing](https://blog.discordapp.com/how-discord-stores-billions-of-messages-7fa6ec7ee4c7)
* [Bucket implementation](https://github.com/daviddominguez/springboot-cassandra-example)
* [Bucket types](https://www.slideshare.net/MarkusHfer/bucket-your-partitions-wisely-cassandra-summit-2016)