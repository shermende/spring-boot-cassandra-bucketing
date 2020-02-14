package dev.shermende.cassandra.bucket;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;

abstract class AbstractTimeBucket implements Bucket {
    static final ZonedDateTime CASSANDRA_EPOCH =
        ZonedDateTime.of(LocalDateTime.of(1970, 1, 1, 0, 0, 0), ZoneOffset.UTC);

    ZonedDateTime utcZonedDateTime(long id) {
        return ZonedDateTime.ofInstant(Instant.ofEpochMilli(id), ZoneOffset.UTC);
    }

}
