package dev.shermende.cassandra.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import java.io.Serializable;
import java.util.Objects;

@JsonIgnoreProperties(ignoreUnknown = true)
@Relation(value = "item", collectionRelation = "data")
public class EventModel extends RepresentationModel<EventModel> implements Serializable {

    private static final long serialVersionUID = 7971056253604387309L;

    private String partition;

    private Long bucket;

    private Long ts;

    private String payload;

    public String getPartition() {
        return partition;
    }

    public EventModel setPartition(String partition) {
        this.partition = partition;
        return this;
    }

    public Long getBucket() {
        return bucket;
    }

    public EventModel setBucket(Long bucket) {
        this.bucket = bucket;
        return this;
    }

    public Long getTs() {
        return ts;
    }

    public EventModel setTs(Long ts) {
        this.ts = ts;
        return this;
    }

    public String getPayload() {
        return payload;
    }

    public EventModel setPayload(String payload) {
        this.payload = payload;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        EventModel that = (EventModel) o;
        return Objects.equals(partition, that.partition) &&
            Objects.equals(bucket, that.bucket) &&
            Objects.equals(ts, that.ts) &&
            Objects.equals(payload, that.payload);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), partition, bucket, ts, payload);
    }

}
