create table if not exists event_bucket
(
    partition varchar,
    bucket    bigint,
    primary key (partition, bucket)
) with clustering order by (bucket desc);

create table if not exists event
(
    partition varchar,
    bucket    bigint,
    ts        bigint,
    payload   varchar,
    primary key ((partition, bucket), ts)
) with clustering order by (ts desc);

