package dev.shermende.cassandra.entity.pk;

import org.springframework.data.cassandra.core.cql.Ordering;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyClass;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;


@PrimaryKeyClass
public class EventKey {

    @PrimaryKeyColumn(ordinal = 0, value = "partition", type = PrimaryKeyType.PARTITIONED)
    private String partition;

    @PrimaryKeyColumn(ordinal = 1, value = "bucket", type = PrimaryKeyType.PARTITIONED)
    private Long bucket;

    @PrimaryKeyColumn(ordinal = 2, value = "ts", type = PrimaryKeyType.CLUSTERED, ordering = Ordering.DESCENDING)
    private Long ts;

    public String getPartition() {
        return partition;
    }

    public EventKey setPartition(String partition) {
        this.partition = partition;
        return this;
    }

    public Long getBucket() {
        return bucket;
    }

    public EventKey setBucket(Long bucket) {
        this.bucket = bucket;
        return this;
    }

    public Long getTs() {
        return ts;
    }

    public EventKey setTs(Long ts) {
        this.ts = ts;
        return this;
    }

}
