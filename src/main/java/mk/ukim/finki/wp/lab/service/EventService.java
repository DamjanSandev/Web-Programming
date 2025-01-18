package mk.ukim.finki.wp.lab.service;

import jakarta.transaction.Transactional;
import mk.ukim.finki.wp.lab.model.Event;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface EventService {
    List<Event> listAll();

    List<Event> searchEvent(String text);

    Optional<Event> save(String name, String description, Double score, Long locationId, Long categoryId,int numTickets);

    Optional<Event> findById(Long id);

    void updateEvent(Long eventid, String name, String description, Double score, Long locationId, Long categoryId,int numTickets);

    void deleteEvent(Long eventid);

    Page<Event> findPage(Long categoryId, Long locationId, Integer pageNum, Integer pageSize);


    Optional<Event> findByName(String event);
    @Transactional
    void reserveCard(Long id, int numTickets);
}
