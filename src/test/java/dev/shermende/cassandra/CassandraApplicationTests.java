package dev.shermende.cassandra;

import dev.shermende.cassandra.assembler.support.CassandraPage;
import dev.shermende.cassandra.assembler.support.CassandraPageable;
import dev.shermende.cassandra.entity.Event;
import dev.shermende.cassandra.entity.pk.EventKey;
import dev.shermende.cassandra.repository.EventRepository;
import dev.shermende.cassandra.service.EventService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class CassandraApplicationTests {

    @Autowired
    private EventService service;

    @Autowired
    private EventRepository repository;

    @Test
    void page() {
        final CassandraPage<Event, String> page = service.page("one", CassandraPageable.of());
        Assertions.assertTrue(page.isHasNext());
        Assertions.assertFalse(page.isHasPrev());
        Assertions.assertEquals(1579874151098L, (long) page.getFirst());
        Assertions.assertEquals(1577195751093L, (long) page.getLast());
    }

    @Test
    void pageOffset() {
        final CassandraPage<Event, String> page = service.page("one", CassandraPageable.of(1579874151094L));
        Assertions.assertFalse(page.isHasNext());
        Assertions.assertTrue(page.isHasPrev());
        Assertions.assertEquals(1577195751097L, (long) page.getFirst());
        Assertions.assertEquals(1574604456540L, (long) page.getLast());
    }

    @Test
    void pageOffsetReverse() {
        final CassandraPage<Event, String> page = service.page("one", CassandraPageable.of(1579874151094L, true));
        Assertions.assertTrue(page.isHasNext());
        Assertions.assertFalse(page.isHasPrev());
        Assertions.assertEquals(1579874151098L, (long) page.getFirst());
        Assertions.assertEquals(1579874151095L, (long) page.getLast());
    }

    @BeforeEach
    public void before() {
        service.save(new Event()
            .setKey(new EventKey().setPartition("one").setBucket(598L).setTs(1574604456540L)).setPayload("/"));
        service.save(new Event()
            .setKey(new EventKey().setPartition("one").setBucket(598L).setTs(1574604456541L)).setPayload("/"));
        service.save(new Event()
            .setKey(new EventKey().setPartition("one").setBucket(598L).setTs(1574604456542L)).setPayload("/"));
        service.save(new Event()
            .setKey(new EventKey().setPartition("one").setBucket(598L).setTs(1574604456543L)).setPayload("/"));
        service.save(new Event()
            .setKey(new EventKey().setPartition("one").setBucket(598L).setTs(1574604456544L)).setPayload("/"));
        service.save(new Event()
            .setKey(new EventKey().setPartition("one").setBucket(599L).setTs(1577195751093L)).setPayload("/"));
        service.save(new Event()
            .setKey(new EventKey().setPartition("one").setBucket(599L).setTs(1577195751094L)).setPayload("/"));
        service.save(new Event()
            .setKey(new EventKey().setPartition("one").setBucket(599L).setTs(1577195751095L)).setPayload("/"));
        service.save(new Event()
            .setKey(new EventKey().setPartition("one").setBucket(599L).setTs(1577195751096L)).setPayload("/"));
        service.save(new Event()
            .setKey(new EventKey().setPartition("one").setBucket(599L).setTs(1577195751097L)).setPayload("/"));
        service.save(new Event()
            .setKey(new EventKey().setPartition("one").setBucket(600L).setTs(1579874151094L)).setPayload("/"));
        service.save(new Event()
            .setKey(new EventKey().setPartition("one").setBucket(600L).setTs(1579874151095L)).setPayload("/"));
        service.save(new Event()
            .setKey(new EventKey().setPartition("one").setBucket(600L).setTs(1579874151096L)).setPayload("/"));
        service.save(new Event()
            .setKey(new EventKey().setPartition("one").setBucket(600L).setTs(1579874151097L)).setPayload("/"));
        service.save(new Event()
            .setKey(new EventKey().setPartition("one").setBucket(600L).setTs(1579874151098L)).setPayload("/"));
    }

    @AfterEach
    public void after() {
        repository.deleteAll();
    }

}
