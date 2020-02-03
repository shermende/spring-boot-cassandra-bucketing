package dev.shermende.cassandra.bucket;

import java.time.ZonedDateTime;

public interface Bucket {

    long getBucket();

    long getBucket(ZonedDateTime time);

    long getBucket(long ts);

}
