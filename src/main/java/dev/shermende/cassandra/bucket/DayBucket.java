package dev.shermende.cassandra.bucket;

import org.springframework.stereotype.Component;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;

@Component
public class DayBucket extends AbstractTimeBucket {

    private static final ChronoUnit CHRONO_UNIT = ChronoUnit.DAYS;

    @Override
    public long getBucket() {
        return CASSANDRA_EPOCH.until(ZonedDateTime.now(ZoneOffset.UTC), CHRONO_UNIT);
    }

    @Override
    public long getBucket(ZonedDateTime time) {
        return CASSANDRA_EPOCH.until(time.withZoneSameInstant(ZoneOffset.UTC), CHRONO_UNIT);
    }

    @Override
    public long getBucket(long ts) {
        return getBucket(utcZonedDateTime(ts));
    }

}