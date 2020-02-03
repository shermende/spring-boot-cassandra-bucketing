package dev.shermende.cassandra.service;

import dev.shermende.cassandra.bucket.Bucket;
import dev.shermende.cassandra.entity.Event;
import dev.shermende.cassandra.entity.pk.EventKey;
import dev.shermende.cassandra.repository.BucketRepository;
import dev.shermende.cassandra.service.support.AbstractCassandraBucketService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class EventService extends AbstractCassandraBucketService<Event, String, EventKey> {

    public EventService(
            @Qualifier("monthBucket") Bucket bucket,
            BucketRepository<Event, String, EventKey> bucketRepository
    ) {
        super(bucket, bucketRepository);
    }

    public Event create(String partition, String payload) {
        return save(
                new Event()
                        .setKey(new EventKey()
                                .setPartition(partition)
                                .setBucket(getCurrentBucket())
                                .setTs(getSnowflakeNextId()))
                        .setPayload(payload)
        );
    }

}
