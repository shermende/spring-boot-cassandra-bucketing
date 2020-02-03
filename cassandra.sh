CQL="
CREATE KEYSPACE IF NOT EXISTS cassandra
WITH REPLICATION = { 'class' : 'SimpleStrategy', 'replication_factor' : 1 }
AND durable_writes = true;
"
until echo $CQL | cqlsh; do
  echo "cqlsh: Cassandra is unavailable to initialize - will retry later"
  sleep 2
done &

exec /docker-entrypoint.sh "$@"
