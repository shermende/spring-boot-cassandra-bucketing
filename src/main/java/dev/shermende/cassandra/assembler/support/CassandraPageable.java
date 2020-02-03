package dev.shermende.cassandra.assembler.support;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CassandraPageable implements Serializable {

    public static CassandraPageable of() {
        return new CassandraPageable();
    }

    public static CassandraPageable of(Long offset) {
        return new CassandraPageable().setOffset(offset);
    }

    public static CassandraPageable of(Long offset, boolean reverse) {
        return new CassandraPageable().setOffset(offset).setReverse(reverse);
    }

    private static final long serialVersionUID = 682376129012063826L;

    private int size = 10;

    private boolean reverse;

    private Long offset;

    public boolean isReverse() {
        return reverse;
    }

    public CassandraPageable setReverse(boolean reverse) {
        this.reverse = reverse;
        return this;
    }

    public int getSize() {
        return size;
    }

    public CassandraPageable setSize(int size) {
        this.size = size;
        return this;
    }

    public Long getOffset() {
        return offset;
    }

    public CassandraPageable setOffset(Long offset) {
        this.offset = offset;
        return this;
    }

}
