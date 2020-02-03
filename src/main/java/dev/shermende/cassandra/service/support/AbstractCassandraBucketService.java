package dev.shermende.cassandra.service.support;


import dev.shermende.cassandra.assembler.support.CassandraPage;
import dev.shermende.cassandra.assembler.support.CassandraPageable;
import dev.shermende.cassandra.bucket.Bucket;
import dev.shermende.cassandra.entity.Bucketable;
import dev.shermende.cassandra.repository.BucketRepository;
import org.springframework.util.Assert;

import javax.validation.Valid;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @param <E> entity
 * @param <P> partition
 * @param <I> key
 */
public abstract class AbstractCassandraBucketService<E extends Bucketable<P>, P, I>
        implements CassandraBucketService<E, P> {
    private static final String PARTITION_IS_NULL = "Partition is null";
    private static final String BUCKET_IS_NULL = "Bucket is null";
    private static final String TS_IS_NULL = "Ts is null";

    private final Bucket bucket;
    protected final BucketRepository<E, P, I> bucketRepository;

    public AbstractCassandraBucketService(
            Bucket bucket,
            BucketRepository<E, P, I> bucketRepository
    ) {
        this.bucket = bucket;
        this.bucketRepository = bucketRepository;
    }

    protected Bucket bucket() {
        return bucket;
    }

    public E save(@Valid E entity) {
        Assert.notNull(entity.getPartition(), PARTITION_IS_NULL);
        Assert.notNull(entity.getBucket(), BUCKET_IS_NULL);
        Assert.notNull(entity.getTs(), TS_IS_NULL);

        bucketRepository.saveBucket(entity.getPartition(), entity.getBucket());
        return bucketRepository.save(entity);
    }

    @Override
    public CassandraPage<E, P> page(
            P partition,
            CassandraPageable pageable
    ) {
        final List<E> list = getContent(partition, pageable);

        if (list.isEmpty())
            return CassandraPage.of();

        return CassandraPage.of(
                list, pageable,
                hasPrevPage(partition, list.get(0).getTs()), hasNextPage(partition, list.get(list.size() - 1).getTs())
        );
    }

    @Override
    public List<E> findPrevPage(
            P partition,
            Long bucket,
            Long snowflake,
            int size
    ) {
        final Long b = Optional.ofNullable(findBucket(snowflake)).orElse(bucket);

        if (Objects.isNull(b))
            return Collections.emptyList();

        final LinkedList<E> list = new LinkedList<>();

        if (Objects.isNull(snowflake))
            list.addAll(findPrev(partition, b, size));

        if (Objects.nonNull(snowflake))
            list.addAll(findPrev(partition, b, snowflake, size));

        if (list.size() >= size)
            return list;

        Long prevB = findPrevBucket(partition, b);

        if (Objects.isNull(prevB))
            return list;

        list.addAll(
                findPrevPage(
                        partition,
                        prevB,
                        list.isEmpty() || list.size() < size ? null : list.get(list.size() - 1).getTs(),
                        size - list.size()
                )
        );

        return list.stream()
                .sorted((s1, s2) -> Long.compare(s2.getTs(), s1.getTs())).collect(Collectors.toList());
    }

    @Override
    public List<E> findPrev(
            P partition,
            Long bucket,
            int size
    ) {
        return bucketRepository.findPrev(partition, bucket, size);
    }

    @Override
    public List<E> findPrev(
            P partition,
            Long bucket,
            Long snowflake,
            int size
    ) {
        return bucketRepository.findPrev(partition, bucket, snowflake, size)
                .stream().sorted((s1, s2) -> Long.compare(s2.getTs(), s1.getTs())).collect(Collectors.toList());
    }


    @Override
    public List<E> findNextPage(
            P partition,
            Long bucket,
            Long snowflake,
            int size
    ) {
        final Long b = Optional.ofNullable(findBucket(snowflake)).orElse(bucket);

        if (Objects.isNull(b))
            return Collections.emptyList();

        final LinkedList<E> list = new LinkedList<>();

        if (Objects.isNull(snowflake))
            list.addAll(findNext(partition, b, size));

        if (Objects.nonNull(snowflake))
            list.addAll(findNext(partition, b, snowflake, size));

        if (list.size() >= size)
            return list;

        Long nextB = findNextBucket(partition, b);
        if (Objects.isNull(nextB))
            return list;

        list.addAll(
                findNextPage(
                        partition,
                        nextB,
                        list.isEmpty() || list.size() < size ? null : list.get(list.size() - 1).getTs(),
                        size - list.size()
                )
        );

        return list;
    }

    @Override
    public List<E> findNext(
            P p,
            Long bucket,
            int size
    ) {
        return bucketRepository.findNext(p, bucket, size);
    }

    @Override
    public List<E> findNext(
            P p,
            Long bucket,
            Long snowflake,
            int size
    ) {
        return bucketRepository.findNext(p, bucket, snowflake, size);
    }

    @Override
    public Long getCurrentBucket() {
        return bucket().getBucket();
    }

    @Override
    public Long getSnowflakeNextId() {
        return System.currentTimeMillis();
    }

    @Override
    public Long findBucket(
            Long snowflake
    ) {
        if (Objects.isNull(snowflake))
            return null;
        return bucket().getBucket(snowflake);
    }

    @Override
    public Long findFirstBucket(
            P partition
    ) {
        return bucketRepository.findFirstBucket(partition);
    }

    @Override
    public Long findLastBucket(
            P partition
    ) {
        return bucketRepository.findLastBucket(partition);
    }

    @Override
    public Long findPrevBucket(
            P partition,
            Long bucket
    ) {
        return bucketRepository.findPrevBucket(partition, bucket);
    }

    @Override
    public Long findNextBucket(
            P partition,
            Long bucket
    ) {
        return bucketRepository.findNextBucket(partition, bucket);
    }


    private List<E> getContent(P partition, CassandraPageable pageable) {
        final int size = pageable.getSize();
        final Long snowflake = pageable.getOffset();

        if (pageable.isReverse())
            return findPrevPage(partition, findFirstBucket(partition), snowflake, size);
        return findNextPage(partition, findLastBucket(partition), snowflake, size);
    }

    private boolean hasPrevPage(P partition, Long snowflake) {
        if (Objects.isNull(snowflake))
            return false;
        final Long b = findBucket(snowflake);
        return Objects.nonNull(b) &&
                (!findPrev(partition, b, snowflake, 1).isEmpty()
                        || Objects.nonNull(findPrevBucket(partition, b)));
    }

    private boolean hasNextPage(P partition, Long snowflake) {
        if (Objects.isNull(snowflake))
            return false;
        final Long b = findBucket(snowflake);
        return Objects.nonNull(b) &&
                (!findNext(partition, b, snowflake, 1).isEmpty()
                        || Objects.nonNull(findNextBucket(partition, b)));
    }


}
