package dev.shermende.cassandra.assembler.support;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import dev.shermende.cassandra.entity.Bucketable;

import java.util.Collections;
import java.util.List;

/**
 * @param <E> entity
 * @param <P> partition
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class CassandraPage<E extends Bucketable<?>, P> {

    public static <E extends Bucketable<?>, P> CassandraPage<E, P> of() {
        return new CassandraPage<>();
    }

    public static <E extends Bucketable<?>, P> CassandraPage<E, P> of(
        List<E> list,
        CassandraPageable pageable,
        boolean hasPrev,
        boolean hasNext
    ) {
        return new CassandraPage<>(
            list,
            list.get(0).getTs(),
            list.get(list.size() - 1).getTs(),
            pageable,
            hasPrev,
            hasNext
        );
    }

    private CassandraPage() {
        this.content = Collections.emptyList();
        this.pageable = CassandraPageable.of();
    }

    private CassandraPage(
        List<E> content,
        Long first,
        Long last,
        CassandraPageable pageable,
        boolean hasPrev,
        boolean hasNext
    ) {
        this.content = content;
        this.first = first;
        this.last = last;
        this.pageable = pageable;
        this.hasPrev = hasPrev;
        this.hasNext = hasNext;
    }

    private List<E> content;

    private CassandraPageable pageable;

    private Long first;

    private Long last;

    private boolean hasPrev;

    private boolean hasNext;

    public List<E> getContent() {
        return content;
    }

    public CassandraPage<E, P> setContent(List<E> content) {
        this.content = content;
        return this;
    }

    public CassandraPageable getPageable() {
        return pageable;
    }

    public CassandraPage<E, P> setPageable(CassandraPageable pageable) {
        this.pageable = pageable;
        return this;
    }

    public Long getFirst() {
        return first;
    }

    public CassandraPage<E, P> setFirst(Long first) {
        this.first = first;
        return this;
    }

    public Long getLast() {
        return last;
    }

    public CassandraPage<E, P> setLast(Long last) {
        this.last = last;
        return this;
    }

    public boolean isHasPrev() {
        return hasPrev;
    }

    public CassandraPage<E, P> setHasPrev(boolean hasPrev) {
        this.hasPrev = hasPrev;
        return this;
    }

    public boolean isHasNext() {
        return hasNext;
    }

    public CassandraPage<E, P> setHasNext(boolean hasNext) {
        this.hasNext = hasNext;
        return this;
    }

}
