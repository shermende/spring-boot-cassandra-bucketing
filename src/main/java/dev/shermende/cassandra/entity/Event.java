package dev.shermende.cassandra.entity;

import dev.shermende.cassandra.entity.pk.EventKey;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import java.util.Objects;

@Table("event")
public class Event implements Bucketable<String> {

    @PrimaryKey
    private EventKey key;

    private String payload;

    public EventKey getKey() {
        return key;
    }

    public Event setKey(EventKey key) {
        this.key = key;
        return this;
    }

    public String getPayload() {
        return payload;
    }

    public Event setPayload(String payload) {
        this.payload = payload;
        return this;
    }

    @Override
    public String getPartition() {
        return Objects.nonNull(key) && Objects.nonNull(key.getPartition()) ? key.getPartition() : null;
    }

    @Override
    public Long getBucket() {
        return Objects.nonNull(key) && Objects.nonNull(key.getBucket()) ? key.getBucket() : null;
    }

    @Override
    public Long getTs() {
        return Objects.nonNull(key) && Objects.nonNull(key.getTs()) ? key.getTs() : null;
    }

}
