package dev.shermende.cassandra;

import dev.shermende.cassandra.service.EventService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@EnableScheduling
@SpringBootApplication
public class CassandraApplication {

    public CassandraApplication(EventService eventService) {
        this.eventService = eventService;
    }

    public static void main(String[] args) {
        SpringApplication.run(CassandraApplication.class, args);
    }

    private final EventService eventService;

//    @Scheduled(fixedDelay = 20000)
    public void run() {
        eventService.create("one", String.valueOf(System.currentTimeMillis()));
    }

}
