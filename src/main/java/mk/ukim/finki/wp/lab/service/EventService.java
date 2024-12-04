package mk.ukim.finki.wp.lab.service;

import mk.ukim.finki.wp.lab.model.Event;

import java.util.List;
import java.util.Optional;

public interface EventService {
    List<Event> listAll();

    List<Event> searchEvent(String text);

    Optional<Event> save(String name, String description, Double score, Long locationId, Long categoryId);

    Optional<Event> findById(Long id);

    void updateEvent(Long eventid, String name, String description, Double score, Long locationId, Long categoryId);

    void deleteEvent(Long eventid);

}
