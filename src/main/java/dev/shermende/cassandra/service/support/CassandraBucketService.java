package dev.shermende.cassandra.service.support;


import dev.shermende.cassandra.assembler.support.CassandraPage;
import dev.shermende.cassandra.assembler.support.CassandraPageable;
import dev.shermende.cassandra.entity.Bucketable;

import javax.validation.Valid;
import java.util.List;

/**
 * @param <E> entity
 * @param <P> partition
 */
public interface CassandraBucketService<E extends Bucketable<P>, P> {

    E save(@Valid E entity);

    CassandraPage<E, P> page(
            P partition,
            CassandraPageable pageable
    );


    List<E> findPrevPage(
            P partition,
            Long bucket,
            Long snowflake,
            int size
    );


    List<E> findPrev(
            P partition,
            Long bucket,
            int size
    );


    List<E> findPrev(
            P partition,
            Long bucket,
            Long snowflake,
            int size
    );


    List<E> findNextPage(
            P partition,
            Long bucket,
            Long snowflake,
            int size
    );


    List<E> findNext(
            P partition,
            Long bucket,
            int size
    );


    List<E> findNext(
            P partition,
            Long bucket,
            Long snowflake,
            int size
    );


    Long findBucket(
            Long snowflake
    );


    Long findFirstBucket(
            P partition
    );


    Long findLastBucket(
            P partition
    );


    Long findPrevBucket(
            P partition,
            Long bucket
    );


    Long findNextBucket(
            P partition,
            Long bucket
    );


    Long getCurrentBucket();


    Long getSnowflakeNextId();

}
