package dev.shermende.cassandra.assembler;

import dev.shermende.cassandra.entity.Event;
import dev.shermende.cassandra.model.EventModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

@Component
public class EventAssembler implements RepresentationModelAssembler<Event, EventModel> {

    @Override
    public EventModel toModel(Event entity) {
        return new EventModel()
                .setPartition(entity.getPartition())
                .setBucket(entity.getBucket())
                .setTs(entity.getTs())
                .setPayload(entity.getPayload());
    }

}
