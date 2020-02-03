package dev.shermende.cassandra.repository;

import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;

@NoRepositoryBean
public interface BucketRepository<E, P, I> extends CassandraRepository<E, I> {

    void saveBucket(P partition, Long bucket);

    Long findFirstBucket(P partition);

    Long findLastBucket(P partition);

    Long findPrevBucket(P partition, Long bucket);

    Long findNextBucket(P partition, Long bucket);

    List<E> findPrev(P partition, Long bucket, int size);

    List<E> findPrev(P partition, Long bucket, Long snowflake, int size);

    List<E> findNext(P partition, Long bucket, int size);

    List<E> findNext(P partition, Long bucket, Long snowflake, int size);

}
