package dev.shermende.cassandra.repository;

import dev.shermende.cassandra.entity.Event;
import dev.shermende.cassandra.entity.pk.EventKey;
import org.springframework.data.cassandra.repository.Query;

import java.util.List;

public interface EventRepository
    extends BucketRepository<Event, String, EventKey> {

    @Override
    @Query(value = "" +
        " insert into event_bucket (partition, bucket) " +
        " values (:partition, :bucket) ")
    void saveBucket(String partition, Long bucket);

    @Override
    @Query(value = "" +
        " select bucket " +
        " from event_bucket " +
        " where partition = :partition " +
        " order by bucket " +
        " limit 1 ")
    Long findFirstBucket(String partition);

    @Override
    @Query(value = "" +
        " select bucket " +
        " from event_bucket " +
        " where partition = :partition " +
        " limit 1 ")
    Long findLastBucket(String partition);

    @Override
    @Query(value = "" +
        " select bucket " +
        " from event_bucket " +
        " where partition = :partition " +
        "       and bucket > :bucket " +
        " order by bucket " +
        " limit 1 ")
    Long findPrevBucket(String partition, Long bucket);

    @Override
    @Query(value = "" +
        " select bucket " +
        " from event_bucket " +
        " where partition = :partition " +
        "       and bucket < :bucket " +
        " limit 1 ")
    Long findNextBucket(String partition, Long bucket);

    @Override
    @Query(value = "" +
        " select * " +
        " from event " +
        " where partition = :partition " +
        "       and bucket = :bucket " +
        " order by ts " +
        " limit :size ")
    List<Event> findPrev(String partition, Long bucket, int size);

    @Override
    @Query(value = "" +
        " select * " +
        " from event " +
        " where partition = :partition " +
        "       and bucket = :bucket " +
        "       and ts > :ts " +
        " order by ts " +
        " limit :size ")
    List<Event> findPrev(String partition, Long bucket, Long ts, int size);

    @Override
    @Query(value = "" +
        " select * " +
        " from event " +
        " where partition = :partition " +
        "       and bucket = :bucket " +
        " limit :size ")
    List<Event> findNext(String partition, Long bucket, int size);

    @Override
    @Query(value = "" +
        " select * " +
        " from event " +
        " where partition = :partition " +
        "       and bucket = :bucket " +
        "       and ts < :ts " +
        " limit :size")
    List<Event> findNext(String partition, Long bucket, Long ts, int size);

}
