package dev.shermende.cassandra.assembler.support;

import dev.shermende.cassandra.entity.Bucketable;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.PagedModel;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.UriTemplate;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.stream.Collectors;

/**
 * @param <E> entity
 * @param <R> resource/model
 * @param <P> partition
 */
public abstract class AbstractCassandraPageAssembler<E extends Bucketable<P>, R extends RepresentationModel<R>, P>
    implements CassandraPageAssembler<E, R, P> {

    private static final String TRUE = "true";
    private static final String FALSE = "false";
    private static final String FIRST = "first";
    private static final String SELF = "self";
    private static final String PREV = "prev";
    private static final String NEXT = "next";
    private static final String OFFSET = "offset";
    private static final String SIZE = "size";
    private static final String REVERSE = "reverse";

    @Override
    public PagedModel<R> toModel(CassandraPage<E, P> entity) {
        throw new IllegalStateException();
    }

    @Override
    public PagedModel<R> toModel(
        CassandraPage<E, P> page,
        RepresentationModelAssembler<E, R> assembler
    ) {
        return addLinks(
            page,
            getModel(page, assembler)
        );
    }

    private PagedModel<R> getModel(
        CassandraPage<E, P> page,
        RepresentationModelAssembler<E, R> assembler
    ) {
        return new PagedModel<>(
            page.getContent().stream().map(assembler::toModel).collect(Collectors.toList()),
            null
        );
    }

    private PagedModel<R> addLinks(
        CassandraPage<E, P> page,
        PagedModel<R> resource
    ) {
        final String request = ServletUriComponentsBuilder.fromCurrentRequest().build().toString();

        resource.add(createFirstLink(request));

        resource.add(createSelfLink(request));

        if (page.isHasPrev())
            resource.add(createPrevLink(request, page.getFirst(), page.getPageable().getSize()));

        if (page.isHasNext())
            resource.add(createNextLink(request, page.getLast(), page.getPageable().getSize()));

        return resource;
    }

    private Link createFirstLink(
        String request
    ) {
        return new Link(
            new UriTemplate(request),
            FIRST
        );
    }

    private Link createSelfLink(
        String request
    ) {
        return new Link(
            new UriTemplate(UriComponentsBuilder.fromHttpUrl(request).build().toString()),
            SELF
        );
    }

    private Link createPrevLink(
        String request,
        Long first,
        int size
    ) {
        return new Link(
            new UriTemplate(UriComponentsBuilder.fromHttpUrl(request)
                .replaceQueryParam(OFFSET, first)
                .replaceQueryParam(SIZE, size)
                .replaceQueryParam(REVERSE, TRUE).build().toString()),
            PREV
        );
    }

    private Link createNextLink(
        String request,
        Long last,
        int size
    ) {
        return new Link(
            new UriTemplate(UriComponentsBuilder.fromHttpUrl(request)
                .replaceQueryParam(OFFSET, last)
                .replaceQueryParam(SIZE, size)
                .replaceQueryParam(REVERSE, FALSE).build().toString()),
            NEXT
        );
    }

}
