package dev.shermende.cassandra.assembler;

import dev.shermende.cassandra.assembler.support.AbstractCassandraPageAssembler;
import dev.shermende.cassandra.entity.Event;
import dev.shermende.cassandra.model.EventModel;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class EventPageAssembler extends AbstractCassandraPageAssembler<Event, EventModel, String> {

}
