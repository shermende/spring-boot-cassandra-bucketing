package dev.shermende.cassandra.entity;

public interface Bucketable<P> {

    /**
     * complex partition
     */
    P getPartition();

    /**
     * time bucket
     */
    Long getBucket();

    /**
     * @return timestamp or snowflake. snowflake better
     */
    Long getTs();

}
