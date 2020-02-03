package dev.shermende.cassandra.assembler.support;

import dev.shermende.cassandra.entity.Bucketable;
import org.springframework.hateoas.PagedModel;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;

/**
 * @param <E> entity
 * @param <R> resource/model
 * @param <P> partition
 */
interface CassandraPageAssembler<E extends Bucketable<P>, R extends RepresentationModel<R>, P>
        extends RepresentationModelAssembler<CassandraPage<E, P>, PagedModel<R>> {

    PagedModel<R> toModel(
            CassandraPage<E, P> page,
            RepresentationModelAssembler<E, R> assembler
    );

}
