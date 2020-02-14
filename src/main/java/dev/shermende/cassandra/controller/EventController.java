package dev.shermende.cassandra.controller;

import dev.shermende.cassandra.assembler.EventAssembler;
import dev.shermende.cassandra.assembler.EventPageAssembler;
import dev.shermende.cassandra.assembler.support.CassandraPageable;
import dev.shermende.cassandra.model.EventModel;
import dev.shermende.cassandra.service.EventService;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.hateoas.PagedModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EventController {

    private final EventService eventService;
    private final EventAssembler assembler;
    private final ObjectFactory<EventPageAssembler> factory;

    public EventController(
        EventService eventService,
        EventAssembler assembler,
        ObjectFactory<EventPageAssembler> factory
    ) {
        this.factory = factory;
        this.assembler = assembler;
        this.eventService = eventService;
    }

    @GetMapping("/{partition}")
    public PagedModel<EventModel> get(
        @PathVariable String partition,
        CassandraPageable pageable
    ) {
        return factory.getObject().toModel(eventService.page(partition, pageable), assembler);
    }

}
